package net.greenmanov.anime.rubybooru.database;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import net.greenmanov.anime.rubybooru.database.migrations.LiquibaseMigration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Verticle for working with database
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class DatabaseVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseVerticle.class.getName());

    @Inject
    private LiquibaseMigration liquibaseMigration;

    @Override
    public void start(Future<Void> startFuture) {
        LOGGER.info("Running liquibase migrations");
        Future<Void> liquibaseFuture = Future.future();
        liquibaseMigration.run(liquibaseFuture);

        liquibaseFuture.setHandler(result -> {
            if (result.failed()) {
                String e = "Couldn't start database verticle because of liquibase fail";
                LOGGER.error(e);
                startFuture.fail(e);
            }
            startFuture.complete();
        });
    }
}
