package net.greenmanov.anime.rubybooru.database.daos;

import com.querydsl.jpa.impl.JPAQuery;
import net.greenmanov.anime.rubybooru.database.entities.Dir;
import net.greenmanov.anime.rubybooru.database.entities.QDir;

import java.util.Optional;

/**
 * Dao for Dir entity
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class DirDao extends AJpaDao {
    private final static QDir DIR = QDir.dir;

    public DirDao() {
    }

    /**
     * Get root directory entity
     *
     * @return root dir
     */
    public Dir getRoot() {
        Dir root = new JPAQuery<>(em).select(DIR).from(DIR).where(DIR.parent.isNull()).fetchOne();
        if (root == null) {
            root = new Dir();
            root.setName("");
            create(root);
        }
        return root;
    }

    /**
     * Return subdirectory of directory, creates new entity if needed
     *
     * @param dir     Parent directory
     * @param subName Dir name
     * @return Dir entity
     */
    public Dir getSubDir(Dir dir, String subName) {
        Optional<Dir> subDir = dir.getSubDirs().stream().filter(d -> d.getName().equals(subName)).findFirst();
        if (subDir.isPresent())
            return subDir.get();
        Dir sub = new Dir();
        transaction((em) -> {
            sub.setName(subName);
            dir.addSubDir(sub);
            em.persist(sub);
        });
        return sub;
    }

    /**
     * Return Dir entity by id
     *
     * @param id Dir id
     * @return Dir entity
     */
    public Dir getById(long id) {
        return em.find(Dir.class, id);
    }

    /**
     * Insert Dir into database
     *
     * @param dir Dir entity
     */
    public void create(Dir dir) {
        this.transaction((em) -> em.persist(dir));
    }

    /**
     * Remove Dir from database
     *
     * @param dir Dir entity
     */
    public void remove(Dir dir) {
        this.transaction((em) -> em.remove(dir));
    }
}
