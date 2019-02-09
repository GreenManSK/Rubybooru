package net.greenmanov.anime.rurybooru.persistance.filters;

import com.querydsl.jpa.impl.JPAQuery;
import net.greenmanov.anime.rurybooru.persistance.entity.Image;

/**
 * Filter image by height
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class ImageHeightFilter implements ImageFilter {
    private int height;
    private FilterOperator operator;

    public ImageHeightFilter(int height, FilterOperator operator) {
        this.height = height;
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
                return query.where(IMAGE.height.gt(height));
            case LT:
                return query.where(IMAGE.height.lt(height));
            case GOE:
                return query.where(IMAGE.height.goe(height));
            case LOE:
                return query.where(IMAGE.height.loe(height));
            default:
                return query.where(IMAGE.height.eq(height));
        }
    }
}
