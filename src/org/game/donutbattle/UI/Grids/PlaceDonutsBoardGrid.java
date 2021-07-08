package org.game.donutbattle.UI.Grids;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import org.game.donutbattle.Board.Board;
import org.game.donutbattle.Board.TileStatus;
import org.game.donutbattle.Game;
import org.game.donutbattle.Player.Player;
import org.game.donutbattle.UI.UIHelper;
import java.util.ArrayList;

/**
 * Advanced class from DefaultBoardGrid for PlaceDonutsBoardGrid.
 */
public class PlaceDonutsBoardGrid extends DefaultBoardGrid {

    private final UIHelper uiHelper;
    private int donutId;
    private TilePane oldDonutTile;

    public PlaceDonutsBoardGrid(UIHelper uiHelper, Player player) {
        super(player, Game.BIG_GRID_SIZE);
        this.uiHelper = uiHelper;
        this.donutId = 0;
    }

    @Override
    void initialize(){
        initTiles();
        initGridPane();
        dragAndDrop();
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
     * Method that handles Drag and and drop from gridpane.
     */
    void dragAndDrop(){
        gridPane.setOnDragDetected(e -> {
            disableImages();
            if(e.getTarget() instanceof ImageView) {
                ImageView donuts = (ImageView) e.getTarget();

                Dragboard dragboard = donuts.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();

                Node node = e.getPickResult().getIntersectedNode();
                Integer colIndex = GridPane.getColumnIndex(node);
                Integer rowIndex = GridPane.getRowIndex(node);
                int x = (colIndex == null) ? 0 : colIndex;
                int y = (rowIndex == null) ? 0 : rowIndex;

                oldDonutTile = getTilePane(x, y);
                content.putImage(donuts.getImage());
                dragboard.setDragView(donuts.getImage(), 16, 16);
                dragboard.setContent(content);
                e.consume();
            }
        });

        gridPane.setOnDragExited(e -> {
            enableImages();
        });

        gridPane.setOnMouseDragged(e -> {
        });

        gridPane.setOnDragDone(e -> {
        });

        gridPane.setOnDragOver(e -> {
            if(e.getDragboard().hasImage()){
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            e.consume();
        });

        // Drag-and-drop gesture entered the board grid
        // Show which tiles are selected for donut by filling them light yellow color
        gridPane.setOnDragEntered(e -> {
            if(e.getGestureSource() != gridPane && e.getDragboard().hasImage()) {

            }
            e.consume();
        });

        // Drag dropped draws the image to the board grid
        // If an image on dragboard -> read it and use it
        gridPane.setOnDragDropped(e -> {
            boolean dragSuccess = false;
            enableImages();
            Node node = e.getPickResult().getIntersectedNode();
            if(e.getGestureSource() != gridPane && e.getDragboard().hasImage()){
                Integer colIndex = GridPane.getColumnIndex(node);
                Integer rowIndex = GridPane.getRowIndex(node);
                int x = (colIndex == null) ? 0 : colIndex;
                int y = (rowIndex == null) ? 0 : rowIndex;
                int size = uiHelper.getDonutSize((int) e.getDragboard().getImage().getHeight(), (int) e.getDragboard().getImage().getWidth());
                int rotation = e.getDragboard().getImage().getWidth() > 31 ? 0 : 90;

                if(!(e.getTarget() instanceof ImageView)){
                    if(addDonuts(e.getDragboard().getImage(), x, y, size, rotation)){
                        dragSuccess = true;
                    }
                }
            }
            e.setDropCompleted(dragSuccess);
            e.consume();
        });
    }

    /**
     * Disables every gridPane children that is instance of ImageView
     */
    void disableImages(){
        gridPane.getChildren().stream().filter(i -> (i instanceof ImageView)).forEach(i -> i.setDisable(true));
    }

    /**
     * Enables every gridPane children that is instance of ImageView
     */
    void enableImages(){
        gridPane.getChildren().stream().filter(i -> (i instanceof ImageView)).forEach(i -> i.setDisable(false));
    }

    /**
     * Checks if coordinates are empty for new ship and by given results interrupt it or finished it.
     * @param image
     * @param x
     * @param y
     * @param size
     * @param rotation
     * @return
     */
    boolean addDonuts(Image image, int x, int y, int size, int rotation){
        if(!positionEmpty(x, y, size, rotation))
            return false;

        if(oldDonutTile != null)
            removeOldDonut();

        ImageView tempImg = new ImageView((image));

        switch(rotation){
            case 0:
                for(int i = x; i < x + size; i++){
                    TilePane currentTile = getTilePane(i, y);
                    currentTile.setDonutId(this.donutId);
                    currentTile.setTileStatus(TileStatus.SHIP);
                }
                break;
            case 90:
                for(int i = y; i < y + size; i++){
                    TilePane currentTile = getTilePane(x, i);
                    currentTile.setDonutId(this.donutId);
                    currentTile.setTileStatus(TileStatus.SHIP);
                }
                break;
            default:
                break;
        }

        oldDonutTile = null;
        gridPane.add(tempImg, x,y);
        this.donutId++;
        return true;
    }


    /**
     * Checks if given position (+size) is empty.
     * @param x
     * @param y
     * @param size
     * @param rotation
     * @return
     */
    boolean positionEmpty(int x, int y, int size, int rotation){
        try{
            switch(rotation){
                case 0:
                    for(int i = x; i < x + size; i++){
                        TilePane currentTile = getTilePane(i, y);
                        if(currentTile.getDonutId() != -1){
                            if(oldDonutTile == null || (oldDonutTile.getDonutId() != currentTile.getDonutId())){
                                return false;
                            }
                        }
                    }
                    break;
                case 90:
                    StringBuilder s = new StringBuilder("postion Check: ");
                    for(int i = y; i < y + size; i++){
                        TilePane currentTile = getTilePane(x, i);
                        if(currentTile.getDonutId() != -1){
                            if(oldDonutTile == null || (oldDonutTile.getDonutId() != currentTile.getDonutId())){
                                return false;
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
            return true;
        }catch (IndexOutOfBoundsException err){
            return false;
        }
    }

    /**
     * clears oldDonutTile and every Tile that is has same donutId
     */
    void removeOldDonut(){
        int x = oldDonutTile.getX();
        int y = oldDonutTile.getY();
        int donutId = getTilePane(x, y).getDonutId();
        ImageView imageView = getImageView(x,y);

        if(donutId == -1)
            return;

        for(int i = 0; i < gameTilePanes.length; i++){
            for(int j = 0; j < gameTilePanes[i].length; j++){
                TilePane tile = getTilePane(i, j);
                if(donutId == tile.getDonutId()){
                    tile.setDonutId(-1);
                    tile.setTileStatus(TileStatus.EMPTY);
                }
            }
        }
        gridPane.getChildren().remove(imageView);
        oldDonutTile = null;
    }

    /**
     * Gives ImageView that is at x,y gridpane coordinates.
     * @param x
     * @param y
     * @return
     */
    ImageView getImageView(int x, int y){
        ArrayList<ImageView> imageViews = new ArrayList<>();
        gridPane.getChildren().stream().filter(grid -> (grid instanceof ImageView)).forEach(imgView -> imageViews.add((ImageView)imgView));
        for(ImageView i : imageViews){
            if(GridPane.getColumnIndex(i) == x && GridPane.getRowIndex(i) == y)
                return i;
        }
        return null;
    }

    /**
     * clears gridPane completely
     */
    public void resetGrid(){
        for(int i = 0; i < gameTilePanes.length; i++){
            for(int j = 0; j < gameTilePanes[i].length; j++){
                getTilePane(i, j).setTileStatus(TileStatus.EMPTY);
                getTilePane(i, j).setDonutId(-1);
            }
        }
        ArrayList<ImageView> imageViews = new ArrayList<>();
        gridPane.getChildren().stream().filter(grid -> (grid instanceof ImageView)).forEach(imgView -> imageViews.add((ImageView)imgView));

        for(ImageView img : imageViews){
            gridPane.getChildren().remove(img);
        }
    }

    /**
     * Updates player Board TileStatus same as gridPane TileStatus
     */
    public void updatePlayerBoard(){
        Board board = this.player.getBoard();

        for(int i = 0; i < this.gameTilePanes.length; i++){
            for(int j = 0; j < this.gameTilePanes[i].length; j++){
                board.getTile(i, j).setTileStatus(getTilePane(i, j).getTileStatus());
            }
        }
    }
}
