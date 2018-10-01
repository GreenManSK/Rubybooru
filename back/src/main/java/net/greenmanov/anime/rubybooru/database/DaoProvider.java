package net.greenmanov.anime.rubybooru.database;

import net.greenmanov.anime.rubybooru.database.daos.AJpaDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used for providing of DAOs
 */
public class DaoProvider {
    private static final Logger LOGGER = LogManager.getLogger(DaoProvider.class.getName());

    private static Map<Class, AJpaDao> daos = new HashMap<>();
    private static IEntityManagerProvider provider;

    private DaoProvider() {
    }

    /**
     * Creates and return DAO of needed type.
     * Have to be used after provider is set
     *
     * @param type Type of DAO
     * @param <T>  Implementation of AJpaDao
     * @return Created dao
     * @throws IllegalStateException If provider was not set before
     */
    @SuppressWarnings("unchecked")
    public static <T extends AJpaDao> T getDao(Class<T> type) throws IllegalStateException {
        if (provider == null) {
            throw new IllegalStateException("Need to set entity manager provider for DaoProvider");
        }
        if (!daos.containsKey(type)) {
            try {
                Constructor<T> constructor = type.getConstructor(EntityManager.class);
                daos.put(type, constructor.newInstance(provider.getEntityManager()));
            } catch (NoSuchMethodException e) {
                LOGGER.error(type.getName() + " do not have right constructor.", e);
            } catch (IllegalAccessException e) {
                LOGGER.error(type.getName() + " do not have public constructor.", e);
            } catch (InstantiationException e) {
                LOGGER.error(type.getName() + " instance can't be created.", e);
            } catch (InvocationTargetException e) {
                LOGGER.error(e);
            }
        }

        return (T) daos.get(type);
    }

    /**
     * Set entity manager provider
     *
     * @param provider Provider
     */
    public static void setProvider(IEntityManagerProvider provider) {
        DaoProvider.provider = provider;
    }
}
