package org.game.donutbattle.UI.Lists;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.TextAlignment;
import org.game.donutbattle.Pieces.*;
import org.game.donutbattle.UI.UIHelper;
import org.game.donutbattle.UI.UIStyler;

import java.util.ArrayList;

/**
 * Advanced class from DefaultDonutList for PlaceDonutsList.
 */
public class PlaceDonutsList extends DefaultDonutList {

    private Label[] labels;
    private UIStyler uiStyler;
    private final int[] originalValues;
    private int[] values;
    boolean rotated;

    public PlaceDonutsList(UIHelper uiHelper, String labelText, int[] values) {
        super(uiHelper, labelText);
        this.uiStyler = new UIStyler();
        this.originalValues = values;
        initValues(values);
        initializeLabels();
    }

    /**
     * Initialise values that is set for donuts.
     * @param values
     */
    void initValues(int[] values){
        this.values = new int[values.length];
        for(int i = 0; i < values.length; i++){
            this.values[i] = values[i];
        }
    }

    /**
     * Checks if all required donuts are placed
     * @return
     */
    public int donutsLeftToPlace(){
        int sum = 0;
        for(int value : this.values) {
            sum += value;
        }
        return sum;
    }

    /**
     * Initializes all donuts. Rotated and default views.
     */
    @Override
    void initDonuts(){
        donutList[0] = new OneManDonut();
        dragAndDrop(donutList[0].getDefaultImageView(), 0);
        dragAndDrop(donutList[0].getRotatedImageView(), 0);

        donutList[1] = new TwoManDonut();
        dragAndDrop(donutList[1].getDefaultImageView(), 1);
        dragAndDrop(donutList[1].getRotatedImageView(), 1);

        donutList[2] = new ThreeManDonut();
        dragAndDrop(donutList[2].getDefaultImageView(), 2);
        dragAndDrop(donutList[2].getRotatedImageView(), 2);

        donutList[3] = new FourManDonut();
        dragAndDrop(donutList[3].getDefaultImageView(), 3);
        dragAndDrop(donutList[3].getRotatedImageView(), 3);

        donutList[4] = new FiveManDonut();
        dragAndDrop(donutList[4].getDefaultImageView(), 4);
        dragAndDrop(donutList[4].getRotatedImageView(), 4);
    }

    /**
     * Makes sure that in game donutsLive has the correct version of donutsVies (rotated/default)
     */
    public void updateDonutsView(){
        ArrayList<ImageView> imageViews = new ArrayList<>();
        currentGridPane.getChildren().stream().filter(n -> n instanceof ImageView).forEach(n -> imageViews.add((ImageView) n));
        currentGridPane.getChildren().removeAll(imageViews);

        for(int i = 0; i < this.donutList.length; i++){
            this.rotated = this.donutList[i].rotateImage();
            int x = this.rotated ? i+1 : 1;
            currentGridPane.add(this.donutList[i].getImageView(), x, (i+1));
            GridPane.setHalignment(this.donutList[i].getImageView(), HPos.CENTER);
        }
    }

    /**
     * Drag and drop method for donuts ImageView.
     * @param donuts
     * @param i
     */
    void dragAndDrop(ImageView donuts, int i){
        donuts.setOnDragDetected(e -> {
            if(this.values[i] < 1)
                return;

            Dragboard dragboard = donuts.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();

            content.putImage(donuts.getImage());
            dragboard.setDragView(donuts.getImage(), 16, 16);
            dragboard.setContent(content);

            e.consume();
        });

        donuts.setOnDragDone(e -> {
            if(e.getTransferMode() == TransferMode.MOVE){
                this.values[i]--;
                updateLabelText(i);
                e.consume();
            }
        });
    }

    /**
     * Updates labelText (donut required amount)
     * @param labelIndex
     */
    public void updateLabelText(int labelIndex){
        this.labels[labelIndex].setText(Integer.toString(this.values[labelIndex]));
    }

    /**
     * Initialize labels for required donut amount label.
     */
    void initializeLabels(){
        this.labels = new Label[5];
        for(int i = 0; i < this.labels.length; i++){
            this.labels[i] = new Label();
            this.labels[i].setText(Integer.toString(this.values[i]));
            uiStyler.ralewayBLabelStyler(this.labels[i], 15);
        }
    }

    /**
     * Adds labels to gridPane
     * @param gridPane
     */
    @Override
    void customizeList(GridPane gridPane) {
        for(int i = 0; i < this.labels.length; i++){
            gridPane.add(this.labels[i], 0, (i+1));
        }
    }

    /**
     * Resets every label value in gridPane.
     */
    public void resetLabels(){
        for(int i = 0; i < this.originalValues.length; i++){
            this.values[i] = this.originalValues[i];
            this.labels[i].setText(Integer.toString(this.values[i]));
        }
    }

    /**
     * Makes and return donuts List (gridPane)
     * @return
     */
    @Override
    public GridPane getDonutListGridPane(){
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5));
        gridPane.setHgap(15);
        gridPane.setVgap(10);
        customizeList(gridPane);

        if(rotated){
            for(int i = 0; i < 6; i++){
                ColumnConstraints col = new ColumnConstraints(6);
                col.setHalignment(HPos.CENTER);
                col.setPrefWidth(30);
                gridPane.getColumnConstraints().add(col);
            }
        }

        for(int j = 0; j < 6; j++) {
            RowConstraints row = new RowConstraints(6);
            row.setValignment(VPos.CENTER);
            row.setPrefHeight(30);
            gridPane.getRowConstraints().add(row);
        }

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
