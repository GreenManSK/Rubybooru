package net.greenmanov.anime.rubybooru.server.api;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.handler.BodyHandler;
import net.greenmanov.anime.rubybooru.server.api.annotation.RouteURL;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Used for finding all routes with RouteURL annotation to add them to the server router
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
final public class Router {
    private static final Logger LOGGER = LoggerFactory.getLogger(Router.class);
    private io.vertx.ext.web.Router router;

    /**
     * Creates new router
     *
     * @param vertx Vertx object
     */
    public Router(Vertx vertx) {

        router = io.vertx.ext.web.Router.router(vertx);
        router.route().handler(BodyHandler.create());
    }

    /**
     * Load all IRoute routes from api package with RouteURL annotation
     */
    public void loadAnnotatedRoutes() {
        Reflections ref = new Reflections(getClass().getPackage().getName());
        for (Class<?> cl : ref.getTypesAnnotatedWith(RouteURL.class)) {
            RouteURL routeURL = cl.getAnnotation(RouteURL.class);
            if (!IRoute.class.isAssignableFrom(cl)) {
                LOGGER.error("Class {} uses RouteURL annotation but do not implement IRoute interface", cl.getName());
                continue;
            }
            try {
                IRoute route = (IRoute) cl.newInstance();
                addRoute(routeURL.url(), routeURL.method(), route);
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.error("Can't create instance of " + cl.getName() + " class", e);
            }
        }
    }

    /**
     * Adds a route to the router
     *
     * @param url    Route URL
     * @param method Route method
     * @param route  Route object
     */
    public void addRoute(String url, HttpMethod method, IRoute route) {
        LOGGER.info("Adding route: {}", url);
        router.route(method, url).handler(route::handel);
    }

    /**
     * Return vertx router for server
     *
     * @return Vertx router
     */
    public io.vertx.ext.web.Router getRouter() {
        return router;
    }
}
