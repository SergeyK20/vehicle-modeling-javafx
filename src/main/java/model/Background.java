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
    private int countRoad;
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
        this.countRoad = 1;
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
        this.countRoad = n;
        this.road = highway;
    }

    public javafx.scene.layout.Background getBackground() {
        return background;
    }

    public void setBackground(javafx.scene.layout.Background background) {

        this.background = background;
    }

    public int getCountRoad() {
        return countRoad;
    }

    public void setCountRoad(int countRoad) {
        this.countRoad = countRoad;
    }

    public List<Integer> getListFromY() {
        List<Integer> listFromY = new ArrayList<Integer>();
        if (road instanceof Highway) {
            switch (countRoad) {
                case 1:
                    listFromY.add(200);
                    listFromY.add(295);
                    break;
                case 2:
                    listFromY.add(120);
                    listFromY.add(210);
                    listFromY.add(295);
                    listFromY.add(375);
                    break;
                case 3:
                    listFromY.add(35);
                    listFromY.add(120);
                    listFromY.add(210);
                    listFromY.add(295);
                    listFromY.add(385);
                    listFromY.add(475);
                    break;
                default:
            }
        } else {
            listFromY.add(300);
        }
        return listFromY;
    }
}
