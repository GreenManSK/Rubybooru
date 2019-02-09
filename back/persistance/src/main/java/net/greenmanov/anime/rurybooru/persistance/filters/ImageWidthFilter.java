package net.greenmanov.anime.rurybooru.persistance.filters;

import com.querydsl.jpa.impl.JPAQuery;
import net.greenmanov.anime.rurybooru.persistance.entity.Image;

/**
 * Filter image by width
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class ImageWidthFilter implements ImageFilter {
    private int width;
    private FilterOperator operator;

    public ImageWidthFilter(int width, FilterOperator operator) {
        this.width = width;
        this.operator = operator;
    }

    /**
     * Filter provided query
     *
     * @param query Image query
     * @return filtered query
     */
    @Override
    public JPAQuery<Image> apply(JPAQuery<Image> query) {
        switch (operator) {
            case GT:
                return query.where(IMAGE.width.gt(width));
            case LT:
                return query.where(IMAGE.width.lt(width));
            case GOE:
                return query.where(IMAGE.width.goe(width));
            case LOE:
                return query.where(IMAGE.width.loe(width));
            default:
                return query.where(IMAGE.width.eq(width));
        }
    }
}
