package net.greenmanov.anime.rurybooru.service;

import net.greenmanov.anime.rurybooru.persistance.entity.Dir;

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
}
