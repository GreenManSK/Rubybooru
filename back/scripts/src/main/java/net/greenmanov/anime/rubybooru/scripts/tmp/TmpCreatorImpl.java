package net.greenmanov.anime.rubybooru.scripts.tmp;

import net.greenmanov.anime.rurybooru.persistance.entity.Image;
import net.greenmanov.anime.rurybooru.service.ImageFileService;
import net.greenmanov.anime.rurybooru.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Simple implementation of TmpCreator
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Service
@Transactional
public class TmpCreatorImpl implements TmpCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(TmpCreatorImpl.class.getName());

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageFileService imageFileService;

    /**
     * Delete all files in tmp folder expect for files starting with .
     *
     * @param tmpPath Path to tmp folder
     * @throws IOException If there is any problem while deleting files
     */
    @Override
    public void emptyTmpDirectory(Path tmpPath) throws IOException {
        File[] files = tmpPath.toFile().listFiles();
        if (files == null) {
            throw new IOException("Invalid tmp path");
        }
        for (File file : files) {
            if (!file.getName().startsWith(".")) {
                file.delete();
            }
        }
    }

    /**
     * Create tmp images for all images with provided size
     *
     * @param tmpPath Path to tmp folder
     * @param width   Width of tmp images
     * @param height  Height of tmp images
     */
    @Override
    public void createTmps(Path tmpPath, int width, int height) {
        List<Image> images = imageService.getAll();
        int c = 0;
        for (Image image : images) {
            imageFileService.resize(image, width, height);
            c++;
            if (c % 1000 == 0) {
                LOGGER.info("Created " + c + "/" + images.size());
            }
        }
    }
}
