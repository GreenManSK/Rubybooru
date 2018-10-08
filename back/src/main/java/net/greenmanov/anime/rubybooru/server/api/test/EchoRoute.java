package net.greenmanov.anime.rubybooru.server.api.test;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import net.greenmanov.anime.rubybooru.server.api.JsonRoute;
import net.greenmanov.anime.rubybooru.server.api.annotation.RouteURL;

import java.util.Map;

@RouteURL(url = "/test/echo/", method = HttpMethod.POST)
final public class EchoRoute extends JsonRoute {
    public EchoRoute() {
    }

    /**
     * Handle test request and return body of test response
     *
     * @param body   JsonObject from request
     * @param params Parameters
     * @return JsonObject response
     */
    @Override
    protected JsonObject handle(JsonObject body, Map<String, Object> params) {
        return body;
    }
}
