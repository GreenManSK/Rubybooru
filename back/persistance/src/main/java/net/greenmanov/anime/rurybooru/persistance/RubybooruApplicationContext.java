package net.greenmanov.anime.rurybooru.persistance;

import net.greenmanov.anime.rurybooru.persistance.dao.DaoPackage;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.persistence.EntityManagerFactory;
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
@PropertySource("classpath:configuration.properties")
@ComponentScan(basePackageClasses = {DaoPackage.class, RubybooruConfig.class})
public class RubybooruApplicationContext {

    @Autowired
    private Environment env;

    @Autowired
    private RubybooruConfig config;

    @Bean
    public RubybooruConfig createConfiguration() {
        return new RubybooruConfig(env);
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean jpaFactoryBean = new LocalContainerEntityManagerFactoryBean();
        jpaFactoryBean.setDataSource(dataSource());
        jpaFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        jpaFactoryBean.setLoadTimeWeaver(instrumentationLoadTimeWeaver());
        jpaFactoryBean.setJpaProperties(hibernateProperties());

        jpaFactoryBean.setPersistenceUnitName("default");
        jpaFactoryBean.setPackagesToScan("net.greenmanov.anime.rurybooru.persistance.entity");

        return jpaFactoryBean;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor postProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public LoadTimeWeaver instrumentationLoadTimeWeaver() {
        return new InstrumentationLoadTimeWeaver();
    }

    @Bean
    public DataSource dataSource() {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(config.getJdbcDriver());
        dataSource.setUrl(config.getJdbcUrl());
        dataSource.setUsername(config.getJdbcUser());
        dataSource.setPassword(config.getJdbcPassword());

        return dataSource;
    }

    protected Properties hibernateProperties() {
        final Properties hibernateProperties = new Properties();

        String[] properties = new String[]{
                "hibernate.dialect",
                "hibernate.hbm2ddl.auto",
                "hibernate.connection.useUnicode",
                "hibernate.connection.characterEncoding",
                "hibernate.connection.CharSet"
        };
        for (String property : properties) {
            hibernateProperties.setProperty(property, config.getProperty(property));
        }

        return hibernateProperties;
    }
}
