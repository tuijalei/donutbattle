package org.game.donutbattle.UI.Grids;

import javafx.scene.layout.Pane;
import org.game.donutbattle.Board.TileStatus;

import java.util.Objects;

/**
 * TilePane is almost same class as Tile, difference is that this is used for UI and have extends Pane.
 * This is later used to give Tile same TileStatus as this TilePane have.
 */
public class TilePane extends Pane {

    private final int x, y;
    private TileStatus tileStatus;
    private int donutId;

    public TilePane(int x, int y){
        this.x = x;
        this.y = y;
        this.tileStatus = TileStatus.EMPTY;
        this.donutId = -1;
    }

    public TilePane(int x, int y, TileStatus tileStatus){
        this.x = x;
        this.y = y;
        this.tileStatus = tileStatus;
        this.donutId = -1;
    }

    public TileStatus getTileStatus(){
        return this.tileStatus;
    }

    public void setTileStatus(TileStatus tileStatus){
        this.tileStatus = tileStatus;
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }

    public int getDonutId(){ return this.donutId; }
    public void setDonutId(int donutId){ this.donutId = donutId;}

    @Override
    public int hashCode() {
        return Objects.hash(x, y, tileStatus);
    }
}
