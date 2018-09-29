package net.greenmanov.anime.rubybooru;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Rubybooru server runner
 */
public class ServerRunner {
    private static final Logger LOGGER = LogManager.getLogger(ServerRunner.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Starting the server...");
        Vertx vertx = Vertx.vertx();
        HttpServerOptions serverOptions = Configuration.getServerOptions();
        if (serverOptions == null) {
            LOGGER.error("Could not start the server. Missing configuration.");
        }
        HttpServer server = vertx.createHttpServer(serverOptions);

        Router router = Router.router(vertx);

        server.requestHandler(router::accept).listen();
        LOGGER.printf(Level.INFO,
                "Server listening: %s://%s:%d",
                serverOptions.isSsl() ? "https" : "http",
                serverOptions.getHost(),
                serverOptions.getPort()
        );
    }
}
