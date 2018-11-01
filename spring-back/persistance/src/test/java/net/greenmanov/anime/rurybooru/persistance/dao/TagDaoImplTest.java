package net.greenmanov.anime.rurybooru.persistance.dao;

import net.greenmanov.anime.rurybooru.persistance.RurybooruTestApplicationContext;
import net.greenmanov.anime.rurybooru.persistance.entity.Image;
import net.greenmanov.anime.rurybooru.persistance.entity.Tag;
import net.greenmanov.iqdb.parsers.TagType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Class TagDaoImplTest
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@ContextConfiguration(classes = RurybooruTestApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TagDaoImplTest extends AbstractTestNGSpringContextTests {


    @Autowired
    private TagDao tagDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testGetById() {
        Tag tag = createTag();
        em.persist(tag);
        assertEquals(tag, tagDao.getById(tag.getId()));
    }

    @Test
    public void testGetAll() {
        Tag tag1 = createTag();
        Tag tag2 = createTag();
        Tag tag3 = createTag();
        tag2.setName("tag2");
        tag3.setType(TagType.ARTIST);

        em.persist(tag1);
        em.persist(tag2);
        em.persist(tag3);

        List<Tag> tags = tagDao.getAll();
        assertEquals(3, tags.size());
        assertTrue(tags.contains(tag1));
        assertTrue(tags.contains(tag2));
        assertTrue(tags.contains(tag3));
    }

    @Test
    public void testCreate() {
        Tag tag = createTag();
        tagDao.create(tag);
        assertEquals(tag, em.find(Tag.class, tag.getId()));
    }

    @Test
    public void testUpdate() {
        Tag tag = createTag();
        em.persist(tag);

        Tag tag2 = createTag();
        tag2.setId(tag.getId());
        tag2.setName("New Name for Update");

        tagDao.update(tag2);

        assertEquals(tag2, em.find(Tag.class, tag.getId()));
    }

    @Test
    public void testRemove() {
        Tag tag = createTag();
        em.persist(tag);
        assertEquals(tag, em.find(Tag.class, tag.getId()));

        tagDao.remove(tag);
        assertNull(em.find(Tag.class, tag.getId()));
    }

    private Tag createTag() {
        Tag tag = new Tag();
        tag.setName("Test tag");
        tag.setType(TagType.COPYRIGHT);
        return tag;
    }
}