package net.greenmanov.anime.rurybooru.service.facade;

import net.greenmanov.anime.rurybooru.api.dto.TagDTO;
import net.greenmanov.anime.rurybooru.api.dto.TagInfoDTO;
import net.greenmanov.anime.rurybooru.api.facade.TagFacade;
import net.greenmanov.anime.rurybooru.persistance.entity.Image;
import net.greenmanov.anime.rurybooru.persistance.entity.Tag;
import net.greenmanov.anime.rurybooru.service.BeanMappingService;
import net.greenmanov.anime.rurybooru.service.ImageService;
import net.greenmanov.anime.rurybooru.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class TagFacadeImpl
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Service
@Transactional
public class TagFacadeImpl implements TagFacade {

    private static final int MAX_IMAGES_PER_PAGE = 1000;

    @Autowired
    private TagService tagService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private BeanMappingService mapper;

    /**
     * Retrieve tag by id
     *
     * @param id tag id
     * @return tag or {@code null}, if tag with id do not exists
     */
    @Override
    public TagDTO getById(long id) {
        return mapper.mapTo(tagService.getById(id), TagDTO.class);
    }

    /**
     * Retrieve all tags
     *
     * @return list of all tags
     */
    @Override
    public List<TagDTO> getAll() {
        return mapper.mapTo(tagService.getAll(), TagDTO.class);
    }

    /**
     * Return TagInfo by id
     *
     * @param id tag id
     * @return tag info or {@code null}, if tag with id do not exists
     */
    @Override
    public TagInfoDTO getTagInfo(long id) {
        Tag tag = tagService.getById(id);
        return tagToTagInfoDTO(tag);
    }

    /**
     * Return set of all tags for images
     *
     * @param imageIds List of image ids
     * @return Set of TagInfoDTOs
     */
    @Override
    public Set<TagInfoDTO> getTagInfoForImages(List<Long> imageIds) {
        List<Image> images = imageService.getByIds(imageIds);
        Set<Tag> tags = new HashSet<>();
        for (Image i : images) {
            tags.addAll(i.getTags());
        }
        return tags.stream().map(this::tagToTagInfoDTO).collect(Collectors.toSet());
    }

    /**
     * Transforms tag entity to TagInfoDTO
     *
     * @param tag Tag entity
     * @return TagInfoDTO with all needed data
     */
    private TagInfoDTO tagToTagInfoDTO(Tag tag) {
        long count = tagService.getTagUseCount(tag);

        TagInfoDTO info = mapper.mapTo(tag, TagInfoDTO.class);
        info.setCount(count);

        return info;
    }
}
