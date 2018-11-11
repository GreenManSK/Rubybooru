package net.greenmanov.anime.rurybooru.service;

import net.greenmanov.anime.rurybooru.persistance.entity.Image;

/**
 * Class ImageFileService
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface ImageFileService {

    /**
     * Resize image to provided resolution without ration change. Larger size of the image will fit provided resolution
     *
     * @param image  Image entity
     * @param width  Width
     * @param height Height
     * @return File name of resized image in tmp directory
     */
    String resize(Image image, Integer width, Integer height);

    /**
     * Open image in file explorer
     *
     * @param image Image entity
     */
    void open(Image image);
}
