package net.greenmanov.anime.rubybooru.sync.configuration;

import net.greenmanov.anime.rubybooru.sync.Main;
import net.greenmanov.anime.rurybooru.persistance.RubybooruApplicationContext;
import net.greenmanov.anime.rurybooru.service.DirService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Class SyncConfiguration
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Configuration
@Import(RubybooruApplicationContext.class)
@ComponentScan(basePackageClasses = {Main.class, DirService.class})
public class SyncConfiguration {
}
