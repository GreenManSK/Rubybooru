package net.greenmanov.anime.rubybooru.database.daos;

import net.greenmanov.anime.rubybooru.database.entities.Tag;

/**
 * DAO for Tag entity
 */
public class TagDao extends AJpaDao {
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
     * Saves image into database
     *
     * @param tag Tag entity
     */
    public void save(Tag tag) {
        em.persist(tag);
    }

    /**
     * Remove image from database
     *
     * @param tag Tag entity
     */
    public void remove(Tag tag) {
        em.remove(tag);
    }
}
