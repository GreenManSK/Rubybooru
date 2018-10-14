package net.greenmanov.anime.rubybooru.parser;

import java.nio.file.Path;
import io.vertx.core.Future;

/**
 * Parser for image directories configured and used by https://github.com/GreenManSK/AnimeImageSorter
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface IDirectoryParser {

    /**
     *
     * @param directory Root directory
     * @param recursive Specify if subdirectories should be also parsed
     * @param dataUpdate If true, all image data in the database will be updated to match info in directory, if false
     *                  only new images will be added and missing images removed from database
     * @param finished Future for notification about parsing finish
     */
    void parseDir(Path directory, boolean recursive, boolean dataUpdate, Future<Void> finished);
}
