package net.greenmanov.anime.rurybooru.persistance.dao;

import net.greenmanov.anime.rurybooru.persistance.entity.Tag;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Interface TagDao
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface TagDao {
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
     * Stores new tag
     *
     * @param tag to be created
     * @throws NullPointerException         if the {@code tag} is {@code null}
     * @throws ConstraintViolationException if the {@code tag} has required field set as {@code null}
     */
    void create(Tag tag);

    /**
     * Updates existing tag according to the id
     *
     * @param tag to be updated
     * @throws NullPointerException         if the {@code tag} is {@code null}
     * @throws ConstraintViolationException if the {@code tag} has required field set as {@code null}
     */
    void update(Tag tag);

    /**
     * Removes the tag
     *
     * @param tag to be deleted
     * @throws NullPointerException if the {@code tag} is {@code null}
     */
    void remove(Tag tag);
}
