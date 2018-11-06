package net.greenmanov.anime.rurybooru.service;

import net.greenmanov.anime.rurybooru.persistance.dao.DirDao;
import net.greenmanov.anime.rurybooru.persistance.entity.Dir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class DirServiceImpl
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Service
public class DirServiceImpl implements DirService {

    private final static Long ROOT_ID = 1L;

    @Autowired
    private DirDao dirDao;

    /**
     * Find dir by its id
     *
     * @param id of the dir
     * @return dir or {@code null}, if there is no dir with requested id
     */
    @Override
    public Dir getById(long id) {
        return dirDao.getById(id);
    }

    /**
     * Return entity for root directory
     *
     * @return Return root entity
     */
    @Override
    public Dir getRoot() {
        Dir root = dirDao.getById(ROOT_ID);
        if (root == null) {
            root = new Dir();
            root.setId(ROOT_ID);
            dirDao.update(root);
        }
        return root;
    }

    /**
     * Stores new dir
     *
     * @param dir to be created
     */
    @Override
    public void create(Dir dir) {
        dirDao.create(dir);
    }

    /**
     * Return entity for subdir of directory base on the name. If entity is not in database new will be created
     *
     * @param parent Parent dir entity
     * @param name   Name of the subdir
     * @return Subdir entity
     */
    @Override
    public Dir getSubDir(Dir parent, String name) {
        Dir dir = parent.getSubDirs().get(name);
        if (dir == null) {
            dir = new Dir();
            dir.setName(name);
            parent.addSubDir(dir);
            dirDao.create(dir);
        }
        return dir;
    }
}
