package org.game.donutbattle.UI.Grids;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import org.game.donutbattle.Board.Tile;
import org.game.donutbattle.Board.TileStatus;
import org.game.donutbattle.Game;
import org.game.donutbattle.GameStage;
import org.game.donutbattle.Player.Player;
import org.game.donutbattle.UI.UIHandler;
import java.util.concurrent.*;

/**
 * Main class that handles donuts eating and playground grid for it.
 */
public class EatDonutsGrid extends DefaultBoardGrid {

    private final UIHandler uiHandler;
    private TilePane selectedPane;

    public EatDonutsGrid(UIHandler uiHandler, Player player){
        super(player, Game.BIG_GRID_SIZE);
        this.uiHandler = uiHandler;
    }

    @Override
    void initialize(){
        this.selectedPane = null;
        initTiles();
        initGridPane();
    }

    @Override
    void defaultValuesToPane(TilePane pane){
        pane.setPrefSize(super.gridSize, super.gridSize);
        pane.setBorder(getBorder(Color.WHITE));
        pane.setOnMouseClicked(event -> {
            selectTile(pane);
        });
    }

    /**
     * Gives selectedPane pane that is given as well as gives that Pane border
     * @param pane
     */
    void selectTile(TilePane pane){
        if(pane.getTileStatus() == TileStatus.EMPTY){
            if(this.selectedPane != null)
                unSelectTile();

            this.selectedPane = pane;
            pane.setBorder(getBorder(Color.RED));
        }
    }

    /**
     * Set selectedTile value to 0 as well removes border from selected tile
     */
    void unSelectTile(){
        this.selectedPane.setBorder(getBorder(Color.WHITE));
        this.selectedPane = null;
    }

    /**
     * Method for "Fire" button that check selectedTile has donut (TileStatus.SHIP) and does actions by result of it.
     * @param btn
     */
    public void eatSelectedPane(Button btn){
        if(this.selectedPane == null)
            return;

        TileStatus tileStatus = eatEnemyShip(this.selectedPane.getX(), this.selectedPane.getY());
        this.selectedPane.setTileStatus(tileStatus);
        updateTileByStatusPane(this.selectedPane);

        TilePane tilePane = player.getPlayerGameBoardGrid().getTilePane(this.selectedPane.getX(), this.selectedPane.getY());
        tilePane.setTileStatus(tileStatus);
        player.getPlayerGameBoardGrid().updateTileByStatusPane(tilePane);
        unSelectTile();

        if(!player.getBoard().hasDonutsAlive()){
            Game.WINNER = player;
            endGame(btn);
        }

        if(tileStatus == TileStatus.MISS){
            missShot(btn);
        }
    }

    /**
     * Child method for eatSelectedPane. This method check certain Tile for it status and changes it.
     * @param x
     * @param y
     * @return
     */
    private TileStatus eatEnemyShip(int x, int y){
        Tile enemyTile = player.getBoard().getTile(x, y);

        if(enemyTile == null || enemyTile.getTileStatus() == null)
            return TileStatus.MISS;

        if(enemyTile.getTileStatus() == TileStatus.SHIP){
            enemyTile.setTileStatus(TileStatus.HIT);
            return TileStatus.HIT;
        }
        enemyTile.setTileStatus(TileStatus.MISS);
        return TileStatus.MISS;
    }

    /**
     * Checks if enemy player doesn't have any more TileStatus.Ship tiles and makes decision of the given result.
     * result true -> updates Game_STAGE and returns to GameOverPane.
     * result false -> Give player another bite (shot).
     * @param btn
     */
    private void endGame(Button btn){
        btn.setDisable(true);
        Game.WINNER = Game.getWinner();
        Game.GAME_STAGE = GameStage.GAME_OVER;
        uiHandler.resetGameOverPane();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        ScheduledFuture scheduledFuture = scheduledExecutorService.schedule(
                new Callable() {
                    public Object call() throws Exception {
                        uiHandler.updateView();
                        return "";
                    }
                },2, TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();
    }

    /**
     * If Player doesn't hit enemy donut this method is called. This method sets button disabled that is given and changes view
     * to playerCheckPane for next player to check in.
     * @param btn
     */
    private void missShot(Button btn){
        btn.setDisable(true);
        Game.GAME_STAGE = GameStage.getNextPlayerTurn(Game.GAME_STAGE);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        ScheduledFuture scheduledFuture = scheduledExecutorService.schedule(
                new Callable() {
                    public Object call() throws Exception {
                        uiHandler.playerCheckPane();
                        btn.setDisable(false);
                        return "";
                    }
                },2, TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();
    }
}