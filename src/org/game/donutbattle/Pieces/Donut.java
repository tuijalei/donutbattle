package org.game.donutbattle.Pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class is used to storage Image path, rotated Image path and ImageView for both path.
 */
public abstract class Donut {

    //String path that is used to get Image for ImageView
    final String imagePath;
    //String path that is used to get Image for ImageView (rotated Image)
    final String rotatedImagePath;

    ImageView imageView;
    ImageView rotatedImageView;

    ImageView currentView;

    Donut(int donutSize, String imagePath, String rotatedImagePath){
        this.imagePath = imagePath;
        this.rotatedImagePath = rotatedImagePath;
        initialize();
    }

    /**
     * Initialize Donut class.
     * imageView setup.
     */
    public void initialize(){
        setImageView();
    }

    public ImageView getImageView(){
        return this.currentView;
    }

    /**
     * Method to give imageView new ImageView using new Image with given path.
     */
    public void setImageView(){
        this.imageView = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
        this.rotatedImageView = new ImageView(new Image(getClass().getResource(rotatedImagePath).toExternalForm()));
        this.currentView = this.imageView;
    }

    public ImageView getDefaultImageView(){
        return this.imageView;
    }

    public ImageView getRotatedImageView(){
        return this.rotatedImageView;
    }

    /**
     * Sets currentView (that is used to display ImageView) from imageView to rotatedImageView or other way around
     */
    public boolean rotateImage(){
        this.currentView = this.currentView.equals(this.imageView) ? this.rotatedImageView : this.imageView;
        return this.currentView != this.imageView;
    }
}
