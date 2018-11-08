package net.greenmanov.anime.rurybooru.rest.controllers;

import net.greenmanov.anime.rurybooru.api.dto.DirDTO;
import net.greenmanov.anime.rurybooru.api.facade.DirFacade;
import net.greenmanov.anime.rurybooru.rest.ApiUris;
import net.greenmanov.anime.rurybooru.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class DirController
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@RestController
@RequestMapping(ApiUris.DIR_URI)
public class DirController {
    private final static Logger logger = LoggerFactory.getLogger(DirController.class);

    @Autowired
    private DirFacade dirFacade;

    /**
     * Get dir by id
     * @param id dir id
     * @return DirDTO
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final DirDTO getDir(@PathVariable("id") long id) {
        logger.debug("getDir({})", id);
        DirDTO dirDTO = dirFacade.getById(id);
        if (dirDTO == null) {
            throw new ResourceNotFoundException();
        }
        return dirDTO;
    }
}
