package net.greenmanov.anime.rurybooru.service;

import net.greenmanov.anime.rurybooru.persistance.entity.Dir;

import java.nio.file.Path;

/**
 * Class DirService
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface DirService {
    /**
     * Find dir by its id
     *
     * @param id of the dir
     * @return dir or {@code null}, if there is no dir with requested id
     */
    Dir getById(long id);

    /**
     * Return entity for root directory
     *
     * @return Return root entity
     */
    Dir getRoot();

    /**
     * Stores new dir
     *
     * @param dir to be created
     */
    void create(Dir dir);

    /**
     * Return entity for subdir of directory base on the name. If entity is not in database new will be created
     *
     * @param parent Parent dir entity
     * @param name   Name of the subdir
     * @return Subdir entity
     */
    Dir getSubDir(Dir parent, String name);
}
