package net.greenmanov.anime.rubybooru.server.api;


import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Routes used for test api
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
abstract public class JsonRoute extends ARoute {
    private static final Logger LOGGER = LoggerFactory.getLogger(IRoute.class);
    private static final String CONTENT_TYPE = "application/test";

    /**
     * Handle request with parameters of route decoded from routingContext
     *
     * @param request  Request
     * @param response Response
     * @param body     Request body
     * @param params   Parameters
     */
    protected void handle(
            HttpServerRequest request,
            HttpServerResponse response,
            Buffer body,
            Map<String, Object> params) {
        JsonObject json;
        try {
            json = body.toJsonObject();
        } catch (Exception e) {
            LOGGER.warn("Invalid JSON for route {}", request.uri());
            response.setStatusCode(400);
            response.setStatusMessage("Invalid JSON");
            response.end();
            return;
        }
        response.putHeader("content-type", CONTENT_TYPE);
        response.end(handle(json, params).toString());
    }

    /**
     * Handle test request and return body of test response
     *
     * @param body   JsonObject from request
     * @param params Parameters
     * @return JsonObject response
     */
    abstract protected JsonObject handle(JsonObject body, Map<String, Object> params);
}