package net.greenmanov.anime.rurybooru.persistance.filters;

import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import net.greenmanov.anime.rurybooru.persistance.entity.Image;

/**
 * Class ImageRatioFilter
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class ImageRatioFilter implements ImageFilter {
    private int width;
    private int height;
    private double delta;

    public ImageRatioFilter(int width, int height, double delta) {
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
        float ratio = 1.0f * width / height;

        NumberExpression<Float> expression = IMAGE.width.floatValue().divide(IMAGE.height.floatValue());
        if (delta == 0)
            return query.where(expression.eq(ratio));
        return query.where(expression.loe(ratio + delta)).where(expression.goe(ratio - delta));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getDelta() {
        return delta;
    }
}
