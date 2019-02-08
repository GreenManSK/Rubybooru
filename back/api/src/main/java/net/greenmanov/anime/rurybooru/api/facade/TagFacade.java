package net.greenmanov.anime.rurybooru.api.facade;

import net.greenmanov.anime.rurybooru.api.dto.TagDTO;
import net.greenmanov.anime.rurybooru.api.dto.TagInfoDTO;

import java.util.List;
import java.util.Set;

/**
 * Class TagFacade
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface TagFacade {
    /**
     * Retrieve tag by id
     *
     * @param id tag id
     * @return tag or {@code null}, if tag with id do not exists
     */
    TagDTO getById(long id);

    /**
     * Retrieve all tags
     * @return list of all tags
     */
    List<TagDTO> getAll();

    /**
     * Return TagInfo by id
     * @param id tag id
     * @return tag info or {@code null}, if tag with id do not exists
     */
    TagInfoDTO getTagInfo(long id);

    /**
     * Return setf all tags for images
     * @param imageIds List of image ids
     * @return Set of TagInfoDTOs
     */
    Set<TagInfoDTO> getTagInfoForImages(List<Long> imageIds);
}
