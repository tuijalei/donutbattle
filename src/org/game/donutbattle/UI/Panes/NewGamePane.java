package org.game.donutbattle.UI.Panes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.game.donutbattle.Board.Board;
import org.game.donutbattle.Board.BoardSize;
import org.game.donutbattle.Game;
import org.game.donutbattle.GameStage;
import org.game.donutbattle.Player.Player;
import org.game.donutbattle.UI.Grids.NewGameBoardGrid;
import org.game.donutbattle.UI.Lists.NewGameDonutList;
import org.game.donutbattle.UI.UIHandler;
import org.game.donutbattle.UI.UIHelper;
import org.game.donutbattle.UI.UIStyler;

/**
 * Pane for new game - shown after launching
 */

public class NewGamePane extends Pane {

    private final UIHandler uiHandler;
    private final UIHelper uiHelper;
    private final UIStyler uiStyler;

    private VBox playerNameBox = new VBox();
    private Label playersNameLabel = new Label("Write your names here:");
    private TextField player1Field = new TextField("Player 1");
    private TextField player2Field = new TextField("Player 2");
    private String donutListLabelText = "Select the donuts! It's your choice." +
                                                "\nThey can fill max half of the board.";
    private Label maxDonutLabel = new Label("Max tiles donuts may cover:");
    private Label maxDonutCount = new Label("50");
    private Label donutsSelectedLabel = new Label("Tiles your selected donuts cover:");
    private Label donutsSelectedCount = new Label("");

    private VBox boardSizeMenuBox = new VBox();
    private Label boardSizeLabel = new Label("Choose the board size:");
    private Label title = new Label("DonutBattle");
    private VBox startButtonBox = new VBox();
    private Button startButton = new Button("Ready for board setups");

    private final BoardSize defaultBoardSize = BoardSize._10x10;

    NewGameBoardGrid newGameBoardGrid;

    public NewGamePane(UIHandler uiHandler, UIHelper uiHelper){
        this.uiHandler = uiHandler;
        this.uiHelper = uiHelper;
        this.uiStyler = new UIStyler();
        this.newGameBoardGrid = new NewGameBoardGrid(this.defaultBoardSize);
        initialize();
    }

