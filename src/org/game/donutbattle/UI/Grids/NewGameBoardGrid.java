package org.game.donutbattle.UI.Grids;

import org.game.donutbattle.Board.Board;
import org.game.donutbattle.Board.BoardSize;
import org.game.donutbattle.Game;
import org.game.donutbattle.Player.Player;

/**
 * Advanced class from DefaultBoardGrid for NewGameBoardGrid.
 */
public class NewGameBoardGrid extends DefaultBoardGrid {

    public NewGameBoardGrid(BoardSize boardSize) {
        super(new Player(boardSize), Game.BIG_GRID_SIZE);
    }

    @Override
    void initialize() {
        initTiles();
        initGridPane();
    }

    /**
     * Update given tale some style
     * @param pane
     */
    @Override
    void updateTileByStatusPane(TilePane pane){
        switch(pane.getTileStatus()){
            default:
                pane.setStyle("-fx-background-color: #FFC0CB");
        }
    }

    /**
     * To resize gridpane data by given boardSize
     * @param boardSize
     */
    public void resizeGrid(BoardSize boardSize){
        player.setBoard(new Board(boardSize));
        initTiles();
        initGridPane();
    }
}
