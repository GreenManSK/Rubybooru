package net.greenmanov.anime.rurybooru.api.facade;

import net.greenmanov.anime.rurybooru.api.dto.GetImagesDTO;
import net.greenmanov.anime.rurybooru.api.dto.ImageDTO;

import java.util.List;

/**
 * Class ImageFacade
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface ImageFacade {

    /**
     * Retrieve image by id
     *
     * @param id image id
     * @return image or {@code null}, if image with id do not exists
     */
    ImageDTO getById(long id);

    /**
     * Return file name of resized version of image saved in tmp path
     * @param id image id
     * @param width image width
     * @param height image height
     * @return File name
     */
    String getTmpImage(long id, int width, int height);

    /**
     * Return list of images based on parameters of DTO object
     *
     * @param getImagesDTO GetImagesDTO
     * @return List of images satisfying DTO parameters
     */
    List<ImageDTO> getImages(GetImagesDTO getImagesDTO);
}
