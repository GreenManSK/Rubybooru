package net.greenmanov.anime.rubybooru.parser;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.greenmanov.anime.ImageSorter.json.JsonConvertor;
import net.greenmanov.anime.ImageSorter.json.JsonDatabase;
import net.greenmanov.anime.rubybooru.database.daos.DirDao;
import net.greenmanov.anime.rubybooru.database.daos.ImageDao;
import net.greenmanov.anime.rubybooru.database.daos.TagDao;
import net.greenmanov.anime.rubybooru.database.entities.Dir;
import net.greenmanov.anime.rubybooru.database.entities.Image;
import net.greenmanov.anime.rubybooru.database.entities.Tag;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic implementation of IDirectoryParser
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
final public class DirectoryParser implements IDirectoryParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryParser.class.getName());

    @Inject
    private DirDao dirDao;

    @Inject
    private TagDao tagDao;

    @Inject
    private ImageDao imageDao;

    private List<Tag> tags;

    public DirectoryParser() {
    }

    /**
     * @param root       Root directory
     * @param recursive  Specify if subdirectories should be also parsed
     * @param dataUpdate If true, all image data in the database will be updated to match info in directory, if false
     *                   only new images will be added and missing images removed from database
     * @param finished   Future for notification about parsing finish
     */
    @Override
    public void parseDir(Path root, boolean recursive, boolean dataUpdate, Future<Void> finished) {
        LOGGER.info("Parsing directory '{}', recursive: {}, dataUpdate: {}", root, recursive, dataUpdate);
        tags = tagDao.getAll();
        parse(root, dirDao.getRoot(), recursive, dataUpdate);
        LOGGER.info("Parsing finished");
        finished.complete();
    }

    /**
     * Parse directory path
     *
     * @param dirPath    Path to directory
     * @param dir        Dir entity
     * @param recursive  Specify if subdirectories should be also parsed
     * @param dataUpdate Check data in the database for updates
     */
    private void parse(Path dirPath, Dir dir, boolean recursive, boolean dataUpdate) {
        Path dbFilePath = dirPath.resolve(JsonDatabase.DEFAULT_NAME);
        if (!Files.exists(dbFilePath)) {
            LOGGER.info("No database file found in {}", dirPath);
            return;
        } else {
            loadData(dir, dirPath, dbFilePath, dataUpdate);
        }

        if (recursive) {
            File[] subdirs = dirPath.toFile().listFiles(File::isDirectory);
            if (subdirs == null) {
                LOGGER.error("Couldn't get subdirectories of {}", dirPath);
                return;
            }
            for (File subdir : subdirs) {
                parse(subdir.toPath(), dirDao.getSubDir(dir, subdir.getName()), true, dataUpdate);
            }
        }
    }

    /**
     * Load data from folder database file into real database.
     * Id dataUpdate is {@code true}, images that are no longer in the folder will be deleted from the database.
     *
     * @param dir        Dir entity
     * @param dirPath    Path to directory
     * @param dbFilePath Path to database file
     * @param dataUpdate Specify if data should be updated for images already in database and images no longer in the
     *                   folder should be removed from the database
     */
    private void loadData(Dir dir, Path dirPath, Path dbFilePath, boolean dataUpdate) {
        LOGGER.info("Reading {}", dbFilePath);
        try {
            List<String> lines = Files.readAllLines(dbFilePath);
            JsonArray array = new JsonArray(String.join("\n", lines));
            for (int i = 0; i < array.size(); i++) {
                loadImage(dir, array.getJsonObject(i), dataUpdate);
            }
        } catch (IOException e) {
            LOGGER.error("Error while reading {}", dbFilePath, e);
        }
        if (dataUpdate) {
            for (Image image : dir.getImages().values()) {
                if (!Files.exists(dirPath.resolve(image.getName()))) {
                    dirDao.transaction((em) -> {
                        dir.removeImage(image);
                        imageDao.remove(image);
                        em.merge(dir);
                    });
                }
            }
        }
    }

    /**
     * Load data for image into database
     *
     * @param dir        Dir entity
     * @param json       Json of image info
     * @param dataUpdate Specify if data should be updated for images already in database
     */
    private void loadImage(Dir dir, JsonObject json, boolean dataUpdate) {
        net.greenmanov.anime.ImageSorter.helpers.Image img = JsonConvertor.toImage(new JSONObject(json.toString()));
        if (dir.hasImage(img.getName())) {
            if (!dataUpdate)
                return;
            imageDao.transaction((em) -> {
                Image entity = dir.getImage(img.getName());
                imageToEntity(entity, img);
                updateTags(entity, img);
                em.merge(entity);
            });
        } else {
            imageDao.transaction((em) -> {
                Image entity = new Image();
                imageToEntity(entity, img);
                updateTags(entity, img);
                imageDao.create(entity);
                dir.addImage(entity);
            });
        }
    }

    /**
     * Update tags of entity based on ImageSorter image tags
     *
     * @param entity Image entity
     * @param image  ImageSorter image
     */
    private void updateTags(Image entity, net.greenmanov.anime.ImageSorter.helpers.Image image) {
        List<net.greenmanov.iqdb.parsers.Tag> tags = image.getTags();
        List<Tag> imageTags = new ArrayList<>();
        for (net.greenmanov.iqdb.parsers.Tag t : tags) {
            Tag tag = new Tag(t.getValue(), t.getTag());
            int index = this.tags.indexOf(tag);
            if (index == -1) {
                tagDao.create(tag);
                this.tags.add(tag);
            } else {
                tag = this.tags.get(index);
            }
            imageTags.add(tag);
        }
        entity.setTags(imageTags);
    }

    /**
     * Load info from ImageSorter image into image entity
     *
     * @param entity Image entity
     * @param image  ImageSorter image
     */
    private void imageToEntity(Image entity, net.greenmanov.anime.ImageSorter.helpers.Image image) {
        entity.setName(image.getName());
        entity.setDate(image.getDate());
        entity.setWidth(image.getWidth());
        entity.setHeight(image.getHeight());
        entity.setSource(image.getSource());
        entity.setInfoSource(image.getInfoSource());
    }
}
