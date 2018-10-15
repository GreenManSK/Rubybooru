package net.greenmanov.anime.rubybooru.server.api;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.greenmanov.anime.rubybooru.server.api.annotation.RouteParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract implementation of IRoute with support of automatic extraction of parameter using @RouteParam annotation
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
abstract public class ARoute implements IRoute {
    private static final Logger LOGGER = LoggerFactory.getLogger(IRoute.class);

    protected Vertx vertx;

    /**
     * Handle request
     *
     * @param routingContext RoutingContext
     */
    @Override
    public void handel(RoutingContext routingContext) {
        HttpServerRequest request = routingContext.request();
        HttpServerResponse response = routingContext.response();
        Map<String, Object> params = new HashMap<>();

        try {
            RouteParam[] routeParams = getClass().getAnnotationsByType(RouteParam.class);
            for (RouteParam param : routeParams) {
                String value = request.getParam(param.name());
                params.put(param.name(), param.type().convert(value));

            }
        } catch (ParamType.ConvertException e) {
            LOGGER.warn("Invalid parameter type on route {}", request.uri());
            response.setStatusCode(400);
            response.setStatusMessage("Invalid parameter");
            response.end();
            return;
        }

        handle(request, response, routingContext.getBody(), params);
    }

    /**
     * Handle request with parameters of route decoded from routingContext
     *
     * @param request  Request
     * @param response Response
     * @param body     Request body
     * @param params   Parameters
     */
    abstract protected void handle(
            HttpServerRequest request,
            HttpServerResponse response,
            Buffer body,
            Map<String, Object> params);

    /**
     * Send json object as response with 200 code. If object do not have key code, it's added automatically
     *
     * @param response HttpServerResponse
     * @param object   JsonObject
     */
    protected void respond(HttpServerResponse response, JsonObject object) {
        respond(response, 200, object);
    }

    /**
     * Send json object as response with provided code. If object do not have key code, it's added automatically
     *
     * @param response HttpServerResponse
     * @param code     Response code
     * @param object   JsonObject
     */
    protected void respond(HttpServerResponse response, int code, JsonObject object) {
        response.setStatusCode(code);
        if (object.getInteger("code") == null)
            object.put("code", code);
        response.end(object.toString());
    }
}
