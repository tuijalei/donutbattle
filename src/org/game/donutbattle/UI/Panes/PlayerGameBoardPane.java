package org.game.donutbattle.UI.Panes;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.game.donutbattle.Player.Player;
import org.game.donutbattle.UI.Grids.EatDonutsGrid;
import org.game.donutbattle.UI.Grids.PlayerGameBoardGrid;
import org.game.donutbattle.UI.UIHandler;
import org.game.donutbattle.UI.UIHelper;
import org.game.donutbattle.UI.UIStyler;

/**
 * THE GAME PANE - initializing both boardViews (enemy and player's whose turn it is)
 */
public class PlayerGameBoardPane extends Pane {

    private final UIHandler uiHandler;
    private final UIHelper uiHelper;
    private final UIStyler uiStyler;
    private final Player player;

    private final EatDonutsGrid eatDonutsGrid;

    private Button button;
    private GridPane imageList = new GridPane();
    private Label imageLabel = new Label("Tiles:");
    private Label hitLabel = new Label("It's a hit!");
    private Label missLabel = new Label("You missed...");
    private VBox leftBox = new VBox(), playerBox = new VBox(), enemyBox = new VBox();

    GridPane gameBoardGridPane;
    PlayerGameBoardGrid playerBoard;

    public PlayerGameBoardPane(UIHandler uiHandler, UIHelper uiHelper, Player player, Player enemy){
        this.uiHandler = uiHandler;
        this.uiHelper = uiHelper;
        this.player = player;
        this.uiStyler = new UIStyler();
        this.playerBoard = new PlayerGameBoardGrid(player);
        this.eatDonutsGrid = new EatDonutsGrid(uiHandler, enemy);
        initialize();
    }

    /**
     * Initialize game board pane. This pane includes player game board and playground board.
     */
    private void initialize(){
        // Base grid initialization
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(6));
        this.gameBoardGridPane = gameBoardLayoutGrid();
        gp.add(gameBoardGridPane,0,0);

        this.gameBoardGridPane.setBorder(this.uiStyler.borderWithBS(Color.WHITE, BorderStrokeStyle.SOLID, 3));
        this.gameBoardGridPane.setPadding(new Insets(20, 10, 20, 10));
        GridPane.setMargin(this.gameBoardGridPane, new Insets(5));

        /** left side stuff**/
        // Player name box
        Label playerBoardLabel = new Label("Player gameboard");
        Label enemyBoardLabel = new Label("Enemy gameboard");
        uiStyler.amaticLabelStyler(playerBoardLabel, 35);
        uiStyler.amaticLabelStyler(enemyBoardLabel, 40);

        // Player name box
        playerBox.getChildren().add(playerBoardLabel);
        playerBox.setAlignment(Pos.CENTER);

        // Player board
        this.playerBoard.getGridPane().setAlignment(Pos.CENTER);
        // Image list for tiles
        this.uiStyler.ralewayBLabelStyler(imageLabel, 15);
        this.uiStyler.ralewayLabelStyler(hitLabel, 14);
        this.uiStyler.ralewayLabelStyler(missLabel, 14);
        this.imageList.add(imageLabel, 0,0);
        this.imageList.add(uiStyler.hitImage(), 0,1);
        this.imageList.add(uiStyler.missImage(), 0,2);
        this.imageList.add(hitLabel, 1,1);
        this.imageList.add(missLabel, 1,2);
        this.imageList.setVgap(15);
        this.imageList.setHgap(5);
        imageList.setBorder(this.uiStyler.borderWithBS(Color.WHITE, BorderStrokeStyle.SOLID, 1));
        imageList.setPadding(new Insets(20, 10,20,10));
        imageList.setAlignment(Pos.CENTER);

        // Left box -> playerBoard and imageList
        leftBox.getChildren().addAll(this.playerBoard.getGridPane(), imageList);
        leftBox.setSpacing(35);
        leftBox.setPadding(new Insets(10));
        leftBox.setAlignment(Pos.TOP_CENTER);

        /** right side stuff**/
        // Enemy's name box
        enemyBox.getChildren().add(enemyBoardLabel);
        enemyBox.setAlignment(Pos.CENTER);

        // Enemy board
        eatDonutsGrid.getGridPane().setAlignment(Pos.CENTER);
        // FIRE!
        this.button = eatButton();

        // Adding everything to base pane
        this.gameBoardGridPane.add(playerBox, 0, 0);
        this.gameBoardGridPane.add(enemyBox, 1, 0);
        this.gameBoardGridPane.add(leftBox, 0,1);
        this.gameBoardGridPane.add(this.eatDonutsGrid.getGridPane(), 1 ,1);
        this.gameBoardGridPane.add(this.button, 1,2);

        // Gridpane settings
        // GridPane.setHalignment(playerBoardLabel, HPos.CENTER);
        // GridPane.setValignment(playerBoardLabel, VPos.CENTER);
        GridPane.setHalignment(this.imageList, HPos.CENTER);
        GridPane.setHalignment(this.eatDonutsGrid.getGridPane(), HPos.CENTER);
        GridPane.setValignment(this.eatDonutsGrid.getGridPane(), VPos.CENTER);
        GridPane.setHalignment(this.button, HPos.CENTER);

        super.getChildren().add(gp);
    }

    /**
     * Getting FireButton - setting style and action
     */
    private Button eatButton(){
        Button btn = new Button("Fire!");
        uiStyler.buttonStyler(btn);
        btn.setPrefWidth(100);
        btn.setPrefHeight(35);
        btn.setOnAction(event -> {
            this.eatDonutsGrid.eatSelectedPane(btn);
        });
        return btn;
    }

    /**
     * Getting base grid for the pane
     */
    private GridPane gameBoardLayoutGrid(){
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(765, 500);
        gridPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        gridPane.setGridLinesVisible(false);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(35);
        col2.setPercentWidth(65);
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        RowConstraints row3 = new RowConstraints();
        row1.setPercentHeight(10);
        row2.setPercentHeight(80);
        row3.setPercentHeight(10);
        gridPane.getColumnConstraints().addAll(col1, col2);
        gridPane.getRowConstraints().addAll(row1, row2, row3);

        return gridPane;
    }
}
