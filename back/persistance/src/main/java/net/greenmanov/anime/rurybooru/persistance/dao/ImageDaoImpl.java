package net.greenmanov.anime.rurybooru.persistance.dao;

import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQuery;
import net.greenmanov.anime.rurybooru.persistance.entity.Image;
import net.greenmanov.anime.rurybooru.persistance.entity.QImage;
import net.greenmanov.anime.rurybooru.persistance.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Class ImageDaoImpl
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Repository
public class ImageDaoImpl implements ImageDao {
    private final static QImage IMAGE = QImage.image;

    @PersistenceContext
    private EntityManager em;

    /**
     * Find image by its id
     *
     * @param id of the image
     * @return image or {@code null}, if there is no image with requested id
     */
    @Override
    public Image getById(long id) {
        return em.find(Image.class, id);
    }

    /**
     * Find all images
     *
     * @return List of all images
     */
    @Override
    public List<Image> getAll() {
        return new JPAQuery<>(em).select(IMAGE).from(IMAGE).fetch();
    }

    /**
     * Retrieve images that satisfy provided parameters. Pages are counted from 1
     *
     * @param tagIds  List of tag IDs that image have to have or {@code null} if tag filtering is not needed
     * @param dirId   ID of the dir that contains images or {@code null} if any dir is ok
     * @param sortColumn Specify column to sort by
     * @param desc    Specify ordering of images for pagination, images are ordered by datetime added
     * @param perPage Number of images per page - maximal number of images in list
     * @param page    Number of page that should be returned (skips first {@code (page - 1) * perPage} images)
     * @return List of images
     */
    @Override
    public List<Image> getImages(List<Long> tagIds, Long dirId, ComparableExpressionBase sortColumn, boolean desc, Integer perPage,
                                 Integer page) {
        JPAQuery<Image> query = new JPAQuery<>(em).select(IMAGE).from(IMAGE);

        if (tagIds != null) {
            for (Long t : tagIds) {
                query.where(IMAGE.tags.any().id.eq(t));
            }
        }

        if (dirId != null) {
            query.where(IMAGE.parent.id.eq(dirId));
        }

        if (perPage != null) {
            query.limit(perPage);
        }

        if (perPage != null && page != null) {
            query.offset((page - 1) * perPage);
        }

        if (sortColumn != null) {
            query.orderBy(desc ? sortColumn.desc() : sortColumn.asc());
        }

        return query.fetch();
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
        JPAQuery<Image> query = new JPAQuery<>(em).select(IMAGE).from(IMAGE);

        if (tagIds != null) {
            for (Long t : tagIds) {
                query.where(IMAGE.tags.any().id.eq(t));
            }
        }

        if (dirId != null) {
            query.where(IMAGE.parent.id.eq(dirId));
        }

        return query.fetchCount();
    }

    /**
     * Get all images with provided tag
     *
     * @param tag Image tag
     * @return List of all images with provided tag
     * @throws NullPointerException if the {@code tag} is {@code null}
     */
    @Override
    public List<Image> getByTag(Tag tag) {
        return new JPAQuery<>(em)
                .select(IMAGE)
                .from(IMAGE)
                .where(IMAGE.tags.contains(tag))
                .fetch();
    }

    /**
     * Stores new image
     *
     * @param image to be created
     * @throws NullPointerException         if the {@code image} is {@code null}
     * @throws ConstraintViolationException if the {@code image} has required field set as {@code null}
     */
    @Override
    public void create(Image image) {
        em.persist(image);
    }

    /**
     * Updates existing image according to the id
     *
     * @param image to be updated
     * @throws NullPointerException         if the {@code image} is {@code null}
     * @throws ConstraintViolationException if the {@code image} has required field set as {@code null}
     */
    @Override
    public void update(Image image) {
        em.merge(image);
    }

    /**
     * Removes the image
     *
     * @param image to be deleted
     * @throws NullPointerException if the {@code image} is {@code null}
     */
    @Override
    public void remove(Image image) {
        em.remove(image);
    }
}
