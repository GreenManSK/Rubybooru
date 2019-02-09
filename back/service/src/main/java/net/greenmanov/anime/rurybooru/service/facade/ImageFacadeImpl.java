package net.greenmanov.anime.rurybooru.service.facade;

import net.greenmanov.anime.rurybooru.api.dto.GetImagesDTO;
import net.greenmanov.anime.rurybooru.api.dto.ImageDTO;
import net.greenmanov.anime.rurybooru.api.facade.ImageFacade;
import net.greenmanov.anime.rurybooru.persistance.RubybooruConfig;
import net.greenmanov.anime.rurybooru.persistance.filters.ImageFilter;
import net.greenmanov.anime.rurybooru.persistance.utils.DirUtils;
import net.greenmanov.anime.rurybooru.service.BeanMappingService;
import net.greenmanov.anime.rurybooru.service.ImageFileService;
import net.greenmanov.anime.rurybooru.service.ImageService;
import net.greenmanov.anime.rurybooru.service.helper.FilterDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class ImageFacadeImpl
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Service
@Transactional
public class ImageFacadeImpl implements ImageFacade {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageFileService imageFileService;

    @Autowired
    private BeanMappingService mapper;

    /**
     * Retrieve image by id
     *
     * @param id image id
     * @return image or {@code null}, if image with id do not exists
     */
    @Override
    public ImageDTO getById(long id) {
        return mapper.mapTo(imageService.getById(id), ImageDTO.class);
    }

    /**
     * Return path to image from gallery root
     *
     * @param id image id
     * @return Path to image file
     */
    @Override
    public String getPath(long id) {
        return DirUtils.getPath(imageService.getById(id));
    }

    /**
     * Open image in file explorer
     *
     * @param id image id
     */
    @Override
    public void open(long id) {
        imageFileService.open(imageService.getById(id));
    }

    /**
     * Return file name of resized version of image saved in tmp path
     *
     * @param id     image id
     * @param width  image width
     * @param height image height
     * @return File name
     */
    @Override
    public String getTmpImage(long id, int width, int height) {
        return imageFileService.resize(imageService.getById(id), width, height);
    }

    /**
     * Return list of images based on parameters of DTO object
     *
     * @param dto GetImagesDTO
     * @return List of images satisfying DTO parameters
     */
    @Override
    public List<ImageDTO> getImages(GetImagesDTO dto) {
        return mapper.mapTo(
                imageService.getImages(
                        dto.getTags(),
                        toFilters(dto.getFilters()),
                        dto.getDir(),
                        dto.getOrder(),
                        dto.getPerPage(),
                        dto.getPage()),
                ImageDTO.class);
    }

    /**
     * Return number of images based on parameters of DTO object
     *
     * @param dto GetImagesDTO
     * @return Number of images
     */
    @Override
    public Long getImagesCount(GetImagesDTO dto) {
        return imageService.getImagesCount(dto.getTags(), toFilters(dto.getFilters()), dto.getDir());
    }

    private List<ImageFilter> toFilters(List<String> strings) {
        if (strings == null)
            return null;
        return FilterDecoder.decode(strings);
    }
}
