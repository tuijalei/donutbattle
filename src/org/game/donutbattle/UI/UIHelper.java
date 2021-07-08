package org.game.donutbattle.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.game.donutbattle.Board.BoardSize;

/**
 * Some kinda container for methods needed in separate classes
 */
public class UIHelper {
    private UIStyler uiStyler = new UIStyler();

    /**
     * Imageviews for donuts
     */
    public ImageView donutsView(String path){
        return new ImageView(new Image(getClass().getResource(path).toExternalForm()));
    }

    /**
     * Implementing the base grid for Panes
     * @return Gridpane (2 cols, 3 rows)
     */
    public GridPane getLayoutGrid(){
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(783, 585);
        gridPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        gridPane.setPadding(new Insets(10));
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
        row1.setPercentHeight(25);
        row2.setPercentHeight(65);
        row3.setPercentHeight(5);
        gridPane.getColumnConstraints().addAll(col1, col2);
        gridPane.getRowConstraints().addAll(row1, row2, row3);

        return gridPane;
    }

    /**
     * VBox for players' names - can be filled
     */
    public VBox setPlayerNameBox(String topic, String placeholder1, String placeholder2){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(5, 5, 5, 5));
        vbox.setSpacing(10);
        vbox.setBorder(uiStyler.borderWithBS(Color.WHITE, BorderStrokeStyle.SOLID, 1));
        vbox.getChildren().addAll(new Label(topic), new TextField(placeholder1), new TextField(placeholder2));
        return vbox;
    }

    /**
     * ComboBox with different board sizes
     */
    public ComboBox<BoardSize> boardSizeBox(int width, BoardSize defaultValue){
        ComboBox<BoardSize> boardSizeBox = new ComboBox<>();
        boardSizeBox.getItems().addAll(BoardSize.values());
        boardSizeBox.getSelectionModel().select(defaultValue);
        return boardSizeBox;
    }

    /**
     * Checking how many tiles covered with selected donuts
     */
    public int areaRequiredForDonuts(int[] values){
        int multiplier = 2;
        int total = 0;

        for(int i = 0; i < values.length; i++){
            total += (i+1)*values[i];
        }
        return total*multiplier;
    }

    /**
     * If too many donuts selected for a game -> error window
     */
    public void errorMsg(String title, String errorMsg){
        Stage stage = new Stage();
        Label error = new Label(errorMsg);
        uiStyler.ralewayLabelStyler(error, 13);
        Button okButton = new Button("Ok");
        VBox vBox = new VBox();

        okButton.setOnAction(e -> {
            stage.close();
        });

        stage.setResizable(false);
        stage.setTitle(title);
        error.setTextAlignment(TextAlignment.CENTER);
        uiStyler.buttonStyler(okButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
        vBox.setSpacing(10);

        vBox.getChildren().addAll(error, okButton);
        stage.setScene(new Scene(vBox, 350, 130));
        stage.show();
    }

    /**
     * Returns donutSize that calculated by given width and height values
     * @param width
     * @param height
     * @return
     */
    public int getDonutSize(int width, int height){
        if(width > height)
            return width/height;
        else
            return height/width;
    }
}
