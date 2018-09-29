package net.greenmanov.anime.rubybooru.api;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import net.greenmanov.anime.rubybooru.api.annotation.RouteParam;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract implementation of IRoute with support of automatic extraction of parameter using @RouteParam annotation
 */
abstract public class ARoute implements IRoute {
    private static final Logger LOGGER = LogManager.getLogger(IRoute.class);

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
            LOGGER.printf(Level.WARN, "Invalid parameter type on route %s", request.uri());
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
}
