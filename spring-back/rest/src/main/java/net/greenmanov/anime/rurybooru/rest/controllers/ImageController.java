package net.greenmanov.anime.rurybooru.rest.controllers;

import net.greenmanov.anime.rurybooru.api.dto.GetImagesDTO;
import net.greenmanov.anime.rurybooru.api.dto.ImageDTO;
import net.greenmanov.anime.rurybooru.api.enums.Order;
import net.greenmanov.anime.rurybooru.api.facade.ImageFacade;
import net.greenmanov.anime.rurybooru.rest.ApiUris;
import net.greenmanov.anime.rurybooru.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class ImageController
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@RestController
@RequestMapping(ApiUris.IMAGES_URI)
public class ImageController {
    private final static Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageFacade imageFacade;

    /**
     * Get one image by id
     *
     * @param id id of the image
     * @return ImageDTO
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ImageDTO getImage(@PathVariable("id") long id) {
        logger.debug("getImage({})", id);
        ImageDTO imageDTO = imageFacade.getById(id);
        if (imageDTO == null) {
            throw new ResourceNotFoundException();
        }
        return imageDTO;
    }

    /**
     * Return filtered images sorted desc by datetime added
     *
     * @param tags    Tag ids or {@code null}
     * @param dirId   Dir id or {@code null}
     * @param perPage Number of images per page
     * @param page    Image Number of page, starting from 1
     * @return List of images
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ImageDTO> getImages(
            @RequestParam(required = false) List<Long> tags,
            @RequestParam(required = false) Long dirId,
            Integer perPage,
            Integer page) {
        GetImagesDTO dto = new GetImagesDTO();
        dto.setDir(dirId);
        dto.setOrder(Order.DESC);
        dto.setPage(page);
        dto.setPerPage(perPage);
        dto.setTags(tags);
        logger.debug("getImages({})", dto);
        return imageFacade.getImages(dto);
    }
}
