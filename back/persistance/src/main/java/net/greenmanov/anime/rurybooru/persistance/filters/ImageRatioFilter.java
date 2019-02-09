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
    private FilterOperator operator;

    public ImageRatioFilter(int width, int height, double delta, FilterOperator operator) {
        this.width = width;
        this.height = height;
        this.delta = delta;
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
        NumberExpression<Integer> expression = IMAGE.width.divide(IMAGE.height);
        SimpleOperation<Integer> ratio = Expressions.operation(Integer.class, Ops.DIV, Expressions.constant(width), Expressions.constant(height));
        if (operator == FilterOperator.EQ) {
            if (delta == 0) {
                return query.where(expression.eq(ratio));
            }
            return query.where(expression.loe(Expressions.operation(Integer.class, Ops.ADD, ratio, Expressions.constant(delta))))
                    .where(expression.goe(Expressions.operation(Integer.class, Ops.SUB, ratio, Expressions.constant(delta))));
        }
        switch (operator) {
            case GT:
                return query.where(expression.gt(ratio));
            case LT:
                return query.where(expression.lt(ratio));
            case GOE:
                return query.where(expression.goe(ratio));
            case LOE:
                return query.where(expression.loe(ratio));
            default:
                return query.where(expression.eq(ratio));
        }
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
