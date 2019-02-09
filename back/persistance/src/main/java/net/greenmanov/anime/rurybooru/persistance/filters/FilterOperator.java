package net.greenmanov.anime.rurybooru.persistance.filters;

/**
 * Class FilterOperator
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public enum FilterOperator {
    EQ("="), LT("<"), GT(">"), LOE("<="), GOE(">=");
    private final String operator;

    FilterOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    /**
     * Transform string value to operator
     *
     * @param value string representation
     * @return operator, if non match EQ is returned
     */
    public static FilterOperator fromString(String value) {
        for (FilterOperator o : FilterOperator.values()) {
            if (o.getOperator().equals(value))
                return o;
        }
        return EQ;
    }
}
