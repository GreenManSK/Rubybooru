package net.greenmanov.anime.rurybooru.service;

import com.google.common.io.Files;
import net.greenmanov.anime.rurybooru.persistance.RubybooruConfig;
import net.greenmanov.anime.rurybooru.persistance.entity.Image;
import net.greenmanov.anime.rurybooru.persistance.utils.DirUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class ImageResizeServiceImpl
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Service
public class ImageFileServiceImpl implements ImageFileService {
    private final static Logger logger = LoggerFactory.getLogger(ImageFileServiceImpl.class);

    @Autowired
    private RubybooruConfig config;

    /**
     * Resize image to provided resolution without ration change. Larger size of the image will fit provided resolution
     *
     * @param image  Image entity
     * @param width  Width
     * @param height Height
     * @return File name of resized image in tmp directory
     */
    @Override
    public String resize(Image image, Integer width, Integer height) {
        String file = getFileName(image, width, height);
        Path tmpFilePath = Paths.get(config.getServerTmpPath()).resolve(file);
        if (!tmpFilePath.toFile().exists()) {
            Path imagePath = Paths.get(config.getGalleryPath() + DirUtils.getPath(image));
            try {
                BufferedImage bi = ImageIO.read(imagePath.toFile());
                BufferedImage resized = resize(bi, width, height);
                ImageIO.write(resized, "jpg", tmpFilePath.toFile());
            } catch (IOException e) {
                logger.error("Couldn't resize image {}", imagePath, e);
            }
        }
        return file;
    }

    /**
     * Open image in file explorer
     *
     * @param image Image entity
     */
    @Override
    public void open(Image image) {
        Path path = Paths.get(config.getGalleryPath() + DirUtils.getPath(image));
        try {
            logger.debug("opening {}", path);
            Runtime.getRuntime().exec("explorer.exe /select," + path);
        } catch (IOException e) {
            logger.error("Couldn't open {} in explorer.exe", path, e);
        }
    }

    /**
     * Check if file for image exists
     *
     * @param image Image entity
     * @return True if file exists, false if not
     */
    @Override
    public boolean imageFileExists(Image image) {
        Path path = Paths.get(config.getGalleryPath() + DirUtils.getPath(image));
        return path.toFile().exists();
    }

    /**
     * Resize image
     */
    private BufferedImage resize(BufferedImage image, Integer width, Integer height) {
        if (image.getWidth() > image.getHeight()) {
            height = (int) Math.round(1.0 * image.getHeight() / image.getWidth() * width);
        } else {
            width = (int) Math.round(1.0 * image.getWidth() / image.getHeight() * height);
        }

        java.awt.Image tmp = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }


    /**
     * Create file name from image in format ID_WIDTH_HEIGHT_.ORIGINAL_EXTENSION
     */
    private String getFileName(Image image, Integer width, Integer height) {
        String extension = Files.getFileExtension(image.getName());
        StringBuilder sb = new StringBuilder();
        sb.append(image.getId());
        sb.append('_');
        sb.append(width);
        sb.append('_');
        sb.append(height);
        sb.append('.');
        sb.append(extension);
        return sb.toString();
    }
}
