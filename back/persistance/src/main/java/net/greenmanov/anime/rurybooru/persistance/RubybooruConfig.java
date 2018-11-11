package net.greenmanov.anime.rurybooru.persistance;

import org.springframework.core.env.Environment;

/**
 * Class RurbybooruConfig
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class RubybooruConfig {

    private Environment env;

    public RubybooruConfig(Environment env) {
        this.env = env;
    }

    public String getGalleryPath() {
        return env.getProperty("sync.galleryPath");
    }

    public String getServerTmpPath() {
        return env.getProperty("server.imgTmpPath");
    }

    public String getJdbcUrl() {
        return env.getProperty("jdbc.url");
    }

    public String getJdbcUser() {
        return env.getProperty("jdbc.user");
    }

    public String getJdbcPassword() {
        return env.getProperty("jdbc.pass");
    }

    public String getJdbcDriver() {
        return env.getProperty("driverClassName");
    }

    public String getProperty(String name) {
        return env.getProperty(name);
    }
}
