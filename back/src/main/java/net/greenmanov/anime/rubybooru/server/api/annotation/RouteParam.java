package net.greenmanov.anime.rubybooru.server.api.annotation;

import net.greenmanov.anime.rubybooru.server.api.ParamType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify parameter of route
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RouteParam {
    String name();

    ParamType type() default ParamType.STRING;
}
