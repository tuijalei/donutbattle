package org.game.donutbattle.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.game.donutbattle.Game;
import org.game.donutbattle.UI.Panes.*;

/**
 * This Class handles StackPane, StackPane child's and Application basic elements.
 */
public class UIHandler extends Application {

    //Application title
    private final String title = "DonutBattle made by Leinonen Tuija and Tuisk Janek!";
    //Application width and height
    private final int width = 800, height = 600;
    //Application resize able boolean
    private final boolean resizeAble = false;
    //StackPane Where we do add Panes that are initialized for game play
    private StackPane stack = new StackPane();
    //Class that includes some useful methods for ui style, methods and so on.
    private UIHelper uiHelper = new UIHelper();

    private NewGamePane newGamePane = new NewGamePane(this, uiHelper);
    private PlayerCheckPane playerCheckPane = new PlayerCheckPane(this);
    private GameOverPane gameOverPane;

    private PlayerGameBoardPane player1GamePane, player2GamePane;

    private PlaceDonutsPane player1PlaceDonutsPane, player2PlaceDonutsPane;

    /**
     * Clear StackPane and after that adds new NewGamePane as well PlayerCheckPane.
     */
    public void resetGame(){
        this.stack.getChildren().clear();
        this.newGamePane = new NewGamePane(this, uiHelper);
        this.playerCheckPane = new PlayerCheckPane(this);
        this.stack.getChildren().addAll(this.newGamePane, this.playerCheckPane);
    }

    /**
     * Initializing new PlaceDonutsPanes for game
     */
    public void resetPlaceDonutsPanes(int[] donutsCount){
        this.player1PlaceDonutsPane = new PlaceDonutsPane(this, this.uiHelper, Game.PLAYER1, donutsCount);
        this.player2PlaceDonutsPane = new PlaceDonutsPane(this, this.uiHelper, Game.PLAYER2, donutsCount);
        this.player1PlaceDonutsPane.setVisible(false);
        this.player1PlaceDonutsPane.setVisible(false);
        this.stack.getChildren().removeAll(player1PlaceDonutsPane, player2PlaceDonutsPane);
        this.stack.getChildren().addAll(player1PlaceDonutsPane, player2PlaceDonutsPane);
    }

    /**
     * Resets PlayerGameBoardPane. Removes old PlayerGameBoardPane values from StackPane and readd new PlayerGameBoardPane to StackPane.
     */
    public void resetPlayerGameBoardPane(){
        this.stack.getChildren().removeAll(this.player1GamePane, this.player2GamePane);
        this.player1GamePane = new PlayerGameBoardPane(this, uiHelper, Game.PLAYER1, Game.PLAYER2);
        this.player2GamePane = new PlayerGameBoardPane(this, uiHelper, Game.PLAYER2, Game.PLAYER1);
        this.player1GamePane.setVisible(false);
        this.player2GamePane.setVisible(false);
        this.stack.getChildren().addAll(this.player1GamePane, this.player2GamePane);
    }

    /**
     * Resets GameOverPane. Removes old PlayerGameBoardPane values from StackPane and readd new GameOverPane to StackPane.
     */
    public void resetGameOverPane(){
        this.gameOverPane = new GameOverPane(this, Game.PLAYER1, Game.PLAYER2);
        this.gameOverPane.setVisible(false);
        this.stack.getChildren().addAll(gameOverPane);
    }

    /**
     * Creates window for gameplay. Different stages in StackPane and those can be set visible/invisible
     */
    @Override
    public void start(Stage stage){
        stage.setTitle(this.title);
        stage.setWidth(this.width);
        stage.setHeight(this.height);
        stage.setResizable(this.resizeAble);
        this.stack.getChildren().addAll(newGamePane, playerCheckPane);
        this.stack.setBackground(new Background(new BackgroundFill(Color.web("#FFB6C1"), null, null)));
        StackPaneVisibleToFalse();
        updateView();
        Scene scene = new Scene(this.stack);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Hides every panes which are stored in StackPane
     */
    private void StackPaneVisibleToFalse(){
        this.stack.getChildren().forEach(pane -> pane.setVisible(false));
    }

    /**
     * Set a pane visible based on gameStage
     */
    public void updateView(){
        StackPaneVisibleToFalse();
        switch(Game.GAME_STAGE){
            case INITIALIZE_GAME:
                if(this.newGamePane != null)
                    this.newGamePane.setVisible(true);
                break;
            case PLAYER_1_SET_UP:
                if(this.player1PlaceDonutsPane != null)
                    this.player1PlaceDonutsPane.setVisible(true);
                break;
            case PLAYER_2_SET_UP:
                if(this.player2PlaceDonutsPane != null)
                    this.player2PlaceDonutsPane.setVisible(true);
                break;
            case PLAYER_1_TURN:
                if(this.player1GamePane != null)
                    this.player1GamePane.setVisible(true);
                break;
            case PLAYER_2_TURN:
                if(this.player2GamePane != null)
                    this.player2GamePane.setVisible(true);
                break;
            case GAME_OVER:
                if(this.gameOverPane != null)
                    this.gameOverPane.setVisible(true);
                break;
        }
    }

    /**
     * Sets a window that players can't see each others' gameboards
     */
    public void playerCheckPane(){
        StackPaneVisibleToFalse();
        playerCheckPane.showCheckPane();
    }
}
