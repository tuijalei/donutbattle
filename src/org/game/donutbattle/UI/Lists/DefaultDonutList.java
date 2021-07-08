package org.game.donutbattle.UI.Lists;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import org.game.donutbattle.Pieces.*;
import org.game.donutbattle.UI.UIHelper;
import org.game.donutbattle.UI.UIStyler;

/**
 * Abstract class for donutList that is used at newGameDonutList and PlaceDonutList
 */
public abstract class DefaultDonutList {

    final UIHelper uiHelper;
    final UIStyler uiStyler;
    String labelText;

    final Donut[] donutList = new Donut[5];

    GridPane currentGridPane;

    public DefaultDonutList(UIHelper uiHelper, String labelText){
        this.uiHelper = uiHelper;
        this.labelText = labelText;
        this.uiStyler = new UIStyler();
        initDonuts();
    }

    /**
     * Initializing donuts for the sidelist
     */
    void initDonuts(){
        donutList[0] = new OneManDonut();
        donutList[1] = new TwoManDonut();
        donutList[2] = new ThreeManDonut();
        donutList[3] = new FourManDonut();
        donutList[4] = new FiveManDonut();
    }

    abstract void customizeList(GridPane gridPane);

    /**
     * Initialise and returns gridPane
     * @return
     */
    public GridPane getDonutListGridPane(){
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5));
        gridPane.setHgap(15);
        gridPane.setVgap(10);
        customizeList(gridPane);

        Label donutListLabel = new Label(this.labelText);
        uiStyler.ralewayLabelStyler(donutListLabel, 14);
        donutListLabel.prefWidth(100);
        donutListLabel.setTextAlignment(TextAlignment.CENTER);

        gridPane.add(donutListLabel, 0 , 0);
        GridPane.setColumnSpan(donutListLabel, 2);

        for(int i = 0; i < this.donutList.length; i++){
            gridPane.add(this.donutList[i].getImageView(), 1, (i+1));
            GridPane.setHalignment(this.donutList[i].getImageView(), HPos.CENTER);
        }

        this.currentGridPane = gridPane;
        return gridPane;
    }
}
