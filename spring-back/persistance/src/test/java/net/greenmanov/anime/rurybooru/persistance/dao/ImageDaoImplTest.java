package net.greenmanov.anime.rurybooru.persistance.dao;

import net.greenmanov.anime.rurybooru.persistance.RurybooruTestApplicationContext;
import net.greenmanov.anime.rurybooru.persistance.entity.Dir;
import net.greenmanov.anime.rurybooru.persistance.entity.Image;
import net.greenmanov.anime.rurybooru.persistance.entity.Tag;
import net.greenmanov.iqdb.parsers.TagType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Class ImageDaoImplTest
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@ContextConfiguration(classes = RurybooruTestApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ImageDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ImageDao imageDao;

    @PersistenceContext
    private EntityManager em;

    private Dir dir;

    @BeforeMethod
    public void setUp() {
        dir = createDir();
        em.persist(dir);
    }

    @Test
    public void testGetById() {
        Image img = createImage();
        em.persist(img);
        assertEquals(img, imageDao.getById(img.getId()));
    }

    @Test
    public void testGetAll() {
        Image img1 = createImage();
        Image img2 = createImage();
        Image img3 = createImage();
        img2.setName("Image 2");
        img3.setName("Image 3");

        em.persist(img1);
        em.persist(img2);
        em.persist(img3);

        List<Image> images = imageDao.getAll();
        assertEquals(3, images.size());
        assertTrue(images.contains(img1));
        assertTrue(images.contains(img2));
        assertTrue(images.contains(img3));
    }

    @Test
    public void testGetByTag() {
        Image img1 = createImage();
        Image img2 = createImage();
        Image img3 = createImage();
        img2.setName("Image 2");
        img3.setName("Image 3");

        Tag tag1 = createTag();
        Tag tag2 = createTag();
        tag2.setName("test 2");

        img1.addTag(tag1);
        img2.addTag(tag1);
        img2.addTag(tag2);
        img3.addTag(tag2);

        em.persist(img1);
        em.persist(img2);
        em.persist(img3);
        em.persist(tag1);
        em.persist(tag2);

        List<Image> images = imageDao.getByTag(tag1);
        assertEquals(2, images.size());
        assertTrue(images.contains(img1));
        assertTrue(images.contains(img2));
        assertFalse(images.contains(img3));
    }

    @Test
    public void testCreate() {
        Image img = createImage();
        imageDao.create(img);
        assertEquals(img, em.find(Image.class, img.getId()));
    }

    @Test
    public void testUpdate() {
        Image img = createImage();
        em.persist(img);

        Image img2 = createImage();
        img2.setId(img.getId());
        img2.setWidth(5000);

        imageDao.update(img2);
        assertEquals(img2, em.find(Image.class, img.getId()));
    }

    @Test
    public void testRemove() {
        Image img = createImage();
        em.persist(img);
        assertEquals(img, em.find(Image.class, img.getId()));

        imageDao.remove(img);
        assertNull(em.find(Image.class, img.getId()));
    }

    private Image createImage() {
        Image img = new Image();

        img.setName("Img Name");
        img.setDate(new Date());
        img.setWidth(500);
        img.setHeight(600);
        img.setSource("http://link.com");

        img.setParent(dir);

        return img;
    }

    private Dir createDir() {
        Dir dir = new Dir();
        dir.setName("Test Dir");
        return dir;
    }

    private Tag createTag() {
        Tag tag = new Tag();
        tag.setName("test");
        tag.setType(TagType.COPYRIGHT);
        return tag;
    }
}
