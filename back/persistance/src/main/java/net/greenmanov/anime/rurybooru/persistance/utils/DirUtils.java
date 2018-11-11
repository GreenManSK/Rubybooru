package net.greenmanov.anime.rurybooru.persistance.utils;

import net.greenmanov.anime.rurybooru.persistance.entity.Dir;
import net.greenmanov.anime.rurybooru.persistance.entity.Image;

/**
 * Util function for Dir entities
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
final public class DirUtils {
    /**
     * Return path to dir from root
     */
    public static String getPath(Dir dir) {
        StringBuilder path = new StringBuilder();
        while (dir != null && dir.getName() != null) {
            path.insert(0, dir.getName());
            path.insert(0, '/');
            dir = dir.getParent();
        }
        return path.toString();
    }

    /**
     * Return path to image from root
     */
    public static String getPath(Image image) {
        return getPath(image.getParent()) + '/' + image.getName();
    }
}
