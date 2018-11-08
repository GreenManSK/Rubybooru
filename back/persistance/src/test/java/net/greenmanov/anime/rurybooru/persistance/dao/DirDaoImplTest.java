package net.greenmanov.anime.rurybooru.persistance.dao;

import net.greenmanov.anime.rurybooru.persistance.RurybooruTestApplicationContext;
import net.greenmanov.anime.rurybooru.persistance.entity.Dir;
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

import static org.testng.Assert.*;

/**
 * Class DirDaoImplTest
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@ContextConfiguration(classes = RurybooruTestApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class DirDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DirDao dirDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testGetById() {
        Dir dir = createDir();
        em.persist(dir);
        assertEquals(dir, dirDao.getById(dir.getId()));
    }

    @Test
    public void testGetByNonexistingId() {
        Dir dir = createDir();
        em.persist(dir);
        assertNull(dirDao.getById(dir.getId() + 1));
    }

    @Test
    public void testGetAllEmpty() {
        assertTrue(dirDao.getAll().isEmpty());
    }

    @Test
    public void testGetAll() {
        Dir dir1 = createDir();
        Dir dir2 = createDir();
        dir2.setName("teesst");
        Dir dir3 = createDir();
        dir3.setName("ttt");
        em.persist(dir1);
        em.persist(dir2);
        em.persist(dir3);

        List<Dir> dirs = dirDao.getAll();
        assertEquals(3, dirs.size());
        assertTrue(dirs.contains(dir1));
        assertTrue(dirs.contains(dir2));
        assertTrue(dirs.contains(dir3));
    }

    @Test
    public void testCreate() {
        Dir dir = createDir();

        dirDao.create(dir);
        assertEquals(dir, em.find(Dir.class, dir.getId()));
    }

    @Test
    public void testUpdate() {
        Dir dir = createDir();
        em.persist(dir);

        Dir dir2 = createDir();
        dir2.setId(dir.getId());
        dir2.setName("New Name");

        dirDao.update(dir2);

        assertEquals(dir2, em.find(Dir.class, dir.getId()));
    }

    @Test
    public void testRemove() {
        Dir dir = createDir();
        em.persist(dir);
        assertEquals(dir, em.find(Dir.class, dir.getId()));

        dirDao.remove(dir);
        assertNull(em.find(Dir.class, dir.getId()));
    }

    private Dir createDir() {
        Dir dir = new Dir();
        dir.setName("test dir");
        return dir;
    }
}