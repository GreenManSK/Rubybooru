package net.greenmanov.anime.rubybooru.database.migrations;

import io.vertx.core.Future;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import net.greenmanov.anime.rubybooru.database.IEntityManagerProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.internal.SessionImpl;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class for running liquibase migrations
 */
final public class LiquibaseMigration {
    private static final Logger LOGGER = LogManager.getLogger(LiquibaseMigration.class.getName());
    private IEntityManagerProvider provider;

    public LiquibaseMigration(IEntityManagerProvider provider) {
        this.provider = provider;
    }

    /**
     * Run liquibase migrations
     *
     * @param future Future when liquibase ends
     */
    public void run(Future<Void> future) {
        try (Connection connection = provider.getEntityManager().unwrap(SessionImpl.class).connection()) {
            Database database = null;
            Liquibase liquibase = null;

            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            liquibase = new Liquibase("dbChangelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update("test");
            future.complete();
        } catch (LiquibaseException | SQLException e) {
            LOGGER.error("Couldn't run liquibase", e);
            future.fail(e);
        }
    }
}
