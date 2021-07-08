package org.game.donutbattle.Board;

/**
 * Class for Board. Board class is used for each player to save values of tiles.
 */
public class Board {

    private final BoardSize boardSize;
    private Tile[][] tiles;

    public Board(BoardSize boardSize){
        this.boardSize = boardSize;
        initializeBoard();
    }

    public BoardSize getBoardSize() {
        return this.boardSize;
    }

    /**
     * Returns exact Tile using x and y values from tiles Multidimensional array
     * @param x
     * @param y
     * @return tiles[x][y]
     */
    public Tile getTile(int x, int y){
        return this.tiles[x][y];
    }

    /**
     * Initialize Multidimensional array tiles.
     * Each Tile TileStatus is set to Empty
     */
    public void initializeBoard(){
        this.tiles = new Tile[boardSize.getDim().width][boardSize.getDim().height];
        for(int i = 0; i < this.tiles.length; i++){
            for(int j = 0; j < this.tiles[i].length; j++){
                    this.tiles[i][j] = new Tile(TileStatus.EMPTY);
            }
        }
    }

    /**
     * Check each tile for TileStatus. If TileStatus = TileStatus.SHIP, add one to counter.
     * @return donuts != 0
     */
    public boolean hasDonutsAlive(){
        int donuts = 0;
        for(int i = 0; i < this.tiles.length; i++){
            for(int j = 0; j < this.tiles[i].length; j++){
                if(this.tiles[i][j].getTileStatus() == TileStatus.SHIP)
                    donuts ++;
            }
        }
        return donuts != 0;
    }
}
