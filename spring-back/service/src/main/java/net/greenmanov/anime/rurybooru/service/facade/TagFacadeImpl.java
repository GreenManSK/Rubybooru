package net.greenmanov.anime.rurybooru.service.facade;

import net.greenmanov.anime.rurybooru.api.dto.TagDTO;
import net.greenmanov.anime.rurybooru.api.dto.TagInfoDTO;
import net.greenmanov.anime.rurybooru.api.facade.TagFacade;
import net.greenmanov.anime.rurybooru.persistance.entity.Tag;
import net.greenmanov.anime.rurybooru.service.BeanMappingService;
import net.greenmanov.anime.rurybooru.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class TagFacadeImpl
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Service
@Transactional
public class TagFacadeImpl implements TagFacade {

    @Autowired
    private TagService tagService;

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

        TagInfoDTO info = new TagInfoDTO();
        info.setCount(count);
        info.setTag(mapper.mapTo(tag, TagDTO.class));

        return info;
    }
}
