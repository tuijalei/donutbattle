package org.game.donutbattle.Board;

import org.game.donutbattle.Pieces.Donut;

/**
 * Implementing a tile in a board
 */
public class Tile {

    private TileStatus tileStatus;
    private Donut donut;

    public Tile(TileStatus tileStatus){
        this.tileStatus = tileStatus;
    }

    public TileStatus getTileStatus() {
        return tileStatus;
    }

    public void setTileStatus(TileStatus tileStatus)
    {
        this.tileStatus = tileStatus;
    }

    public Donut getDonut() {
        return donut;
    }

    public void setDonut(Donut donut) {
        this.donut = donut;
    }


}
