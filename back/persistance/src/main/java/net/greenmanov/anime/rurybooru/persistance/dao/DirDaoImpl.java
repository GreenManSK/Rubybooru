package net.greenmanov.anime.rurybooru.persistance.dao;

import com.querydsl.jpa.impl.JPAQuery;
import net.greenmanov.anime.rurybooru.persistance.entity.Dir;
import net.greenmanov.anime.rurybooru.persistance.entity.QDir;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Class DirDaoImpl
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Repository
public class DirDaoImpl implements DirDao {
    private final static QDir DIR = QDir.dir;

    @PersistenceContext
    private EntityManager em;

    /**
     * Find dir by its id
     *
     * @param id of the dir
     * @return dir or {@code null}, if there is no dir with requested id
     */
    @Override
    public Dir getById(long id) {
        return em.find(Dir.class, id);
    }

    /**
     * Find all dirs
     *
     * @return List of all dirs
     */
    @Override
    public List<Dir> getAll() {
        return new JPAQuery<>(em).select(DIR).from(DIR).fetch();
    }

    /**
     * Stores new dir
     *
     * @param dir to be created
     * @throws NullPointerException         if the {@code dir} is {@code null}
     * @throws ConstraintViolationException if the {@code dir} has required field set as {@code null}
     */
    @Override
    public void create(Dir dir) {
        em.persist(dir);
    }

    /**
     * Updates existing dir according to the id
     *
     * @param dir to be updated
     * @throws NullPointerException         if the {@code dir} is {@code null}
     * @throws ConstraintViolationException if the {@code dir} has required field set as {@code null}
     */
    @Override
    public void update(Dir dir) {
        em.merge(dir);
    }

    /**
     * Removes the dir
     *
     * @param dir to be deleted
     * @throws NullPointerException if the {@code dir} is {@code null}
     */
    @Override
    public void remove(Dir dir) {
        em.remove(dir);
    }
}
