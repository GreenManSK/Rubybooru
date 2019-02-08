package net.greenmanov.anime.rurybooru.service;

import com.querydsl.core.types.dsl.ComparableExpressionBase;
import net.greenmanov.anime.rurybooru.api.enums.Order;
import net.greenmanov.anime.rurybooru.persistance.dao.ImageDao;
import net.greenmanov.anime.rurybooru.persistance.entity.Image;
import net.greenmanov.anime.rurybooru.persistance.entity.QImage;
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
    private final static QImage IMAGE = QImage.image;

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
     * Find all images with provided IDs
     *
     * @param ids List of image ids
     * @return List of iamges
     */
    @Override
    public List<Image> getByIds(List<Long> ids) {
        return imageDao.getByIds(ids);
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
        return imageDao.getImages(tagIds, dirId, getSortColumn(order), order == Order.NEWEST, perPage, page);
    }

    /**
     * Retrieve number of images that satisfy provided parameters
     *
     * @param tagIds List of tag IDs that image have to have or {@code null} if tag filtering is not needed
     * @param dirId  ID of the dir that contains images or {@code null} if any dir is ok
     * @return Number of images
     */
    @Override
    public Long getImagesCount(List<Long> tagIds, Long dirId) {
        return imageDao.getImagesCount(tagIds, dirId);
    }

    private ComparableExpressionBase getSortColumn(Order order) {
        switch (order) {
            case WIDTH:
                return IMAGE.width;
            case HEIGHT:
                return IMAGE.height;
            case NEWEST:
            case OLDEST:
                return IMAGE.date;
            default:
                return null;
        }
    }

    /**
     * Stores new image
     *
     * @param image to be created
     */
    @Override
    public void create(Image image) {
        imageDao.create(image);
    }

    /**
     * Removes the image
     *
     * @param image to be deleted
     */
    @Override
    public void remove(Image image) {
        imageDao.remove(image);
    }
}
