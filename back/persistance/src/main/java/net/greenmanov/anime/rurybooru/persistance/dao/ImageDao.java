package net.greenmanov.anime.rurybooru.persistance.dao;

import net.greenmanov.anime.rurybooru.persistance.entity.Image;
import net.greenmanov.anime.rurybooru.persistance.entity.Tag;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Interface ImageDao
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface ImageDao {
    /**
     * Find image by its id
     *
     * @param id of the image
     * @return image or {@code null}, if there is no image with requested id
     */
    Image getById(long id);

    /**
     * Find all images
     *
     * @return List of all images
     */
    List<Image> getAll();

    /**
     * Get all images with provided tag
     *
     * @param tag Image tag
     * @return List of all images with provided tag
     * @throws NullPointerException if the {@code tag} is {@code null}
     */
    List<Image> getByTag(Tag tag);

    /**
     * Stores new image
     *
     * @param image to be created
     * @throws NullPointerException         if the {@code image} is {@code null}
     * @throws ConstraintViolationException if the {@code image} has required field set as {@code null}
     */
    void create(Image image);

    /**
     * Updates existing image according to the id
     *
     * @param image to be updated
     * @throws NullPointerException         if the {@code image} is {@code null}
     * @throws ConstraintViolationException if the {@code image} has required field set as {@code null}
     */
    void update(Image image);

    /**
     * Removes the image
     *
     * @param image to be deleted
     * @throws NullPointerException if the {@code image} is {@code null}
     */
    void remove(Image image);

    /**
     * Retrieve images that satisfy provided parameters. Pages are counted from 1
     *
     * @param tagIds  List of tag IDs that image have to have or {@code null} if tag filtering is not needed
     * @param dirId   ID of the dir that contains images or {@code null} if any dir is ok
     * @param desc    Specify ordering of images for pagination, images are ordered by datetime added
     * @param perPage Number of images per page - maximal number of images in list
     * @param page    Number of page that should be returned (skips first {@code (page - 1) * perPage} images)
     * @return List of images
     */
    List<Image> getImages(List<Long> tagIds, Long dirId, boolean desc, Integer perPage, Integer page);
}