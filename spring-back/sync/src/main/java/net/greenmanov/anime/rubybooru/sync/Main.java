package net.greenmanov.anime.rubybooru.sync;

import net.greenmanov.anime.rubybooru.sync.configuration.SyncConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Class Main
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try (AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(SyncConfiguration.class)) {

            LOGGER.info("Hello info!");
            System.out.println("Hello world!");
        }
    }
}
