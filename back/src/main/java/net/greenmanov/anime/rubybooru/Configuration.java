package net.greenmanov.anime.rubybooru;

import io.vertx.core.http.HttpServerOptions;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Provides configurations for all parts of the application
 */
final public class Configuration {
    private static final Logger LOGGER = LogManager.getLogger(Configuration.class.getName());
    private static final String CONFIG_FILE = "/config.properties";

    private static final String PORT_PROPERTY = "server.port";
    private static final String HOST_PROPERTY = "server.host";
    private static final String SSL_PROPERTY = "server.ssl";

    /**
     * Return server or null if config file do not exists
     * @return HttpServerOptions or null
     */
    public static HttpServerOptions getServerOptions() {
        InputStream propertiesStream = Configuration.class.getResourceAsStream(CONFIG_FILE);
        if (propertiesStream == null) {
            LOGGER.printf(Level.ERROR, "You need to create %s file in resources to use the server", CONFIG_FILE);
            return null;
        }
        try {
            Properties configuration = new Properties();
            configuration.load(propertiesStream);

            HttpServerOptions options = new HttpServerOptions();
            options.setPort(Integer.valueOf(configuration.getProperty(PORT_PROPERTY, "8080")));
            options.setHost(configuration.getProperty(HOST_PROPERTY, "localhost"));
            options.setSsl(configuration.getProperty(SSL_PROPERTY, "0").equals("1"));

            return options;
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return null;
    }

}
