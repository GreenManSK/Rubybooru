package net.greenmanov.anime.rubybooru.sync.configuration;

import net.greenmanov.anime.rurybooru.persistance.RubybooruApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Class SyncConfiguration
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Configuration
@Import(RubybooruApplicationContext.class)
//@ComponentScan(basePackageClasses = {TagServiceImpl.class, TagFacadeImpl.class})
public class SyncConfiguration {
}
