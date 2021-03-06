package main.java.model;

import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс отвечающий за фон и количество дорог
 */
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
                                300,
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
                                n == 1 ? 400 : (n == 2 ? 500 : 600),
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
                    listFromY.add(125);
                    listFromY.add(195);
                    break;
                case 2:
                    listFromY.add(95);
                    listFromY.add(170);
                    listFromY.add(250);
                    listFromY.add(320);
                    break;
                case 3:
                    listFromY.add(35);
                    listFromY.add(120);
                    listFromY.add(210);
                    listFromY.add(300);
                    listFromY.add(390);
                    listFromY.add(480);
                    break;
                default:
            }
        } else {
            listFromY.add(120);
        }
        return listFromY;
    }

    public int getHeightY() {
        if (road instanceof Highway) {
            switch (countRoad) {
                case 1:
                    return 400;
                case 2:
                    return 500;
                case 3:
                    return 600;
                default:
                    return 0;
            }
        } else {
            return 300;
        }
    }

}
