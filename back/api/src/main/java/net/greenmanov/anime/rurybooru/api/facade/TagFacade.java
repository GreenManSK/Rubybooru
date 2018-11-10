package net.greenmanov.anime.rurybooru.api.facade;

import net.greenmanov.anime.rurybooru.api.dto.GetImagesDTO;
import net.greenmanov.anime.rurybooru.api.dto.TagDTO;
import net.greenmanov.anime.rurybooru.api.dto.TagInfoDTO;

import java.util.List;

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
     * Return TagInfo by id
     * @param id tag id
     * @return tag info or {@code null}, if tag with id do not exists
     */
    TagInfoDTO getTagInfo(long id);

    /**
     * Return tag info for first n most popular tags of images fitting getImageDTO
     * @param getImagesDTO GetImageDTO
     * @param n Number of tags
     * @return List of TagInfoDTO for first n or less most popular tags
     */
    List<TagInfoDTO> getTagInfoForGetImage(GetImagesDTO getImagesDTO, int n);
}
