package net.greenmanov.anime.rubybooru;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4J2LoggerFactory;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import net.greenmanov.anime.rubybooru.server.ServerVerticle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Rubybooru runner
 */
final public class Runner {
    private static final Logger LOGGER = LogManager.getLogger(Runner.class.getName());

    private Vertx vertx;
    private List<Verticle> verticles = new ArrayList<>();

    @Inject
    public Runner(Vertx vertx) {
        this.vertx = vertx;
    }

    public static void main(String[] args) {
        // Set logger of io.netty to log4j2
        InternalLoggerFactory.setDefaultFactory(Log4J2LoggerFactory.INSTANCE);

        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        Runner runner = container.instance().select(Runner.class).get();
        runner.start();
    }

    /**
     * Create Vertx and deploy verticles
     */
    private void start() {
        LOGGER.info("Initializing application...");
        deployVerticles();
    }

    private void deployVerticles() {
        for (Verticle verticle: verticles) {
            LOGGER.info("Deploying verticle " + verticle.getClass().getName());
            vertx.deployVerticle(verticle);
        }
    }

    @Inject
    public void setServerVerticle(ServerVerticle verticle) {
        verticles.add(verticle);
    }
}
