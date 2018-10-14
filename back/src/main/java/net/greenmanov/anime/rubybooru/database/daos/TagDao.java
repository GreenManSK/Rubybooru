package net.greenmanov.anime.rubybooru.database.daos;

import com.querydsl.jpa.impl.JPAQuery;
import net.greenmanov.anime.rubybooru.database.entities.QTag;
import net.greenmanov.anime.rubybooru.database.entities.Tag;

import java.util.List;

/**
 * DAO for Tag entity
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class TagDao extends AJpaDao {
    private final static QTag TAG = QTag.tag;

    public TagDao() {
    }

    /**
     * Return Tag entity by id
     *
     * @param id Tag id
     * @return Tag entity
     */
    public Tag getById(long id) {
        return em.find(Tag.class, id);
    }

    /**
     * Get all tags
     * @return List of all tags
     */
    public List<Tag> getAll() {
        return new JPAQuery<>(em).select(TAG).from(TAG).fetch();
    }

    /**
     * Saves image into database
     *
     * @param tag Tag entity
     */
    public void create(Tag tag) {
        this.transaction((em) -> em.persist(tag));
    }

    /**
     * Remove image from database
     *
     * @param tag Tag entity
     */
    public void remove(Tag tag) {
        this.transaction((em) -> em.remove(tag));
    }
}
