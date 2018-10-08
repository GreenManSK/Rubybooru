package net.greenmanov.anime.rubybooru.database;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Entity manager producer
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class EntityManagerProducer {
    @Produces
    @MainDatabase
    public EntityManager createEntityManager() {
        return Persistence
                .createEntityManagerFactory("main")
                .createEntityManager();
    }

    public void close(
            @Disposes @MainDatabase EntityManager entityManager) {
        entityManager.close();
    }
}