    /**
     * Initialize Pane
     */
    private void initialize(){
        // Let's init the grid for NewGamePane
        GridPane newGamePane = uiHelper.getLayoutGrid();

        /** left side stuff **/
        // Textfields for setting players' names
        playerNameBox.setPadding(new Insets(15));
        playerNameBox.setSpacing(10);
        playerNameBox.setBorder(uiStyler.borderWithBS(Color.WHITE, BorderStrokeStyle.SOLID, 1));
        playerNameBox.setAlignment(Pos.CENTER);
        uiStyler.ralewayLabelStyler(playersNameLabel, 14);
        player1Field.setStyle(uiStyler.basicStringStyler());
        player2Field.setStyle(uiStyler.basicStringStyler());
        playerNameBox.getChildren().addAll(playersNameLabel, player1Field, player2Field);

        // List of donuts with comboBoxes and images
        NewGameDonutList donutList = new NewGameDonutList(this.uiHelper, this.donutListLabelText);
        GridPane donutListGrid = donutList.getDonutListGridPane();
        donutListGrid.setAlignment(Pos.CENTER);
        donutListGrid.setBorder(uiStyler.borderWithBS(Color.WHITE, BorderStrokeStyle.SOLID, 1));

        // Labels to see how many tiles selected donuts can cover
        // and how many tiles are covered by selected donuts
        uiStyler.ralewayLabelStyler(maxDonutLabel, 14);
        uiStyler.ralewayBLabelStyler(maxDonutCount, 15);
        uiStyler.ralewayLabelStyler(donutsSelectedLabel, 14);
        uiStyler.ralewayBLabelStyler(donutsSelectedCount, 15);

        GridPane.setRowSpan(donutListGrid,2);
        GridPane.setColumnSpan(maxDonutLabel, 2);
        GridPane.setColumnSpan(maxDonutCount, 2);
        GridPane.setColumnSpan(donutsSelectedLabel, 2);
        GridPane.setColumnSpan(donutsSelectedCount, 2);
        GridPane.setHalignment(maxDonutLabel, HPos.CENTER);
        GridPane.setHalignment(maxDonutCount, HPos.CENTER);
       // GridPane.setHalignment(donutsSelectedLabel, HPos.CENTER);
       // GridPane.setHalignment(donutsSelectedCount, HPos.CENTER);

        // Binding donutsSelectedCount label textProperty to donuts' combobox values
        //donutList.bindingLabel(donutsSelectedCount);

        // Adding donuts
        // comboBoxes and donut pictures in indexes 0-5
        donutListGrid.add(maxDonutLabel,0,6);
        donutListGrid.add(maxDonutCount, 0, 7);
       // donutListGrid.add(donutsSelectedLabel, 0, 8);
       // donutListGrid.add(donutsSelectedCount, 0, 9);

        /** right side stuff **/
        // Game title
        uiStyler.amaticLabelStyler(title, 50);

        // Nodes for board size selection
        ComboBox<BoardSize> boardSizeBox = uiHelper.boardSizeBox(200, this.defaultBoardSize);

        uiStyler.ralewayLabelStyler(boardSizeLabel, 14);
        boardSizeBox.setStyle(uiStyler.basicStringStyler());
        boardSizeMenuBox.setBorder(uiStyler.borderWithBS(Color.WHITE, BorderStrokeStyle.SOLID, 1));
        boardSizeMenuBox.setAlignment(Pos.CENTER);
        boardSizeMenuBox.getChildren().addAll(title, boardSizeLabel, boardSizeBox);
        boardSizeMenuBox.setSpacing(2);
        boardSizeMenuBox.setPadding(new Insets(5));

        // Panes for boardView
        GridPane rightStuffPane = new GridPane();
        rightStuffPane.setAlignment(Pos.CENTER);

        Pane boardView = new Pane();
        GridPane.setHalignment(boardView, HPos.CENTER);

        // first shown
        boardView.getChildren().add(this.newGameBoardGrid.getGridPane());

        // Let's make boardSizeBox and boardView stack work together
        // Binding also maxDonutCount label's textProperty to selectedSize
        boardSizeBox.setOnAction(e -> {
            BoardSize selectedSize = boardSizeBox.getSelectionModel().getSelectedItem();
            this.newGameBoardGrid.resizeGrid(selectedSize);
            boardView.getChildren().set(0, this.newGameBoardGrid.getGridPane());

            maxDonutCount.textProperty().bind(
                    new SimpleIntegerProperty(selectedSize.getDim().width*selectedSize.getDim().height/2).asString());
        });
        rightStuffPane.getChildren().add(boardView);

        // Button to start the game
        startButtonBox.getChildren().add(startButton);
        startButtonBox.setAlignment(Pos.CENTER);
        uiStyler.buttonStyler(startButton);

        // Checking if boardArea is twice as large as areaUsedForDonuts
        // If so, update GameStatus and paneView
        // Otherwise error for user
        startButton.setOnAction(e -> {
            readyForBoardSetupButton(boardSizeBox.getSelectionModel().getSelectedItem(), donutList.getDonutsValuesAsIntArr());
        });

        // Adding everything together
        newGamePane.add(playerNameBox, 0, 0);
        newGamePane.add(donutListGrid, 0, 1);
        newGamePane.add(boardSizeMenuBox, 1, 0);
        newGamePane.add(rightStuffPane, 1,1);
        newGamePane.add(startButtonBox, 1,2);
        // Adding newGamePane to "game view"
        super.getChildren().add(newGamePane);
    }

    /**
     * Set new values for Players and updates GameStage. As well check if selectedSize is big enough for required area.
     * @param selectedSize
     * @param donutsValues
     */
    private void readyForBoardSetupButton(BoardSize selectedSize, int[] donutsValues){
        int boardArea = selectedSize.getDim().width * selectedSize.getDim().height;

        if(boardArea >= this.uiHelper.areaRequiredForDonuts(donutsValues)){

            Game.GAME_STAGE = GameStage.PLAYER_1_SET_UP;
            Game.PLAYER1 = new Player(player1Field.getText(), new Board(selectedSize));
            Game.PLAYER2 = new Player(player2Field.getText(), new Board(selectedSize));

            this.uiHandler.resetPlaceDonutsPanes(donutsValues);
            this.uiHandler.updateView();
        } else {
            this.uiHelper.errorMsg("Too many donuts!",
                    "We know, we all love donuts but don't get greedy."
                            +"\nToo many donuts selected." +
                            "\n\nTry again!");
        }
    }
}
