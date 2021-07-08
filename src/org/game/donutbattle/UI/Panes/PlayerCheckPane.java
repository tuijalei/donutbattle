package org.game.donutbattle.UI.Panes;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.game.donutbattle.Game;
import org.game.donutbattle.GameStage;
import org.game.donutbattle.UI.UIHandler;
import org.game.donutbattle.UI.UIStyler;

/**
 * Pane for letting player know whose turn it is
 */
public class PlayerCheckPane extends Pane {

    private final UIHandler uiHandler;
    private final UIStyler uiStyler;
    private Label label;

    public PlayerCheckPane(UIHandler uiHandler){
        this.uiHandler = uiHandler;
        this.uiStyler = new UIStyler();
        initialize();
    }

    /**
     * Initialize Pane
     */
    private void initialize(){
        // Base grid
        GridPane gp = new GridPane();
        BorderPane bPane = new BorderPane();
        gp.getChildren().add(bPane);

        gp.setPadding(new Insets(6));

        bPane.setPrefSize(768, 550);
        bPane.setPadding(new Insets(20, 10, 20, 10));
        bPane.setBorder(this.uiStyler.borderWithBS(Color.WHITE, BorderStrokeStyle.SOLID, 3));
        GridPane.setMargin(gp, new Insets(5));


        this.label = new Label("Waiting for player X to check!");
        this.uiStyler.ralewayLabelStyler(this.label, 17);

        // VBox for label and button
        VBox vBox = new VBox(this.label, checkButton());
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);

        bPane.setCenter(vBox);
        super.getChildren().add(gp);
    }

    /**
     * Set this Pane to visible and updates label text.
     */
    public void showCheckPane(){
        if(Game.GAME_STAGE == GameStage.PLAYER_1_TURN){

            Platform.runLater(() -> {
                this.label.setText("Waiting for "+Game.PLAYER1.getName()+" to check!");
            });
        }else {
            Platform.runLater(() -> {
                this.label.setText("Waiting for "+Game.PLAYER2.getName()+" to check!");
            });
        }
        super.setVisible(true);
    }

    /**
     * Getting CheckButton - setting style and action for the button
     */
    private Button checkButton(){
        Button button = new Button("Check");
        uiStyler.buttonStyler(button);
        button.setPadding(new Insets(10));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                uiHandler.updateView();
            }
        });
        return button;
    }
}
