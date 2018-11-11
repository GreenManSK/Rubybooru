package net.greenmanov.anime.rurybooru.service.facade;

import net.greenmanov.anime.rurybooru.api.dto.GetImagesDTO;
import net.greenmanov.anime.rurybooru.api.dto.ImageDTO;
import net.greenmanov.anime.rurybooru.api.facade.ImageFacade;
import net.greenmanov.anime.rurybooru.service.BeanMappingService;
import net.greenmanov.anime.rurybooru.service.ImageResizeService;
import net.greenmanov.anime.rurybooru.service.ImageService;
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
    private ImageResizeService imageResizeService;

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
     * Return file name of resized version of image saved in tmp path
     * @param id image id
     * @param width image width
     * @param height image height
     * @return File name
     */
    @Override
    public String getTmpImage(long id, int width, int height) {
       return imageResizeService.resize(imageService.getById(id), width, height);
    }

    /**
     * Return list of images based on parameters of DTO object
     *
     * @param dto GetImagesDTO
     * @return List of images satisfying DTO parameters
     */
    @Override
    public List<ImageDTO> getImages(GetImagesDTO dto) {
        return mapper.mapTo(imageService.getImages(dto.getTags(), dto.getDir(), dto.getOrder(), dto.getPerPage(),
                dto.getPage()), ImageDTO.class);
    }
}
