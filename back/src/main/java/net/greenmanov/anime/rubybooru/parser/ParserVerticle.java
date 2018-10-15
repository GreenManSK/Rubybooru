package net.greenmanov.anime.rubybooru.parser;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import net.greenmanov.anime.rubybooru.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.nio.file.Path;

/**
 * Verticle for parsing directories of sorted images
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
final public class ParserVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParserVerticle.class.getName());

    public static final String EVENT_BUS_ADDRESS = ParserVerticle.class.getName();

    @Inject
    private IDirectoryParser parser;

    private boolean parsing = false;

    @Override
    public void start(Future<Void> startFuture) {
        registerToEventBus();
        startParser(startFuture, false);
    }

    private void registerToEventBus() {
        EventBus eb = vertx.eventBus();
        eb.consumer(EVENT_BUS_ADDRESS, message -> {
            if (parsing) {
                message.fail(400, "Parser is parsing right now");
                return;
            }
            if (message.body() instanceof Boolean) {
                message.reply(true);
                startParser(Future.future(), (Boolean) message.body());
            } else {
                LOGGER.warn("Invalid call to {}, need boolean body", EVENT_BUS_ADDRESS);
                message.fail(400, "Invalid parameter");
            }
        });
    }

    private void startParser(Future<Void> startFuture, boolean dataUpdate) {
        LOGGER.info("Parsing sorted image directories...");
        Path imageRoot = Configuration.getImageRoot();
        if (imageRoot == null) {
            startFuture.fail("Can't parse dirs without root dir");
            return;
        }
        parsing = true;
        Future<Void> future = Future.future();
        future.setHandler(res -> {
            parsing = false;
            startFuture.complete();
        });
        parser.parseDir(imageRoot, true, dataUpdate, future);
    }
}