package net.greenmanov.anime.rurybooru.rest.controllers;

import net.greenmanov.anime.rurybooru.api.dto.GetImagesDTO;
import net.greenmanov.anime.rurybooru.api.dto.TagDTO;
import net.greenmanov.anime.rurybooru.api.dto.TagInfoDTO;
import net.greenmanov.anime.rurybooru.api.enums.Order;
import net.greenmanov.anime.rurybooru.api.facade.TagFacade;
import net.greenmanov.anime.rurybooru.rest.ApiUris;
import net.greenmanov.anime.rurybooru.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class TagController
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@RestController
@RequestMapping(ApiUris.TAG_URI)
public class TagController {
    private final static Logger logger = LoggerFactory.getLogger(TagController.class);

    @Autowired
    private TagFacade tagFacade;

    /**
     * Get tag info
     *
     * @param id id of the tag
     * @return TagInfoDTO
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TagInfoDTO getTag(@PathVariable("id") long id) {
        logger.debug("getTagInfo({})", id);
        TagInfoDTO tagInfoDTO = tagFacade.getTagInfo(id);
        if (tagInfoDTO == null) {
            throw new ResourceNotFoundException();
        }
        return tagInfoDTO;
    }

    /**
     * Return top tags for image search
     *
     * @param tags   Tag ids or {@code null}
     * @param dirId  Dir id or {@code null}
     * @param order  Order of images
     * @param number Number of  tags
     * @return List of TagInfoDTO
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TagInfoDTO> getTags(
            @RequestParam(required = false) List<Long> tags,
            @RequestParam(required = false) Long dirId,
            @RequestParam(required = false) Order order,
            Integer number
    ) {
        GetImagesDTO dto = new GetImagesDTO();
        dto.setDir(dirId);
        dto.setOrder(order == null ? Order.NEWEST : order);
        dto.setTags(tags);
        logger.debug("getTagInfoForGetImage({}, {})", dto, number);
        return tagFacade.getTagInfoForGetImage(dto, number);
    }

    /**
     * Return all tags
     * @return all tags
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TagDTO> getTags() {
        logger.debug("getTags()");
        return tagFacade.getAll();
    }
}
