package net.greenmanov.anime.rurybooru.service;

import net.greenmanov.anime.rurybooru.api.enums.Order;
import net.greenmanov.anime.rurybooru.persistance.dao.ImageDao;
import net.greenmanov.anime.rurybooru.persistance.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class ImageServiceImpl
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    /**
     * Find image by its id
     *
     * @param id of the image
     * @return image or {@code null}, if there is no image with requested id
     */
    @Override
    public Image getById(long id) {
        return imageDao.getById(id);
    }

    /**
     * Retrieve images that satisfy provided parameters. Pages are counted from 1
     *
     * @param tagIds  List of tag IDs that image have to have or {@code null} if tag filtering is not needed
     * @param dirId   ID of the dir that contains images or {@code null} if any dir is ok
     * @param order   Specify ordering of images for pagination, images are ordered by datetime added
     * @param perPage Number of images per page - maximal number of images in list
     * @param page    Number of page that should be returned (skips first {@code (page - 1) * perPage} images)
     * @return List of images
     */
    @Override
    public List<Image> getImages(List<Long> tagIds, Long dirId, Order order, Integer perPage, Integer page) {
        return imageDao.getImages(tagIds, dirId, order == Order.DESC, perPage, page);
    }
}
