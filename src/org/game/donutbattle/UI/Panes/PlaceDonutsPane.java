package org.game.donutbattle.UI.Panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import org.game.donutbattle.Game;
import org.game.donutbattle.GameStage;
import org.game.donutbattle.Player.Player;
import javafx.beans.binding.Bindings;
import org.game.donutbattle.UI.Grids.PlaceDonutsBoardGrid;
import org.game.donutbattle.UI.Lists.PlaceDonutsList;
import org.game.donutbattle.UI.UIHandler;
import org.game.donutbattle.UI.UIHelper;
import org.game.donutbattle.UI.UIStyler;

/**
 * Pane for setting up the baords
 * Each player have their own view one after one
 */
public class PlaceDonutsPane extends Pane {

    private final Player player;
    private final UIHelper uiHelper;
    private final UIStyler uiStyler;
    private UIHandler uiHandler;
    private final int[] finalDonutsCount;
    private int[] currentDonutsCount;

    private PlaceDonutsBoardGrid donutsBoardGrid;

    private GridPane placeDonutPane;
    private VBox playerNameBox = new VBox();
    private Label playerNameLabel = new Label();
    private String donutLabel = "Selected donuts for the game:";
    private PlaceDonutsList donutList;

    private HBox instBox = new HBox();
    private Label instLabel = new Label("Place your donuts on the board. You may drag and drop each donut." +
                                                " They must be placed horizontally or vertically." +
                                                " You can rotate one by pressing R." +
                                                " Donuts can touch each other but can't occupy the same grid." +
                                                " After the game begins, you can't change the positions anymore.");

    private Button nextButton = new Button();
    private Button clearButton = new Button("Clear board");
    private Button cancelButton = new Button("Back to main menu");
    private HBox buttonBox = new HBox();

    public PlaceDonutsPane(UIHandler uiHandler, UIHelper uiHelper, Player player, int[] donutsCount){
        this.uiHandler = uiHandler;
        this.uiHelper = uiHelper;
        this.player = player;
        this.finalDonutsCount = donutsCount;
        this.currentDonutsCount = donutsCount;
        this.uiStyler = new UIStyler();
        this.playerNameLabel.setText(this.player.getName());
        initialize();
    }

    /**
     * Initializing Pane
     */
    private void initialize(){
        // Let's init the grid for PlaceDonutPane
        this.placeDonutPane = uiHelper.getLayoutGrid();
        this.donutsBoardGrid = new PlaceDonutsBoardGrid(uiHelper, this.player);
        this.donutList = new PlaceDonutsList(this.uiHelper, "", this.currentDonutsCount);

        this.setOnKeyPressed(e -> {
            KeyCode keyCode = e.getCode();
            if (keyCode.equals(KeyCode.R)) {
                this.donutList.updateDonutsView();
                return;
            }
        });

        GridPane donutListGridPane = this.donutList.getDonutListGridPane();

        /** left side stuff **/
        // Player name
        playerNameLabel.setTextAlignment(TextAlignment.CENTER);
        uiStyler.amaticLabelStyler(playerNameLabel, 50);

        playerNameBox.getChildren().add(playerNameLabel);
        playerNameBox.setAlignment(Pos.CENTER);
        playerNameBox.setBorder(uiStyler.borderWithBS(Color.WHITE, BorderStrokeStyle.SOLID, 1));

        // List of donuts and their selected amount for the game
        donutListGridPane.setAlignment(Pos.CENTER);
        donutListGridPane.setBorder(uiStyler.borderWithBS(Color.WHITE, BorderStrokeStyle.SOLID, 1));
        GridPane.setRowSpan(donutListGridPane,2);

        /** right side stuff **/
        // Instructions
        instBox.getChildren().add(instLabel);
        instBox.setAlignment(Pos.CENTER);
        instBox.setBorder(uiStyler.borderWithBS(Color.WHITE, BorderStrokeStyle.SOLID, 1));
        uiStyler.ralewayLabelStyler(instLabel, 15);
        instLabel.setTextAlignment(TextAlignment.JUSTIFY);
        instLabel.setWrapText(true);
        instLabel.setPadding(new Insets(15));
        placeDonutPane.add(instBox, 1,0);

        // Gamegrid where you can drag and drop items
        GridPane rightStuffPane = new GridPane();
        rightStuffPane.setAlignment(Pos.CENTER);
        rightStuffPane.getChildren().add(this.donutsBoardGrid.getGridPane());

        // Styling some buttons
        uiStyler.buttonStyler(nextButton);
        uiStyler.buttonStyler(clearButton);
        uiStyler.buttonStyler(cancelButton);

        // Next button textProperty binding to player -> different texts based on whose turn
        nextButton.textProperty().bind(Bindings.when(
                Bindings.createBooleanBinding(() -> player == Game.PLAYER1))
                .then("Next set up").otherwise("Let's start the game!"));
        buttonBox.getChildren().addAll(clearButton, nextButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        // Board can be cleared and donuts placed again
        // Also donutLabels updated back to original numbers
        clearButton.setOnAction(e -> {
            clearGridFromDonuts();
        });

        // Setups can be cancelled!
        // Returning to NewGamePane
        cancelButton.setOnAction(event -> {
            //uiHandler.restartGame();
            Game.PLAYER1 = null;
            Game.PLAYER2 = null;
            Game.GAME_STAGE = GameStage.INITIALIZE_GAME;
            this.uiHandler.updateView();
        });

        // Button action, next pane depending on whose turn for setup
        // if player1's -> player2's PlaceDonutPane
        // if player2's -> player1's PlayerGameBoard
        nextButton.setOnAction(e -> {
            nextButtonAction();
        });

        // Adding everything together
        placeDonutPane.add(playerNameBox, 0,0);
        placeDonutPane.add(donutListGridPane, 0, 1);
        placeDonutPane.add(rightStuffPane, 1, 1);
        placeDonutPane.add(buttonBox, 1, 2);

        // Adding placeDonutPane to "game view"
        super.getChildren().add(placeDonutPane);
    }

    /**
     * Clearing the board and updating label values back
     */
    private void clearGridFromDonuts(){
        this.donutsBoardGrid.resetGrid();
        this.donutList.resetLabels();
    }

    /**
     * Checking if all required donuts are placed and if outcome of that is true save player board and updateView.
     * However if its not true it displays error message.
     */
    private void nextButtonAction(){
        if(this.donutList.donutsLeftToPlace() != 0){
            uiHelper.errorMsg("Missed donut error!", "You missed a donut! (How's that even possible?) "
                                                            + "\nPut them all on the board, please.");
            return;
        }
        if(player==Game.PLAYER1) {
            Game.GAME_STAGE = GameStage.PLAYER_2_SET_UP;
            donutsBoardGrid.updatePlayerBoard();
            this.uiHandler.updateView();
        } else {
            Game.GAME_STAGE = GameStage.PLAYER_1_TURN;
            donutsBoardGrid.updatePlayerBoard();
            this.uiHandler.resetPlayerGameBoardPane();
            uiHandler.playerCheckPane();
        }
    }

}
