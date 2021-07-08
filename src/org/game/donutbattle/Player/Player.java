package org.game.donutbattle.Player;

import org.game.donutbattle.Board.Board;
import org.game.donutbattle.Board.BoardSize;
import org.game.donutbattle.UI.Grids.PlayerGameBoardGrid;

/**
 * Player class that saves most important data for each player.
 */
public class Player {

    private String name;
    private Board board;
    private PlayerGameBoardGrid playerGameBoardGrid;

    public Player(BoardSize boardSize){
        this.name = "";
        this.board = new Board(boardSize);
    }

    public Player(String name, Board board){
        this.name = name;
        this.board = board;
    }

    public void setPlayerGameBoardGrid(PlayerGameBoardGrid playerGameBoardGrid) {
        this.playerGameBoardGrid = playerGameBoardGrid;
    }

    public PlayerGameBoardGrid getPlayerGameBoardGrid(){return this.playerGameBoardGrid;}

    public String getName() { return this.name;}

    public Board getBoard() { return this.board;}
    public void setBoard(Board board) { this.board = board;}

    @Override
    public String toString(){
        return this.name;
    }
}
