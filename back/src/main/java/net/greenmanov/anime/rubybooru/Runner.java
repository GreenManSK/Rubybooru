package net.greenmanov.anime.rubybooru;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4J2LoggerFactory;
import io.vertx.core.Vertx;
import net.greenmanov.anime.rubybooru.server.ServerVerticle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Rubybooru runner
 */
final public class Runner {
    private static final Logger LOGGER = LogManager.getLogger(Runner.class.getName());
    private Vertx vertx;

    public static void main(String[] args) {
        // Set logger of io.netty to log4j2
        InternalLoggerFactory.setDefaultFactory(Log4J2LoggerFactory.INSTANCE);
        (new Runner()).start();
    }

    /**
     * Create Vertx and deploy verticles
     */
    private void start() {
        LOGGER.info("Initializing application...");
        vertx = Vertx.vertx();
        deployVerticles();
    }

    private void deployVerticles() {
        vertx.deployVerticle(ServerVerticle.class.getCanonicalName());
    }
}
