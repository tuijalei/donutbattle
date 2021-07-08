package org.game.donutbattle.UI.Grids;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.game.donutbattle.Board.TileStatus;
import org.game.donutbattle.Player.Player;
import org.game.donutbattle.UI.UIStyler;

/**
 * Abstact Class that is used for different Board (GridPane)
 */
public abstract class DefaultBoardGrid {

    final Player player;
    final UIStyler uiStyler = new UIStyler();

    GridPane gridPane;
    Pane[][] gameTilePanes;

    final int gridSize;

    public DefaultBoardGrid(Player player, int gridSize){
        this.player = player;
        this.gridSize = gridSize;
        initialize();
    }

    public GridPane getGridPane(){
        return this.gridPane;
    }

    /**
     * Initialize is abstract as we want each sub class to be unique.
     */
    abstract void initialize();

    /**
     * Initialize gameTilePanes Panes aka TilePanes with TileStatus.Empty.
     */
    void initTiles(){
        int width = this.player.getBoard().getBoardSize().getDim().width;
        int height = this.player.getBoard().getBoardSize().getDim().height;

        this.gameTilePanes = new TilePane[width][height];

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++) {
                this.gameTilePanes[i][j] = getNewTile(i, j);
            }
        }
    }

    /**
     * Initialize gameTilePanes aka TilePanes with given TileStatus that is taken from player Board Tile.
     */
    void initTilesWithTileStatus(){
        int width = this.player.getBoard().getBoardSize().getDim().width;
        int height = this.player.getBoard().getBoardSize().getDim().height;

        this.gameTilePanes = new TilePane[width][height];

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++) {
                this.gameTilePanes[i][j] = getNewTile(i, j, this.player.getBoard().getTile(i, j).getTileStatus());
            }
        }
    }

    /**
     * Initialize GridPane
     */
    void initGridPane(){
        int width = this.player.getBoard().getBoardSize().getDim().width;
        int height = this.player.getBoard().getBoardSize().getDim().height;

        this.gridPane = new GridPane();

        for(int i = 0; i < width; i++){
            ColumnConstraints col = new ColumnConstraints(this.gridSize);
            col.setHalignment(HPos.LEFT);
            this.gridPane.getColumnConstraints().add(col);
        }
        for(int j = 0; j < height; j++) {
            RowConstraints row = new RowConstraints(this.gridSize);
            row.setValignment(VPos.TOP);
            this.gridPane.getRowConstraints().add(row);
        }

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++) {
                this.gridPane.add(this.gameTilePanes[i][j], i, j);
            }
        }
    }

    public TilePane getTilePane(int x, int y){
        return (TilePane) this.gameTilePanes[x][y];
    }

    /**
     * Gives new Pane. Pane has set default values as well pane data is updated by it TileStatus(TileStatus is EMPTY).
     * @param x
     * @param y
     * @return
     */
    public Pane getNewTile(int x, int y){
        TilePane pane = new TilePane(x, y);
        defaultValuesToPane(pane);
        updateTileByStatusPane(pane);
        return pane;
    }

    /**
     * Gives new Pane. Pane has set default values as well pane data is updated by it TileStatus(Is given).
     * @param x
     * @param y
     * @return
     */
    Pane getNewTile(int x, int y, TileStatus tileStatus){
        TilePane pane = new TilePane(x, y, tileStatus);
        defaultValuesToPane(pane);
        updateTileByStatusPane(pane);
        return pane;
    }

    /**
     * Update given tale some style
     * @param pane
     */
    void updateTileByStatusPane(TilePane pane){
        switch(pane.getTileStatus()){
            case SHIP:
                pane.setStyle("-fx-background-color: #27ae60");
                break;
            case HIT:
                pane.getChildren().add(uiStyler.hitImage());
                break;
            case MISS:
                pane.getChildren().add(uiStyler.missImage());
                break;
            default:
                pane.setStyle("-fx-background-color: #FFC0CB");
        }
    }

    /**
     * funtion set TilePane (Pane) default values.
     * @param pane
     */
    void defaultValuesToPane(TilePane pane){
        pane.setPrefSize(this.gridSize, this.gridSize);
        pane.setBorder(getBorder(Color.WHITE));
    }

    Border getBorder(Color color){
        return new Border(new BorderStroke(color, BorderStrokeStyle.SOLID,null, new BorderWidths(1)));
    }
}
