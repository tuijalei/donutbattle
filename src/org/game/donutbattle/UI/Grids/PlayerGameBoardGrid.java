package org.game.donutbattle.UI.Grids;

import javafx.scene.image.ImageView;
import org.game.donutbattle.Game;
import org.game.donutbattle.Player.Player;

/**
 * Advanced class from DefaultBoardGrid for NewGameBoardGrid.
 */
public class PlayerGameBoardGrid extends DefaultBoardGrid {

    public PlayerGameBoardGrid(Player player) {
        super(player, Game.SMALL_GRID_SIZE);
    }

    @Override
    void initialize(){
        player.setPlayerGameBoardGrid(this);
        initTilesWithTileStatus();
        initGridPane();
    }

    /**
     * Update given tale some style
     * @param pane
     */
    @Override
    void updateTileByStatusPane(TilePane pane){
        switch(pane.getTileStatus()){
            case SHIP:
                pane.setStyle("-fx-background-color: #27ae60");
                break;
            case HIT:
            {
                ImageView imageView = uiStyler.hitImage();
                imageView.setScaleX(0.5);
                imageView.setScaleY(0.5);
                imageView.relocate(-8, -8);
                pane.getChildren().add(imageView);
            }
                break;
            case MISS:
            {
                ImageView imageView = uiStyler.missImage();
                imageView.setScaleX(0.5);
                imageView.setScaleY(0.5);
                imageView.relocate(-8, -8);
                pane.getChildren().add(imageView);
            }
                break;
            default:
                pane.setStyle("-fx-background-color: #FFC0CB");
        }
    }
}
