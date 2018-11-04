package net.greenmanov.anime.rurybooru.service.facade;

import net.greenmanov.anime.rurybooru.api.dto.GetImagesDTO;
import net.greenmanov.anime.rurybooru.api.dto.ImageDTO;
import net.greenmanov.anime.rurybooru.api.facade.ImageFacade;
import net.greenmanov.anime.rurybooru.service.BeanMappingService;
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
     * Return list of images based on parameters of DTO object
     *
     * @param dto GetImagesDTO
     * @return List of images satisfying DTO parameters
     */
    @Override
    public List<ImageDTO> getImages(GetImagesDTO dto) {
        return mapper.mapTo(imageService.getImages(dto.getTags(), dto.getDir(), dto.getOrder(), dto.getPrePage(),
                dto.getPage()), ImageDTO.class);
    }
}
