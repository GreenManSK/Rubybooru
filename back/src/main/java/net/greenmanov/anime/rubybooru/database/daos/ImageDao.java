package net.greenmanov.anime.rubybooru.database.daos;

import net.greenmanov.anime.rubybooru.database.entities.Image;

/**
 * DAO for Image entity
 */
public class ImageDao extends AJpaDao {
    public ImageDao() {

    }

    /**
     * Return Image entity by id
     *
     * @param id Image id
     * @return Image entity
     */
    public Image getById(long id) {
        return em.find(Image.class, id);
    }
}
