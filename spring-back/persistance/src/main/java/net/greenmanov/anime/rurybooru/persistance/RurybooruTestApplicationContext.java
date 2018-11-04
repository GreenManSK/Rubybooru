package net.greenmanov.anime.rurybooru.persistance;

import net.greenmanov.anime.rurybooru.persistance.dao.DaoPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Application context for Rurybooru
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@PropertySource("classpath:configuration.example.properties")
@ComponentScan(basePackageClasses = {DaoPackage.class})
public class RurybooruTestApplicationContext extends RubybooruApplicationContext {

    @Override
    protected Properties hibernateProperties() {
        Properties properties =  super.hibernateProperties();
        properties.setProperty("hibernate.connection.driver_class", "org.apache.derby.jdbc.EmbeddedDriver");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.DerbyTenSevenDialect");
        return properties;
    }

    @Bean
    @Override
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.DERBY).build();
    }
}
