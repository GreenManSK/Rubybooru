package net.greenmanov.anime.rurybooru.persistance.filters;

import com.querydsl.jpa.impl.JPAQuery;
import net.greenmanov.anime.rurybooru.persistance.entity.Image;
import net.greenmanov.anime.rurybooru.persistance.entity.QImage;

/**
 * Image query filters
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface ImageFilter {
    QImage IMAGE = QImage.image;

    /**
     * Filter provided query
     *
     * @param query Image query
     * @return filtered query
     */
    JPAQuery<Image> apply(JPAQuery<Image> query);
}
