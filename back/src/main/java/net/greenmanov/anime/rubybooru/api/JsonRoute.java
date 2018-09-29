package net.greenmanov.anime.rubybooru.api;


import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Routes used for test api
 */
abstract public class JsonRoute extends ARoute {
    private static final Logger LOGGER = LogManager.getLogger(IRoute.class);
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
            LOGGER.printf(Level.WARN, "Invalid JSON for route %s", request.uri());
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
