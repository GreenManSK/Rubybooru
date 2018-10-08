package net.greenmanov.anime.rubybooru.database.daos;

import net.greenmanov.anime.rubybooru.database.entities.Dir;

/**
 * Dao for Dir entity
 */
public class DirDao extends AJpaDao {
    public DirDao() {
    }


    /**
     * Return Dir entity by id
     *
     * @param id Dir id
     * @return Dir entity
     */
    public Dir getById(long id) {
        return em.find(Dir.class, id);
    }

    /**
     * Saves Dir into database
     *
     * @param dir Dir entity
     */
    public void save(Dir dir) {
        em.persist(dir);
    }

    /**
     * Remove Dir from database
     *
     * @param dir Dir entity
     */
    public void remove(Dir dir) {
        em.remove(dir);
    }
}
