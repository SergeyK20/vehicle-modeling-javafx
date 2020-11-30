package main.java.model;


import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Transport extends StackPane {

    private volatile long idNode;
    private volatile TranslateTransition animation;
    private boolean flagPause;
    private int randomImage;
    private Map<Integer, String> mapImage;
    private Rectangle rectangle;
    private volatile boolean isOvertaking;
    private Text text;

    {
        mapImage = new HashMap<Integer, String>();
        mapImage.put(1, "/image/auto1.jpg");
        mapImage.put(2, "/image/auto2.png");
        randomImage = (int) Math.round(new Random().nextDouble() + 1);
    }

    public Transport(long idNode, TranslateTransition animation) {
        rectangle = new Rectangle();
        text = new Text();
        ImagePattern ip = new ImagePattern(new Image(getClass().getResourceAsStream(mapImage.get(randomImage))));
        this.idNode = idNode;
        this.animation = animation;
        rectangle.setHeight(40);
        rectangle.setWidth(50);
        rectangle.setFill(ip);
        this.getChildren().addAll(rectangle, text);
        this.setAlignment(Pos.TOP_CENTER);
        animation.setNode(this);
        animation.setByX(10f);
        animation.setByY(10f);
        animation.setCycleCount(1);
        animation.setInterpolator(Interpolator.LINEAR);
        animation.play();
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
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
        setTextSpeed(animation.getRate());
    }

    public void setTextSpeed(double speed){
        text.setText(String.valueOf((Math.round(speed * 2000.0))));
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

    public boolean isOvertaking() {
        return isOvertaking;
    }

    public void setOvertaking(boolean overtaking) {
        isOvertaking = overtaking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transport transport = (Transport) o;

        if (idNode != transport.idNode) return false;
        if (flagPause != transport.flagPause) return false;
        if (randomImage != transport.randomImage) return false;
        if (isOvertaking != transport.isOvertaking) return false;
        if (!Objects.equals(animation, transport.animation)) return false;
        return Objects.equals(mapImage, transport.mapImage);
    }

    @Override
    public int hashCode() {
        int result = (int) (idNode ^ (idNode >>> 32));
        result = 31 * result + (animation != null ? animation.hashCode() : 0);
        result = 31 * result + (flagPause ? 1 : 0);
        result = 31 * result + randomImage;
        result = 31 * result + (mapImage != null ? mapImage.hashCode() : 0);
        result = 31 * result + (isOvertaking ? 1 : 0);
        return result;
    }
}
