package net.greenmanov.anime.rubybooru.server.api.parser;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import net.greenmanov.anime.rubybooru.parser.ParserVerticle;
import net.greenmanov.anime.rubybooru.server.api.ARoute;
import net.greenmanov.anime.rubybooru.server.api.ParamType;
import net.greenmanov.anime.rubybooru.server.api.annotation.RouteParam;
import net.greenmanov.anime.rubybooru.server.api.annotation.RouteURL;

import java.util.Map;

/**
 * Class LoadDirs
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@RouteURL(url = "/parser/load-dirs/", method = HttpMethod.GET)
@RouteParam(name = LoadDirs.DATA_UPDATE, type = ParamType.BOOL)
public class LoadDirs extends ARoute {
    public static final String DATA_UPDATE = "dataUpdate";

    public LoadDirs(Vertx vertx) {
        this.vertx = vertx;
    }

    /**
     * Handle request with parameters of route decoded from routingContext
     *
     * @param request  Request
     * @param response Response
     * @param body     Request body
     * @param params   Parameters
     */
    @Override
    protected void handle(HttpServerRequest request, HttpServerResponse response, Buffer body, Map<String, Object> params) {
        vertx.eventBus().send(ParserVerticle.EVENT_BUS_ADDRESS, params.get(DATA_UPDATE) == null ? false :
                params.get(DATA_UPDATE), result -> {
            if (result.succeeded()) {
                respond(response, new JsonObject());
            } else {
                respond(response, 400, new JsonObject().put("message", result.cause().getMessage()));
            }
        });
    }
}
