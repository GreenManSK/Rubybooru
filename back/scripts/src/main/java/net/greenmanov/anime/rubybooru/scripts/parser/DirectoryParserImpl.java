package net.greenmanov.anime.rubybooru.scripts.parser;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.greenmanov.anime.ImageSorter.json.JsonConvertor;
import net.greenmanov.anime.ImageSorter.json.JsonDatabase;
import net.greenmanov.anime.rurybooru.persistance.entity.Dir;
import net.greenmanov.anime.rurybooru.persistance.entity.Image;
import net.greenmanov.anime.rurybooru.persistance.entity.Tag;
import net.greenmanov.anime.rurybooru.service.DirService;
import net.greenmanov.anime.rurybooru.service.ImageFileService;
import net.greenmanov.anime.rurybooru.service.ImageService;
import net.greenmanov.anime.rurybooru.service.TagService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * DirectoryParser implemented using https://github.com/GreenManSK/AnimeImageSorter
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Service
@Transactional
public class DirectoryParserImpl implements DirectoryParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryParserImpl.class.getName());

    @Autowired
    private TagService tagService;

    @Autowired
    private DirService dirService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageFileService imageFileService;

    private BiMap<Long, Tag> tags;

    /**
     * Parse all data from directory according to parameters and saves it to database
     *
     * @param directory  Root directory
     * @param recursive  Specify if subdirectories should be also parsed
     * @param dataUpdate If true, all image data in the database will be updated to match info in directory, if false
     */
    @Override
    public void parseDir(Path directory, boolean recursive, boolean dataUpdate) {
        LOGGER.info("Parsing directory '{}', recursive: {}, dataUpdate: {}", directory, recursive, dataUpdate);

        tags = HashBiMap.create(tagService.getAll().stream().collect(Collectors.toMap(Tag::getId, t -> t)));
        parse(directory, recursive, dataUpdate);

        LOGGER.info("Parsing finished");
    }

    private void parse(Path directory, boolean recursive, boolean dataUpdate) {
        parse(null, directory, recursive, dataUpdate);
    }

    /**
     * Parse directory path
     *
     * @param parent        Dir entity for parent of this directory
     * @param directoryPath Path to directory
     * @param recursive     Specify if subdirectories should be also parsed
     * @param dataUpdate    Check data in the database for updates
     */
    private void parse(Dir parent, Path directoryPath, boolean recursive, boolean dataUpdate) {
        LOGGER.info("Parsing {}", directoryPath);
        Dir dir;
        if (parent != null) {
            dir = dirService.getSubDir(parent, directoryPath.getFileName().toString());
        } else {
            dir = dirService.getRoot();
        }

        syncImages(dir, directoryPath, dataUpdate);

        if (recursive) {
            File[] subdirs = directoryPath.toFile().listFiles();
            if (subdirs == null) {
                LOGGER.error("Couldn't get subdirs of {}", directoryPath);
                return;
            }
            for (File subdir : subdirs) {
                if (subdir.isDirectory()) {
                    parse(dir, subdir.toPath(), recursive, dataUpdate);
                }
            }
        }
    }

    /**
     * Synchronize images between real directory and its database representation
     *
     * @param dir           Dir entity
     * @param directoryPath Path to directory
     * @param dataUpdate    Specify if deleted images should be removed and existing images updated if needed
     */
    private void syncImages(Dir dir, Path directoryPath, boolean dataUpdate) {
        LOGGER.info("Adding images {}", directoryPath);
        JSONArray db = loadDatabase(directoryPath);
        if (db == null)
            return;
        for (Object obj : db) {
            if (obj instanceof JSONObject) {
                net.greenmanov.anime.ImageSorter.helpers.Image img = JsonConvertor.toImage((JSONObject) obj);
                saveImage(img, dir, dataUpdate);
            } else {
                LOGGER.warn("Invalid object in database of {}: {}", directoryPath, obj);
            }
        }

        if (dataUpdate) {
            removeDeletedImages(dir, directoryPath);
        }
    }

    /**
     * Create new image or update existing image in database based on the image name
     *
     * @param img        Image object from ImageSorter
     * @param dir        Dir entity
     * @param dataUpdate Specify if existing images should be updated
     */
    private void saveImage(net.greenmanov.anime.ImageSorter.helpers.Image img, Dir dir, boolean dataUpdate) {
        Image imageEntity = dir.getImages().getOrDefault(img.getName(), null);

        if (imageEntity != null && dataUpdate) {
            updateImageEntity(imageEntity, img);
            updateTags(imageEntity, img);
        } else if (imageEntity == null) {
            imageEntity = new Image();
            dir.addImage(imageEntity);
            updateImageEntity(imageEntity, img);
            updateTags(imageEntity, img);
            if (!imageFileService.imageFileExists(imageEntity)) {
                dir.removeImage(imageEntity);
                return;
            }
            imageService.create(imageEntity);
        }
    }

    /**
     * Synchronize tags between image entity and image object from json database
     *
     * @param entity Image entity
     * @param img    Image object from ImageSorter
     */
    private void updateTags(Image entity, net.greenmanov.anime.ImageSorter.helpers.Image img) {
        Set<net.greenmanov.iqdb.parsers.Tag> newTags = new HashSet<>(img.getTags());
        Set<Tag> oldTags = new HashSet<>(entity.getTags());

        // Remove unused tags
        for (Tag tag : oldTags) {
            if (!newTags.contains(new net.greenmanov.iqdb.parsers.Tag(tag.getType(), tag.getName()))) {
                entity.removeTag(tag);
            }
        }

        // Add new tags
        for (net.greenmanov.iqdb.parsers.Tag tag : newTags) {
            Tag tagEntity = new Tag(tag.getValue(), tag.getTag());
            if (!oldTags.contains(tagEntity)) {
                Long tagId = this.tags.inverse().get(tagEntity);
                if (tagId != null) {
                    entity.addTag(this.tags.get(tagId));
                } else {
                    tagService.create(tagEntity);
                    this.tags.put(tagEntity.getId(), tagEntity);
                    entity.addTag(tagEntity);
                }
            }
        }
    }

    /**
     * Remove images not present in real directory from database
     *
     * @param dir           Dir entity
     * @param directoryPath Path to directory
     */
    private void removeDeletedImages(Dir dir, Path directoryPath) {
        List<Image> removed = new ArrayList<>();
        for (Map.Entry<String, Image> entry : dir.getImages().entrySet()) {
            if (!Files.exists(directoryPath.resolve(entry.getValue().getName()))) {
                removed.add(entry.getValue());
            }
        }

        for (Image img : removed) {
            dir.removeImage(img);
            imageService.remove(img);
        }
    }

    /**
     * Update image entity using data from ImageSorter Image object
     *
     * @param entity Image entity
     * @param img    ImageSorter object
     */
    private void updateImageEntity(Image entity, net.greenmanov.anime.ImageSorter.helpers.Image img) {
        entity.setName(img.getName());
        entity.setDate(img.getDate());
        entity.setWidth(img.getWidth());
        entity.setHeight(img.getHeight());
        entity.setSource(img.getSource());
        entity.setInfoSource(img.getInfoSource());
    }

    /**
     * Load JSON database for directory created by ImageSorter
     *
     * @param directoryPath Path to directory
     * @return JSONArray of JSONObject representing images or {@code null} if there was problem while parsing file
     */
    private JSONArray loadDatabase(Path directoryPath) {
        Path dbFile = directoryPath.resolve(JsonDatabase.DEFAULT_NAME);
        try {
            List<String> lines = Files.readAllLines(dbFile);
            return new JSONArray(String.join("\n", lines));
        } catch (IOException | JSONException e) {
            LOGGER.error("Couldn't parse database file {}", dbFile, e);
        }
        return null;
    }
}
