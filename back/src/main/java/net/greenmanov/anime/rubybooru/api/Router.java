package net.greenmanov.anime.rubybooru.api;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.handler.BodyHandler;
import net.greenmanov.anime.rubybooru.api.annotation.RouteURL;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;


/**
 * Used for finding all routes with RouteURL annotation to add them to the server router
 */
final public class Router {
    private static final Logger LOGGER = LogManager.getLogger(Router.class);
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
                LOGGER.printf(
                        Level.ERROR,
                        "Class %s uses RouteURL annotation but do not implement IRoute interface",
                        cl.getName()
                );
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
        LOGGER.printf(Level.INFO,"Adding route: %s", url);
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
