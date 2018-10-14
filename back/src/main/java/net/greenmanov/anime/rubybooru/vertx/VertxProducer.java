package net.greenmanov.anime.rubybooru.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import javax.enterprise.inject.Produces;

/**
 * Produces Vertx object
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class VertxProducer {

    @Produces
    public Vertx createVertx() {
        VertxOptions options = new VertxOptions();
        options.setMaxWorkerExecuteTime(Long.MAX_VALUE);
        return Vertx.vertx(options);
    }
}
