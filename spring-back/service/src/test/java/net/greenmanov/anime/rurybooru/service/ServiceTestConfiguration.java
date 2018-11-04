package net.greenmanov.anime.rurybooru.service;

import net.greenmanov.anime.rurybooru.persistance.RurybooruTestApplicationContext;
import net.greenmanov.anime.rurybooru.service.configuration.ServiceConfiguration;
import net.greenmanov.anime.rurybooru.service.facade.TagFacadeImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Class ServiceTestConfiguration
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Configuration
@Import(RurybooruTestApplicationContext.class)
@ComponentScan(basePackageClasses = {TagServiceImpl.class, TagFacadeImpl.class})
public class ServiceTestConfiguration extends ServiceConfiguration {

}
