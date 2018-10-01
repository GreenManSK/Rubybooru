package net.greenmanov.anime.rubybooru.database;

import javax.persistence.EntityManager;

/**
 * Interface to provided entity manager for project
 */
public interface IEntityManagerProvider {
    /**
     * Return entity manager
     * @return entity manager
     */
    EntityManager getEntityManager();

    /**
     * Closes entity manager
     */
    void close();
}
