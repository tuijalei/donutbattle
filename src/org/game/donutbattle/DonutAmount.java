package org.game.donutbattle;

/**
 * Enum for controlling donutamounts in comboboxes
 */
public enum DonutAmount {

    _0("0", 0),
    _1("1", 1),
    _2("2", 2),
    _3("3", 3),
    _4("4", 4),
    _5("5", 5);

    private final String toString;
    private final int amount;

    DonutAmount(String toString, int amount){
        this.toString = toString;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return toString;
    }
}


