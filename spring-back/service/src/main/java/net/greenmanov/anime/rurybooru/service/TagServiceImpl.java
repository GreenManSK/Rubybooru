package net.greenmanov.anime.rurybooru.service;

import net.greenmanov.anime.rurybooru.persistance.dao.TagDao;
import net.greenmanov.anime.rurybooru.persistance.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class TagServiceImpl
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    /**
     * Find tag by its id
     *
     * @param id of the tag
     * @return tag or {@code null}, if there is no tag with requested id
     */
    @Override
    public Tag getById(long id) {
        return tagDao.getById(id);
    }

    /**
     * Find all tags
     *
     * @return List of all tags
     */
    @Override
    public List<Tag> getAll() {
        return tagDao.getAll();
    }

    /**
     * Return number of images that have provided tag
     *
     * @param tag Tag entity
     * @return number of images with tag
     */
    @Override
    public long getTagUseCount(Tag tag) {
        return tagDao.getTagUseCount(tag);
    }

    /**
     * Stores new tag
     *
     * @param tag to be created
     */
    @Override
    public void create(Tag tag) {
        tagDao.create(tag);
    }

    /**
     * Updates existing tag according to the id
     *
     * @param tag to be updated
     */
    @Override
    public void update(Tag tag) {
        tagDao.update(tag);
    }

    /**
     * Removes the tag
     *
     * @param tag to be deleted
     */
    @Override
    public void remove(Tag tag) {
        tagDao.update(tag);
    }
}
