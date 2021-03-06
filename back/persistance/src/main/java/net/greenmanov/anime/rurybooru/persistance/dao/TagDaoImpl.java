package net.greenmanov.anime.rurybooru.persistance.dao;

import com.querydsl.jpa.impl.JPAQuery;
import net.greenmanov.anime.rurybooru.persistance.entity.QImage;
import net.greenmanov.anime.rurybooru.persistance.entity.QTag;
import net.greenmanov.anime.rurybooru.persistance.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Class TagDaoImpl
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Repository
public class TagDaoImpl implements TagDao {
    private final static QTag TAG = QTag.tag;
    private final static QImage IMAGE = QImage.image;

    @PersistenceContext
    private EntityManager em;

    /**
     * Find tag by its id
     *
     * @param id of the tag
     * @return tag or {@code null}, if there is no tag with requested id
     */
    @Override
    public Tag getById(long id) {
        return em.find(Tag.class, id);
    }

    /**
     * Find all tags
     *
     * @return List of all tags
     */
    @Override
    public List<Tag> getAll() {
        return new JPAQuery<>(em).select(TAG).from(TAG).fetch();
    }

    /**
     * Return number of images that have provided tag
     *
     * @param tag Tag entity
     * @return number of images with tag
     */
    @Override
    public long getTagUseCount(Tag tag) {
        return new JPAQuery<>(em).from(IMAGE).where(IMAGE.tags.contains(tag)).fetchCount();
    }

    /**
     * Stores new tag
     *
     * @param tag to be created
     * @throws NullPointerException         if the {@code tag} is {@code null}
     * @throws ConstraintViolationException if the {@code tag} has required field set as {@code null}
     */
    @Override
    public void create(Tag tag) {
        em.persist(tag);
    }

    /**
     * Updates existing tag according to the id
     *
     * @param tag to be updated
     * @throws NullPointerException         if the {@code tag} is {@code null}
     * @throws ConstraintViolationException if the {@code tag} has required field set as {@code null}
     */
    @Override
    public void update(Tag tag) {
        em.merge(tag);
    }

    /**
     * Removes the tag
     *
     * @param tag to be deleted
     * @throws NullPointerException if the {@code tag} is {@code null}
     */
    @Override
    public void remove(Tag tag) {
        em.remove(tag);
    }
}
