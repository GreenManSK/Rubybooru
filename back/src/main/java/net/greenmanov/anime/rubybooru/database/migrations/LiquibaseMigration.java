package net.greenmanov.anime.rubybooru.database.migrations;

import io.vertx.core.Future;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import net.greenmanov.anime.rubybooru.database.MainDatabase;
import org.hibernate.internal.SessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class for running liquibase migrations
 */
final public class LiquibaseMigration {
    private static final Logger LOGGER = LoggerFactory.getLogger(LiquibaseMigration.class.getName());
    private static final String CHANGELOG_PATH = "liquibase/changelog.xml";

    private final EntityManager em;

    @Inject
    public LiquibaseMigration(@MainDatabase EntityManager em) {
        this.em = em;
    }

    /**
     * Run liquibase migrations
     *
     * @param future Future when liquibase ends
     */
    public void run(Future<Void> future) {
        try (Connection connection = em.unwrap(SessionImpl.class).connection()) {
            Database database = null;
            Liquibase liquibase = null;

            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            liquibase = new Liquibase(CHANGELOG_PATH, new ClassLoaderResourceAccessor(), database);
            liquibase.update("test");
            future.complete();
        } catch (LiquibaseException | SQLException e) {
            LOGGER.error("Couldn't run liquibase", e);
            future.fail(e);
        }
    }
}
