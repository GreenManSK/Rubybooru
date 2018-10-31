package net.greenmanov.anime.rubybooru.database.daos;

import net.greenmanov.anime.rubybooru.database.entities.Image;

/**
 * DAO for Image entity
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
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

    /**
     * Saves image into database
     *
     * @param image Image entity
     */
    public void create(Image image) {
        this.transaction((em) -> em.persist(image));
    }

    /**
     * Remove image from database
     *
     * @param image Image entity
     */
    public void remove(Image image) {
        this.transaction((em) -> em.remove(image));
    }
}