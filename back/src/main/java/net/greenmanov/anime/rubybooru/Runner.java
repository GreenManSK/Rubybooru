package net.greenmanov.anime.rubybooru;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import net.greenmanov.anime.rubybooru.database.DatabaseVerticle;
import net.greenmanov.anime.rubybooru.server.ServerVerticle;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Rubybooru runner
 */
final public class Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class.getName());

    private Vertx vertx;
    private List<Verticle> verticles = new ArrayList<>();
    @Inject
    private DatabaseVerticle databaseVerticle;

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
                for (Verticle verticle : verticles) {
                    LOGGER.info("Deploying verticle " + verticle.getClass().getName());
                    vertx.deployVerticle(verticle);
                }
            } else {
                LOGGER.info("Closing application");
                System.exit(1);
            }
        });
    }

    @Inject
    public void setServerVerticle(ServerVerticle verticle) {
        verticles.add(verticle);
    }
}
