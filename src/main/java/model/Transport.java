package main.java.model;


import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Transport extends Rectangle {

    private long idNode;
    private TranslateTransition animation;
    private boolean flagPause;
    private int randomImage;
    private Map<Integer, String> mapImage;

    {
        mapImage = new HashMap<Integer, String>();
        mapImage.put(1, "/image/auto1.jpg");
        mapImage.put(2, "/image/auto2.png");
        randomImage = (int) Math.round(new Random().nextDouble() + 1);
    }

    public Transport(long idNode, TranslateTransition animation) {
        ImagePattern ip = new ImagePattern(new Image(getClass().getResourceAsStream(mapImage.get(randomImage))));
        this.idNode = idNode;
        this.animation = animation;
        this.setHeight(40);
        this.setWidth(50);
        this.setFill(ip);
        animation.setNode(this);
        animation.setByX(10f);
        animation.setByY(10f);
        animation.setCycleCount(1);
        //animation.setRate(0.025);
        animation.setInterpolator(Interpolator.LINEAR);
        animation.play();
    }

    public long getIdNode() {
        return idNode;
    }

    public void setIdNode(long idNode) {
        this.idNode = idNode;
    }

    public TranslateTransition getAnimation() {
        return animation;
    }

    public void setAnimation(TranslateTransition animation) {
        this.animation = animation;
    }

    public void setSpeed(int speed) {
        animation.setRate(speed / 2000.0);
    }

    public int getSpeed() {
        return (int) animation.getRate() * 2000;
    }

    public boolean isFlagPause() {
        return flagPause;
    }

    public void setFlagPause(boolean flagPause) {
        this.flagPause = flagPause;
    }
}
