package net.greenmanov.anime.rubybooru.sync;

import net.greenmanov.anime.rubybooru.sync.configuration.SyncConfiguration;
import net.greenmanov.anime.rubybooru.sync.parser.DirectoryParser;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

/**
 * Main class for sync module
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@PropertySource("classpath:configuration.properties")
@Component
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class.getName());

    @Autowired
    private Environment env;

    @Autowired
    private DirectoryParser directoryParser;

    public static void main(String[] args) throws ParseException {
        try (AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(SyncConfiguration.class)) {
            Main main = ctx.getBean(Main.class);
            main.start(args);
            System.exit(0);
        }
    }

    private void start(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(getOptions(), args);

        boolean dataUpdate = cmd.hasOption("d");
        String pathProperty = env.getProperty("sync.galleryPath");
        if (pathProperty == null) {
            LOGGER.error("sync.galleryPath property missing in configuration");
            return;
        }
        directoryParser.parseDir(Paths.get(env.getProperty("sync.galleryPath")), true, dataUpdate);
    }

    private Options getOptions() {
        Options options = new Options();
        options.addOption("d", "dataUpdate", false, "Update existing images in database");
        return options;
    }
}
