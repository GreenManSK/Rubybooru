package net.greenmanov.anime.rubybooru.server.api.test;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import net.greenmanov.anime.rubybooru.server.api.ARoute;
import net.greenmanov.anime.rubybooru.server.api.ParamType;
import net.greenmanov.anime.rubybooru.server.api.annotation.RouteParam;
import net.greenmanov.anime.rubybooru.server.api.annotation.RouteURL;

import java.util.Map;

/**
 * Route for echoing GET parameter
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@RouteURL(url = "/test/echo/:param/", method = HttpMethod.GET)
@RouteParam(name = EchoParamRoute.PARAM, type = ParamType.STRING)
final public class EchoParamRoute extends ARoute {

    public static final String PARAM = "param";

    public EchoParamRoute() {
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
        response.end((String) params.get(PARAM));
    }
}