package net.greenmanov.anime.rubybooru.database.daos;

import net.greenmanov.anime.rubybooru.database.MainDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.function.Consumer;

/**
 * Provides basic transaction needs for JPA DAO
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
abstract public class AJpaDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(AJpaDao.class.getName());

    @Inject
    @MainDatabase
    protected EntityManager em;

    /**
     * Execute action in transaction
     *
     * @param action Action
     */
    protected void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            LOGGER.error("Error while executing transaction", e);
            throw e;
        }
    }
}
