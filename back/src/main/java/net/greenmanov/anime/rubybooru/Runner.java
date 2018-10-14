package net.greenmanov.anime.rubybooru;

import io.vertx.core.*;
import net.greenmanov.anime.rubybooru.database.DatabaseVerticle;
import net.greenmanov.anime.rubybooru.parser.ParserVerticle;
import net.greenmanov.anime.rubybooru.server.ServerVerticle;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rubybooru runner
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
final public class Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class.getName());

    static { //runs when the main class is loaded.
        System.setProperty("org.jboss.logging.provider", "slf4j");
    }

    private Vertx vertx;

    @Inject
    private DatabaseVerticle databaseVerticle;

    @Inject
    private ServerVerticle serverVerticle;

    @Inject
    private ParserVerticle parserVerticle;

    @Inject
    public Runner(Vertx vertx) {
        this.vertx = vertx;
    }

    public static void main(String[] args) {
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
        LOGGER.info("Deploying database verticle");
        DeploymentOptions options = new DeploymentOptions().setWorker(true);
        vertx.deployVerticle(databaseVerticle, options, res -> {
            if (res.succeeded()) {
                List<Future> futures = new ArrayList<>();
                for (Map.Entry<Verticle, DeploymentOptions> entry : getVerticles().entrySet()) {
                    Verticle verticle = entry.getKey();
                    LOGGER.info("Deploying verticle " + verticle.getClass().getName());
                    Future future = Future.future();
                    vertx.deployVerticle(verticle, entry.getValue(), res2 -> future.complete());
                    futures.add(future);
                }
                CompositeFuture.all(futures).setHandler(res2 -> {
                    LOGGER.info("Deploying Server verticle");
                    vertx.deployVerticle(serverVerticle);
                });
            } else {
                LOGGER.info("Closing application");
                System.exit(1);
            }
        });
    }

    private Map<Verticle, DeploymentOptions> getVerticles() {
        Map<Verticle, DeploymentOptions> subVericles = new HashMap<>();
        subVericles.put(parserVerticle, new DeploymentOptions().setWorker(true));
        return subVericles;
    }
}
