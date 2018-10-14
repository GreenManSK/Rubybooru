package net.greenmanov.anime.rubybooru.parser;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
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

    @Inject
    private IDirectoryParser parser;

    @Override
    public void start(Future<Void> startFuture) {
        LOGGER.info("Parsing sorted image directories...");
        Path imageRoot = Configuration.getImageRoot();
        if (imageRoot == null) {
            startFuture.fail("Can't parse dirs without root dir");
            return;
        }
        parser.parseDir(imageRoot, true, false, startFuture);
    }
}