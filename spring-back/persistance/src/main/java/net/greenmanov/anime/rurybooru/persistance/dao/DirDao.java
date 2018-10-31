package net.greenmanov.anime.rurybooru.persistance.dao;

import net.greenmanov.anime.rurybooru.persistance.entity.Dir;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Interface DirDao
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface DirDao {
    /**
     * Find dir by its id
     *
     * @param id of the dir
     * @return dir or {@code null}, if there is no dir with requested id
     */
    Dir getById(long id);

    /**
     * Find all dirs
     *
     * @return List of all dirs
     */
    List<Dir> getAll();

    /**
     * Stores new dir
     *
     * @param dir to be created
     * @throws NullPointerException         if the {@code dir} is {@code null}
     * @throws ConstraintViolationException if the {@code dir} has required field set as {@code null}
     */
    void create(Dir dir);

    /**
     * Updates existing dir according to the id
     *
     * @param dir to be updated
     * @throws NullPointerException         if the {@code dir} is {@code null}
     * @throws ConstraintViolationException if the {@code dir} has required field set as {@code null}
     */
    void update(Dir dir);

    /**
     * Removes the dir
     *
     * @param dir to be deleted
     * @throws NullPointerException if the {@code dir} is {@code null}
     */
    void remove(Dir dir);
}
