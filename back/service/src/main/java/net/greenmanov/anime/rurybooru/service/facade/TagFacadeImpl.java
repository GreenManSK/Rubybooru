package net.greenmanov.anime.rurybooru.service.facade;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.TreeMultiset;
import net.greenmanov.anime.rurybooru.api.dto.GetImagesDTO;
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
import java.util.List;
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
     * Return TagInfo by id
     *
     * @param id tag id
     * @return tag info or {@code null}, if tag with id do not exists
     */
    @Override
    public TagInfoDTO getTagInfo(long id) {
        Tag tag = tagService.getById(id);
        long count = tagService.getTagUseCount(tag);

        TagInfoDTO info = mapper.mapTo(tag, TagInfoDTO.class);
        info.setCount(count);

        return info;
    }

    /**
     * Return tag info for first n most popular tags of images fitting getImageDTO
     *
     * @param dto GetImageDTO
     * @param n   Number of tags
     * @return List of TagInfoDTO for first n or less most popular tags
     */
    @Override
    public List<TagInfoDTO> getTagInfoForGetImage(GetImagesDTO dto, int n) {
        List<Image> images = imageService.getImages(dto.getTags(), dto.getDir(), dto.getOrder(), MAX_IMAGES_PER_PAGE, 1);
        Multiset<Tag> tags = HashMultiset.create();
        for (Image image : images) {
            tags.addAll(image.getTags());
        }
        return Multisets.copyHighestCountFirst(tags).stream().limit(n).map(tag -> {
            TagInfoDTO info = mapper.mapTo(tag, TagInfoDTO.class);
            info.setCount(tagService.getTagUseCount(tag));
            return info;
        }).collect(Collectors.toList());
    }
}
