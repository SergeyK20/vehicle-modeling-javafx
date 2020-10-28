package main.java.model;

import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import java.util.ArrayList;
import java.util.List;

public class Background {

    private javafx.scene.layout.Background background;
    private int countLine;
    private BuilderRoad road;

    public Background(Tunnel tunnel) {
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
        this.road = tunnel;
    }

    //констуркор для фона автострады
    public Background(Highway highway, int n) {
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
        this.road = highway;
    }

    public javafx.scene.layout.Background getBackground() {
        return background;
    }

    public void setBackground(javafx.scene.layout.Background background) {

        this.background = background;
    }

    public int getCountLine() {
        return countLine;
    }

    public void setCountLine(int countLine) {
        this.countLine = countLine;
    }

    public List<Integer> getListFromY() {
        List<Integer> listFromY = new ArrayList<Integer>();
        if (road instanceof Highway) {
            switch (countLine) {
                case 1:
                    listFromY.add(230);
                    listFromY.add(325);
                    break;
                case 2:
                    listFromY.add(140);
                    listFromY.add(230);
                    listFromY.add(325);
                    listFromY.add(415);
                    break;
                case 3:
                    listFromY.add(50);
                    listFromY.add(140);
                    listFromY.add(230);
                    listFromY.add(325);
                    listFromY.add(415);
                    listFromY.add(515);
                    break;
                default:
            }
        } else {
            listFromY.add(300);
        }
        return listFromY;
    }
}
