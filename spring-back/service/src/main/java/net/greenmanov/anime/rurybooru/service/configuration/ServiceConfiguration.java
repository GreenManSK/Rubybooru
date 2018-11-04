package net.greenmanov.anime.rurybooru.service.configuration;

import net.greenmanov.anime.rurybooru.api.dto.DirDTO;
import net.greenmanov.anime.rurybooru.api.dto.ImageDTO;
import net.greenmanov.anime.rurybooru.persistance.RubybooruApplicationContext;
import net.greenmanov.anime.rurybooru.persistance.dao.TagDao;
import net.greenmanov.anime.rurybooru.persistance.entity.Dir;
import net.greenmanov.anime.rurybooru.persistance.entity.Image;
import net.greenmanov.anime.rurybooru.persistance.entity.Tag;
import net.greenmanov.anime.rurybooru.service.TagServiceImpl;
import net.greenmanov.anime.rurybooru.service.facade.TagFacadeImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring configuration for service layer
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Configuration
@Import(RubybooruApplicationContext.class)
@ComponentScan(basePackageClasses = {TagServiceImpl.class, TagFacadeImpl.class})
public class ServiceConfiguration {
    @Bean
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(Dir.class, DirDTO.class);
            mapping(Image.class, ImageDTO.class);
            mapping(Tag.class, TagDao.class);
        }
    }
}
