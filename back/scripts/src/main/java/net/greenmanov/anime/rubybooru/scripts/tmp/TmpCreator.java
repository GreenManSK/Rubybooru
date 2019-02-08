package net.greenmanov.anime.rubybooru.scripts.tmp;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Creates tmp images for images in database
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface TmpCreator {

    /**
     * Delete all files in tmp folder expect for files starting with .
     *
     * @param tmpPath Path to tmp folder
     * @throws IOException If there is any problem while deleting files
     */
    void emptyTmpDirectory(Path tmpPath) throws IOException;

    /**
     * Create tmp images for all images with provided size
     * @param tmpPath Path to tmp folder
     * @param width Width of tmp images
     * @param height Height of tmp images
     */
    void createTmps(Path tmpPath, int width, int height);
}
