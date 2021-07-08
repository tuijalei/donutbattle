package org.game.donutbattle.UI;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Styling the game - Labels and Buttons
 * Diggerent .ttf/.otf-fonts, border styles, css-styles...
 */
public class UIStyler {

    /**
     * Initializing wanted border
     */
    public Border borderWithBS(Color color, BorderStrokeStyle style, int width){
        return new Border(new BorderStroke(color, style,null, new BorderWidths(width)));
    }

    public ImageView missImage(){
        ImageView missView = new ImageView(new Image("file:resources/UI/miss.png"));
        return missView;
    }

    public ImageView hitImage(){
        ImageView hitView = new ImageView(new Image("file:resources/UI/hit.png"));
        return hitView;
    }

    /**
     * Default font style for labels
     */
    public String getBasicFontStyle(){
        return "-fx-font-size: 12px; -fx-font-family: Verdana;";
    }

    /**
     * Binding button's styleProperty to hoverProperty and pressedProperty
     */
    public Button buttonStyler(Button button){
        button.styleProperty().bind(Bindings.when(button.hoverProperty()).
                then(this.mouseEnteredStyler()).
                otherwise(this.basicStringStyler()));

        return button;
    }

    /**
     * Default button style
     */
    public String basicStringStyler(){
        return "-fx-color: #FFCCCC; " +
                "-fx-border-radius:4px; " +
                "-fx-border-color: white; " +
                "-fx-font-size: 12px;" +
                "-fx-faint-focus-color: transparent;" +
                "-fx-focus-color: transparent;" +
                "-fx-font-family: Verdana;";
    }

    /**
     * Button style when mouse entered
     */
    private String mouseEnteredStyler(){
        return "-fx-color: #FFB2B2; " +
                "-fx-border-radius:4px;" +
                "-fx-border-color: white; " +
                "-fx-font-size: 12px;" +
                "-fx-faint-focus-color: transparent;" +
                "-fx-focus-color: transparent;" +
                "-fx-font-family: Verdana;";
    }

    /**
     * Button style when button pressed
     */
    private String buttonPressedButtonStyler(){
        return "-fx-color: #FF9999; " +
                "-fx-border-radius:4px;" +
                "-fx-border-color: white; " +
                "-fx-font-size: 12px;" +
                "-fx-font-family: Verdana;" +
                "-fx-position: relative;" +
                "-fx-faint-focus-color: transparent;" +
                "-fx-focus-color: transparent;" +
                "-fx-outline: white 5x";
    }

    /**
     * Styling Labels with Amatic-Bold font
     * Can modify the font size for labels too
     */
    public Label amaticLabelStyler(Label source, int fontSize){
        source.setFont(Font.loadFont("file:resources/fonts/Amatic-Bold.ttf", fontSize));
        return source;
    }

    /**
     * Styling Labels with Raleway-Regular font
     * Can modify the font size for labels too
     */
    public Label ralewayLabelStyler(Label source, int fontSize){
        source.setFont(Font.loadFont("file:resources/fonts/Raleway-Regular.ttf", fontSize));
        source.setPadding(new Insets(5));
        return source;
    }

    /**
     * Styling Labels with Raleway-Semibold font
     * Can modify the font size for labels too
     */
    public Label ralewayBLabelStyler(Label source, int fontSize){
        source.setFont(Font.loadFont("file:resources/fonts/Raleway-SemiBold.ttf", fontSize));
        source.setPadding(new Insets(5));
        return source;
    }

}
