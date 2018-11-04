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
import java.util.Arrays;
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

    private Image img1;
    private Image img2;
    private Image img3;

    private Tag tag1;
    private Tag tag2;

    @BeforeMethod
    public void setUp() {
        dir = createDir();
        em.persist(dir);

        img1 = createImage();
        img2 = createImage();
        img3 = createImage();
        img2.setName("Image 2");
        img3.setName("Image 3");

        img1.setDate(new Date(123));
        img2.setDate(new Date(124));
        img3.setDate(new Date(125));

        tag1 = createTag();
        tag2 = createTag();
        tag2.setName("test 2");
        em.persist(tag1);
        em.persist(tag2);

        img1.addTag(tag1);
        img2.addTag(tag1);
        img2.addTag(tag2);
        img3.addTag(tag2);

        em.persist(img1);
        em.persist(img2);
        em.persist(img3);
    }

    @Test
    public void testGetById() {
        assertEquals(img1, imageDao.getById(img1.getId()));
    }

    @Test
    public void testGetAll() {
        List<Image> images = imageDao.getAll();
        assertEquals(3, images.size());
        assertTrue(images.contains(img1));
        assertTrue(images.contains(img2));
        assertTrue(images.contains(img3));
    }

    @Test
    public void testGetImagesPagination() {
        List<Image> images = imageDao.getImages(null, null, false, 1, 2);
        assertEquals(1, images.size());
        assertTrue(images.contains(img2));

        images = imageDao.getImages(null, null, false, 2, 1);
        assertEquals(2, images.size());
        assertTrue(images.contains(img1));
        assertTrue(images.contains(img2));

        images = imageDao.getImages(null, null, false, 2, 2);
        assertEquals(1, images.size());
        assertTrue(images.contains(img3));
    }

    @Test
    public void testGetImagesOrder() {
        List<Image> asc = imageDao.getImages(null, null, false, 20, 1);
        List<Image> desc = imageDao.getImages(null, null, true, 20, 1);
        assertEquals(3, asc.size());
        assertEquals(3, desc.size());

        for (int i = 0; i < asc.size(); i++) {
            assertEquals(asc.get(i), desc.get(desc.size() - 1 - i));
        }


        desc = imageDao.getImages(null, null, true, 1, 3);
        assertEquals(1, desc.size());
        assertTrue(desc.contains(img1));
    }

    @Test
    public void testGetImagesFilters() {
        List<Image> images = imageDao.getImages(null, dir.getId() + 1, false, 20, 1);
        assertTrue(images.isEmpty());

        images = imageDao.getImages(null, dir.getId(), false, 20, 1);
        assertEquals(3, images.size());
        assertTrue(images.contains(img1));
        assertTrue(images.contains(img2));
        assertTrue(images.contains(img3));

        images = imageDao.getImages(Arrays.asList(tag1.getId(), tag2.getId()), null, false, 20, 1);
        assertEquals(1, images.size());
        assertTrue(images.contains(img2));

        images = imageDao.getImages(Arrays.asList(tag1.getId()), null, false, 20, 1);
        assertEquals(2, images.size());
        assertTrue(images.contains(img1));
        assertTrue(images.contains(img2));
    }

    @Test
    public void testGetByTag() {
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
