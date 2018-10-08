package net.greenmanov.anime.rubybooru.server.api;

import io.vertx.ext.web.RoutingContext;

/**
 * API Route
 * Should use RouteURL annotation to specify its URL
 */
public interface IRoute {
    /**
     * Handle request
     *
     * @param routingContext RoutingContext
     */
    void handel(RoutingContext routingContext);
}
