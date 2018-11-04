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
}
