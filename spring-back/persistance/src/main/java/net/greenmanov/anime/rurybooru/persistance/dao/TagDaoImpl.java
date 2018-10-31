package net.greenmanov.anime.rurybooru.persistance.dao;

import com.querydsl.jpa.impl.JPAQuery;
import net.greenmanov.anime.rurybooru.persistance.entity.QTag;
import net.greenmanov.anime.rurybooru.persistance.entity.Tag;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Find tag by its id
     *
     * @param id of the tag
     * @return tag or {@code null}, if there is no tag with requested id
     */
    @Override
    public Tag getById(long id) {
        return sessionFactory.getCurrentSession().find(Tag.class, id);
    }

    /**
     * Find all tags
     *
     * @return List of all tags
     */
    @Override
    public List<Tag> getAll() {
        return new JPAQuery<>(sessionFactory.getCurrentSession()).select(TAG).from(TAG).fetch();
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
        sessionFactory.getCurrentSession().persist(tag);
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
        sessionFactory.getCurrentSession().merge(tag);
    }

    /**
     * Removes the tag
     *
     * @param tag to be deleted
     * @throws NullPointerException if the {@code tag} is {@code null}
     */
    @Override
    public void remove(Tag tag) {
        sessionFactory.getCurrentSession().remove(tag);
    }
}
