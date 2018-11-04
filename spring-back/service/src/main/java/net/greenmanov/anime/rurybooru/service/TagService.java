package net.greenmanov.anime.rurybooru.service;

import net.greenmanov.anime.rurybooru.persistance.entity.Tag;

import java.util.List;

/**
 * Interface TagService
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface TagService {
    /**
     * Find tag by its id
     *
     * @param id of the tag
     * @return tag or {@code null}, if there is no tag with requested id
     */
    Tag getById(long id);

    /**
     * Find all tags
     *
     * @return List of all tags
     */
    List<Tag> getAll();

    /**
     * Return number of images that have provided tag
     *
     * @param tag Tag entity
     * @return number of images with tag
     */
    long getTagUseCount(Tag tag);

    /**
     * Stores new tag
     *
     * @param tag to be created
     */
    void create(Tag tag);

    /**
     * Updates existing tag according to the id
     *
     * @param tag to be updated
     */
    void update(Tag tag);

    /**
     * Removes the tag
     *
     * @param tag to be deleted
     */
    void remove(Tag tag);
}
