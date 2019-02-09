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
}
