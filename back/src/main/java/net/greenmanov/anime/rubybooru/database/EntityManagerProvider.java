package net.greenmanov.anime.rubybooru.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Persistence entity manager provider
 */
final public class EntityManagerProvider implements IEntityManagerProvider {
    private static final String PERSISTENCE_UNIT_NAME = "RubybooruDB";
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public EntityManagerProvider() {
    }

    public EntityManager getEntityManager() {
        if (entityManager != null) {
            return entityManager;
        }
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        entityManager = entityManagerFactory.createEntityManager();
        return entityManager;
    }

    /**
     * Closes entity manager
     */
    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }
}
