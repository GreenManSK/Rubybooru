package net.greenmanov.anime.rubybooru.vertx;

import io.vertx.core.Vertx;

import javax.enterprise.inject.Produces;

/**
 * Produces Vertx object
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class VertxProducer {

    @Produces
    public Vertx createVertx() {
        return Vertx.vertx();
    }
}
