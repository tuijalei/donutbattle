package org.game.donutbattle.UI.Panes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.game.donutbattle.Game;
import org.game.donutbattle.GameStage;
import org.game.donutbattle.Player.Player;
import org.game.donutbattle.UI.Grids.GameOverBoardGrid;
import org.game.donutbattle.UI.UIHandler;
import org.game.donutbattle.UI.UIHelper;
import org.game.donutbattle.UI.UIStyler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * Pane for the last view - gameOver - and showing who's the winner!
 */
public class GameOverPane extends Pane {

    private final UIHandler uiHandler;
    private final UIHelper uiHelper;
    private final UIStyler uiStyler;
    private final Player player1, player2;
    private final Media taDaSound;
    private final MediaPlayer mediaPlayer;

    public GameOverPane(UIHandler uiHandler, Player player1, Player player2){
        this.uiHandler = uiHandler;
        this.player1 = player1;
        this.player2 = player2;
        this.uiHelper = new UIHelper();
        this.uiStyler = new UIStyler();
        this.taDaSound = new Media(new File("resources/Sounds/cheering.mp3").toURI().toString());
        this.mediaPlayer = new MediaPlayer(taDaSound);
        initialize();
    }

    /**
     * Initialize Pane
     */
    private void initialize(){
        // Base grid
        GridPane gp = new GridPane();
        GridPane gameOverPane = new GridPane();
        Label label = getLabel();
        HBox hBox = new HBox();

        gp.getChildren().add(gameOverPane);
        gp.setPadding(new Insets(10));

        gameOverPane.setVgap(40);
        gameOverPane.setHgap(60);
        gameOverPane.setPrefSize(760, 540);
        gameOverPane.setPadding(new Insets(30));
        gameOverPane.setBorder(this.uiStyler.borderWithBS(Color.WHITE, BorderStrokeStyle.SOLID, 3));

        // Button box
        hBox.getChildren().addAll(getNewGameButton(), getExitGameButton());
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);

        gameOverPane.add(label, 0, 0);
        gameOverPane.add(new GameOverBoardGrid(this.player2).getGridPane(), 0, 1);
        gameOverPane.add(new GameOverBoardGrid(this.player2).getGridPane(), 1, 1);
        gameOverPane.add(hBox, 0,2);
        gameOverPane.setAlignment(Pos.CENTER);

        // GridPane setters
        GridPane.setMargin(gp, new Insets(6));
        GridPane.setColumnSpan(label, 2);
        GridPane.setColumnSpan(hBox, 2);
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setHalignment(hBox, HPos.CENTER);

        // Adding everything to "game view"
        super.getChildren().add(gp);
        // Adding some sound
        mediaPlayer.play();
    }

    /**
     * Label for winner with style
     */
    private Label getLabel(){
        Label label = new Label(Game.WINNER.getName()+" won!");
        label.setAlignment(Pos.CENTER);
        uiStyler.amaticLabelStyler(label, 60);
        return label;
    }

    /**
     * Getting NewGameButton - setting style and action
     */
    private Button getNewGameButton(){
        Button button = new Button("New Game");
        uiStyler.buttonStyler(button);
        button.setPadding(new Insets(10));
        button.setPrefWidth(100);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                uiHandler.resetGame();
                Game.GAME_STAGE = GameStage.INITIALIZE_GAME;
                uiHandler.updateView();
            }
        });
        return button;
    }

    /**
     * Getting ExitGameButton - setting style and action
     */
    private Button getExitGameButton(){
        Button button = new Button("Exit");
        uiStyler.buttonStyler(button);
        button.setPrefWidth(100);
        button.setPadding(new Insets(10));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                ((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
            }
        });
        return button;
    }
}
