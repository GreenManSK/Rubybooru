package net.greenmanov.anime.rurybooru.persistance.filters;

import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import net.greenmanov.anime.rurybooru.persistance.entity.Image;

/**
 * Class ImageRationFilter
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class ImageRationFilter implements ImageFilter {
    private int width;
    private int height;
    private double delta = 0;

    public ImageRationFilter(int width, int height, double delta) {
        this.width = width;
        this.height = height;
        this.delta = delta;
    }

    /**
     * Filter provided query
     *
     * @param query Image query
     * @return filtered query
     */
    @Override
    public JPAQuery<Image> apply(JPAQuery<Image> query) {
        double ratio = 1.0 * width / height;
        NumberExpression<Double> expression = IMAGE.width.doubleValue().divide(IMAGE.height.doubleValue());
        if (delta == 0)
            return query.where(expression.eq(ratio));
        return query.where(expression.loe(ratio + delta)).where(expression.goe(ratio - delta));
    }
}
