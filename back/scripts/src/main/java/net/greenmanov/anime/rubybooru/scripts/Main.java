package net.greenmanov.anime.rubybooru.scripts;

import net.greenmanov.anime.rubybooru.scripts.configuration.SyncConfiguration;
import net.greenmanov.anime.rubybooru.scripts.parser.DirectoryParser;
import net.greenmanov.anime.rubybooru.scripts.tmp.TmpCreator;
import net.greenmanov.anime.rurybooru.persistance.RubybooruConfig;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Main class for scripts module
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Component
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class.getName());

    @Autowired
    private RubybooruConfig config;

    @Autowired
    private DirectoryParser directoryParser;

    @Autowired
    private TmpCreator tmpCreator;

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

        if (cmd.hasOption("s")) {
            boolean dataUpdate = cmd.hasOption("d");
            String pathProperty = config.getGalleryPath();
            if (pathProperty == null) {
                LOGGER.error("scripts.galleryPath property missing in configuration");
                return;
            }
            directoryParser.parseDir(Paths.get(pathProperty), true, dataUpdate);
        } else if (cmd.hasOption("t")) {
            String tmpPathProperty = config.getServerTmpPath();
            if (tmpPathProperty == null) {
                LOGGER.error("server.imgTmpPath property missing in configuration");
                return;
            }
            Path tmpPath = Paths.get(tmpPathProperty);
            boolean deleteOld = cmd.hasOption("d");
            if (deleteOld) {
                try {
                    LOGGER.info("Deleting tmp images");
                    tmpCreator.emptyTmpDirectory(tmpPath);
                    LOGGER.info("Tmp images deleted");
                } catch (IOException e) {
                    LOGGER.error("Couldn't delete tmp files", e);
                    return;
                }
            }
            String[] size = cmd.getOptionValue("t").split("x");
            tmpCreator.createTmps(tmpPath, Integer.valueOf(size[0]), Integer.valueOf(size[1]));
        } else {
            LOGGER.error("No script parameters provided");
        }
    }

    private Options getOptions() {
        Options options = new Options();

        options.addOption("s", "sync", false, "Synchronize images and database");
        options.addOption("t", "tmp", true, "Generate tmp images, argument is [WxH] in px");

        options.addOption("d", "dataUpdate", false, "Update existing"); // Works for sync and tmp

        return options;
    }
}
