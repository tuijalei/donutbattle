package org.game.donutbattle.UI.Lists;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import org.game.donutbattle.DonutAmount;
import org.game.donutbattle.UI.UIHelper;
import org.game.donutbattle.UI.UIStyler;

/**
 * Advanced class from DefaultDonutList for PlaceDonutsList.
 */
public class NewGameDonutList extends DefaultDonutList{

    private int totalValue = 0;
    private UIStyler uiStyler = new UIStyler();
    private ComboBox<DonutAmount>   oneCombo = new ComboBox<>(),
                                    twoCombo = new ComboBox<>(),
                                    threeCombo = new ComboBox<>(),
                                    fourCombo = new ComboBox<>(),
                                    fiveCombo = new ComboBox<>();

    public NewGameDonutList(UIHelper uiHelper, String labelText) {
        super(uiHelper, labelText);
    }

    /**
     * Adds donuts and comboBox with default values to gridPane
     * @param gridPane
     */
    @Override
    void customizeList(GridPane gridPane) {
        oneCombo.getItems().addAll(DonutAmount.values());
        oneCombo.getSelectionModel().select(DonutAmount._1);

        twoCombo.getItems().addAll(DonutAmount.values());
        twoCombo.getSelectionModel().select(DonutAmount._0);

        threeCombo.getItems().addAll(DonutAmount.values());
        threeCombo.getSelectionModel().select(DonutAmount._0);

        fourCombo.getItems().addAll(DonutAmount.values());
        fourCombo.getSelectionModel().select(DonutAmount._0);

        fiveCombo.getItems().addAll(DonutAmount.values());
        fiveCombo.getSelectionModel().select(DonutAmount._0);

        gridPane.add(oneCombo, 0,1);
        gridPane.add(twoCombo, 0,2);
        gridPane.add(threeCombo, 0,3);
        gridPane.add(fourCombo, 0,4);
        gridPane.add(fiveCombo, 0,5);

        oneCombo.setStyle(uiStyler.basicStringStyler());
        twoCombo.setStyle(uiStyler.basicStringStyler());
        threeCombo.setStyle(uiStyler.basicStringStyler());
        fourCombo.setStyle(uiStyler.basicStringStyler());
        fiveCombo.setStyle(uiStyler.basicStringStyler());

    }

    /**
     * Get every comboBox setValue and returns it as integer array.
     * @return
     */
    public int[] getDonutsValuesAsIntArr(){
        int[] arr = new int[5];
            arr[0] = oneCombo.getValue().getAmount();
            arr[1] = twoCombo.getValue().getAmount();
            arr[2] = threeCombo.getValue().getAmount();
            arr[3] = fourCombo.getValue().getAmount();
            arr[4] = fiveCombo.getValue().getAmount();
        return arr;
    }
}
