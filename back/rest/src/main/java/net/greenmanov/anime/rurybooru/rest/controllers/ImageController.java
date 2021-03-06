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
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.constraints.Size;
import java.util.List;

import static net.greenmanov.anime.rurybooru.rest.ApiUris.STATIC_GALLERY_URI;
import static net.greenmanov.anime.rurybooru.rest.ApiUris.STATIC_TMP_URI;

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
     * Reditect to file with image resized to provided width and height. Aspect ration of image is not changed,
     * bigger side is fit to provided width or height
     *
     * @param width  Width in pixels
     * @param height Height in pixels
     * @return RedirectView
     */
    @RequestMapping(value = "/tmp/{id}", method = RequestMethod.GET)
    public final RedirectView getTmpImage(
            @PathVariable("id") long id,
            @Size(min = 100) @RequestParam() Integer width,
            @Size(min = 100) @RequestParam() Integer height
    ) {
        logger.debug("getTmpImage({}, {}, {})", id, width, height);
        String imageName = imageFacade.getTmpImage(id, width, height);
        RedirectView redirect = new RedirectView();
        redirect.setUrl("../.." + STATIC_TMP_URI + "/" + imageName);
        return redirect;
    }

    /**
     * Redirect to image file
     *
     * @param id Id of image
     * @return RedirectView
     */
    @RequestMapping(value = "/file/{id}", method = RequestMethod.GET)
    public final RedirectView getImageFile(@PathVariable("id") long id) {
        logger.debug("getImagePath({})", id);
        String imagePath = imageFacade.getPath(id);
        RedirectView redirect = new RedirectView();
        redirect.setUrl("../.." + STATIC_GALLERY_URI + "/" + imagePath);
        return redirect;
    }

    /**
     * Opens image in windows file explorer
     *
     * @param id image id
     */
    @RequestMapping(value = "/open/{id}", method = RequestMethod.GET)
    public final void openImage(@PathVariable("id") long id) {
        logger.debug("open({})", id);
        imageFacade.open(id);
    }

    /**
     * Return filtered images sorted desc by datetime added
     *
     * @param tags    Tag ids or {@code null}
     * @param filters Encoded ImageFilter represented as strings
     * @param dirId   Dir id or {@code null}
     * @param perPage Number of images per page
     * @param page    Image Number of page, starting from 1
     * @return List of images
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ImageDTO> getImages(
            @RequestParam(required = false) List<Long> tags,
            @RequestParam(required = false) List<String> filters,
            @RequestParam(required = false) Long dirId,
            @RequestParam(required = false) Order order,
            @Size(min = 1, max = 128) @RequestParam() Integer perPage,
            @Size(min = 1) @RequestParam() Integer page) {
        GetImagesDTO dto = new GetImagesDTO();
        dto.setDir(dirId);
        dto.setOrder(order == null ? Order.NEWEST : order);
        dto.setPage(page);
        dto.setPerPage(perPage);
        dto.setTags(tags);
        dto.setFilters(filters);
        logger.debug("getImages({})", dto);
        return imageFacade.getImages(dto);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Long getImageCount(
            @RequestParam(required = false) List<Long> tags,
            @RequestParam(required = false) List<String> filters,
            @RequestParam(required = false) Long dirId
    ) {
        GetImagesDTO dto = new GetImagesDTO();
        dto.setDir(dirId);
        dto.setTags(tags);
        dto.setFilters(filters);

        logger.debug("getImagesCount({})", dto);
        return imageFacade.getImagesCount(dto);
    }
}
