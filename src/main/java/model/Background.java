package main.java.model;

import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class Background {

    private javafx.scene.layout.Background background;
    private int countLine;

    public Background(Tunnel tunnel){
        this.background = new javafx.scene.layout.Background(new BackgroundImage
                (new Image("/image/123.png"),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(
                                800,
                                600,
                                false,
                                false,
                                false,
                                false)
                )
        );
        this.countLine = 1;
    }

    //констуркор для фона автострады
    public Background(Highway highway, int n){
        this.background = new javafx.scene.layout.Background(new BackgroundImage
                (new Image("/image/" + n + ".png"),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(
                                800,
                                600,
                                false,
                                false,
                                false,
                                false)
                )
        );
        this.countLine = n;
    }

    public javafx.scene.layout.Background getBackground() {
        return background;
    }

    public void setBackground(javafx.scene.layout.Background background) {
        this.background = background;
    }
}
