package net.greenmanov.anime.rubybooru.database.daos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.function.Consumer;

/**
 * Provides basic transaction needs for JPA DAO
 */
abstract public class AJpaDao {
    private static final Logger LOGGER = LogManager.getLogger(AJpaDao.class.getName());
    protected final EntityManager em;

    public AJpaDao(EntityManager em) {
        this.em = em;
    }

    /**
     * Execute action in transaction
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
            LOGGER.error(e);
            throw e;
        }
    }
}
