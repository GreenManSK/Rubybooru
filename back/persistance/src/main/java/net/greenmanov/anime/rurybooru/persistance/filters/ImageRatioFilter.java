package net.greenmanov.anime.rurybooru.persistance.filters;

import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.SimpleOperation;
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
        NumberExpression<Integer> expression = IMAGE.width.divide(IMAGE.height);
        SimpleOperation<Integer> ratio = Expressions.operation(Integer.class, Ops.DIV, Expressions.constant(width), Expressions.constant(height));
        if (delta == 0) {
            return query.where(expression.eq(ratio));
        }
        return query.where(expression.loe(Expressions.operation(Integer.class, Ops.ADD, ratio, Expressions.constant(delta))))
                .where(expression.goe(Expressions.operation(Integer.class, Ops.SUB, ratio, Expressions.constant(delta))));
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
