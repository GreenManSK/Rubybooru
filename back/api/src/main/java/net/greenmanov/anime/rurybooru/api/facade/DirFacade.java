package net.greenmanov.anime.rurybooru.api.facade;

import net.greenmanov.anime.rurybooru.api.dto.DirDTO;

/**
 * Class DirFacade
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface  DirFacade {
    /**
     * Retrieve dir by id
     *
     * @param id dir id
     * @return dir or {@code null}, if dir with id do not exists
     */
    DirDTO getById(long id);
}
