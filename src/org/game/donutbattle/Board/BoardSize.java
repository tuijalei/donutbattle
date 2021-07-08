package org.game.donutbattle.Board;

import java.awt.*;

/**
 * enum for game board size values
 */
public enum BoardSize {

    _5x5("5x5", new Dimension(5, 5)),
    _6x6("6x6", new Dimension(6, 6)),
    _7x7("7x7", new Dimension(7, 7)),
    _8x8("8x8", new Dimension(8, 8)),
    _9x9("9x9", new Dimension(9, 9)),
    _10x10("10x10", new Dimension(10, 10));

    private final String toString;
    private final Dimension dim;

    BoardSize(String toString, Dimension dim){
        this.toString = toString;
        this.dim = dim;
    }

    public Dimension getDim(){
        return this.dim;
    }

    @Override
    public String toString(){
        return toString;
    }
}
