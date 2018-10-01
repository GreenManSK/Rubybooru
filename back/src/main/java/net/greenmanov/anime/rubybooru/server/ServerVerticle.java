package net.greenmanov.anime.rubybooru.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import net.greenmanov.anime.rubybooru.Configuration;
import net.greenmanov.anime.rubybooru.Runner;
import net.greenmanov.anime.rubybooru.api.Router;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Verticle for running API server
 */
final public class ServerVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LogManager.getLogger(Runner.class.getName());

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        LOGGER.info("Starting the server...");
        HttpServerOptions serverOptions = Configuration.getServerOptions();
        if (serverOptions == null) {
            LOGGER.error("Could not start the server. Missing configuration.");
        }
        HttpServer server = vertx.createHttpServer(serverOptions);

        Router router = new Router(vertx);
        router.loadAnnotatedRoutes();
        server.requestHandler(router.getRouter()::accept).listen(res -> {
            if (res.succeeded()) {
                LOGGER.printf(Level.INFO,
                        "Server listening: %s://%s:%d",
                        serverOptions.isSsl() ? "https" : "http",
                        serverOptions.getHost(),
                        serverOptions.getPort()
                );
                startFuture.complete();
            } else {
                LOGGER.error("Couldn't start server");
                startFuture.fail(res.cause());
            }
        });
    }
}
