package net.greenmanov.anime.rubybooru.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import net.greenmanov.anime.rubybooru.Configuration;
import net.greenmanov.anime.rubybooru.server.api.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Verticle for running API server
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
final public class ServerVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerVerticle.class.getName());

    @Override
    public void start(Future<Void> startFuture) {
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
                LOGGER.info(
                        "Server listening: {}://{}:{}",
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
