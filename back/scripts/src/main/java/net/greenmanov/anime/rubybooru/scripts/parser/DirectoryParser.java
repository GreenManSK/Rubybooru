package net.greenmanov.anime.rubybooru.scripts.parser;

import java.nio.file.Path;

/**
 * Synchronize data from image directories to the database
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface DirectoryParser {

    /**
     * Parse all data from directory according to parameters and saves it to database
     * @param directory  Root directory
     * @param recursive  Specify if subdirectories should be also parsed
     * @param dataUpdate If true, all image data in the database will be updated to match info in directory, if false
     *                   only new images will be added and missing images removed from database
     */
    void parseDir(Path directory, boolean recursive, boolean dataUpdate);
}
