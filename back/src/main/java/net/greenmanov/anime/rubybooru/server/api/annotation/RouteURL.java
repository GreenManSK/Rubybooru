package net.greenmanov.anime.rubybooru.server.api.annotation;

import io.vertx.core.http.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify routing URL for router
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RouteURL {
    String url();

    HttpMethod method() default HttpMethod.GET;
}
