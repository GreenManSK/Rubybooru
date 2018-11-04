package net.greenmanov.anime.rurybooru.service.facade;

import net.greenmanov.anime.rurybooru.api.dto.DirDTO;
import net.greenmanov.anime.rurybooru.api.facade.DirFacade;
import net.greenmanov.anime.rurybooru.service.BeanMappingService;
import net.greenmanov.anime.rurybooru.service.DirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class DirFacadeImpl
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Service
@Transactional
public class DirFacadeImpl implements DirFacade {

    @Autowired
    private DirService dirService;

    @Autowired
    private BeanMappingService mapper;

    /**
     * Retrieve dir by id
     *
     * @param id dir id
     * @return dir or {@code null}, if dir with id do not exists
     */
    @Override
    public DirDTO getById(long id) {
        return mapper.mapTo(dirService.getById(id), DirDTO.class);
    }
}
