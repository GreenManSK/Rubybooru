package net.greenmanov.anime.rurybooru.rest.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.greenmanov.anime.rurybooru.persistance.RubybooruConfig;
import net.greenmanov.anime.rurybooru.rest.controllers.ControllersPackage;
import net.greenmanov.anime.rurybooru.service.configuration.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import javax.naming.ldap.PagedResultsControl;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * WebContext for REST API
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@EnableWebMvc
@Configuration
@Import({ServiceConfiguration.class})
@ComponentScan(basePackageClasses = {ControllersPackage.class})
public class RestWebContext implements WebMvcConfigurer {
    private final static Integer DEFAULT_CACHE_PERIOD = 60 * 60 * 24 * 365; //Seconds

    @Autowired
    private RubybooruConfig config;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AllowOriginInterceptor());
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    @Primary
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH));

        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/tmp/**")
                .addResourceLocations("file:/" + config.getServerTmpPath())
                .setCachePeriod(DEFAULT_CACHE_PERIOD);
    }
}
