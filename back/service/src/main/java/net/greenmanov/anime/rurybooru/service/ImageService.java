package net.greenmanov.anime.rurybooru.service;

import net.greenmanov.anime.rurybooru.api.enums.Order;
import net.greenmanov.anime.rurybooru.persistance.entity.Image;
import net.greenmanov.anime.rurybooru.persistance.filters.ImageFilter;

import java.util.List;

/**
 * Class ImageService
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface ImageService {
    /**
     * Find image by its id
     *
     * @param id of the image
     * @return image or {@code null}, if there is no image with requested id
     */
    Image getById(long id);

    /**
     * Find all images with provided IDs
     *
     * @param ids List of image ids
     * @return List of iamges
     */
    List<Image> getByIds(List<Long> ids);

    /**
     * Find all images
     *
     * @return List of all images
     */
    List<Image> getAll();

    /**
     * Retrieve images that satisfy provided parameters. Pages are counted from 1
     *
     * @param tagIds  List of tag IDs that image have to have or {@code null} if tag filtering is not needed
     * @param filters Image filters
     * @param dirId   ID of the dir that contains images or {@code null} if any dir is ok
     * @param order   Specify ordering of images for pagination
     * @param perPage Number of images per page - maximal number of images in list
     * @param page    Number of page that should be returned (skips first {@code (page - 1) * perPage} images)
     * @return List of images
     */
    List<Image> getImages(List<Long> tagIds, List<ImageFilter> filters, Long dirId, Order order, Integer perPage, Integer page);

    /**
     * Retrieve number of images that satisfy provided parameters
     *
     * @param tagIds  List of tag IDs that image have to have or {@code null} if tag filtering is not needed
     * @param filters Image filters
     * @param dirId   ID of the dir that contains images or {@code null} if any dir is ok
     * @return Number of images
     */
    Long getImagesCount(List<Long> tagIds, List<ImageFilter> filters, Long dirId);

    /**
     * Stores new image
     *
     * @param image to be created
     */
    void create(Image image);

    /**
     * Removes the image
     *
     * @param image to be deleted
     */
    void remove(Image image);
}
