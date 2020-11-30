package main.java;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;

import java.io.*;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import main.java.model.Transport;
import main.java.view.StartScene;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        StartScene startScene = new StartScene(stage);
        startScene.start();


       /* Rectangle rectangle = new Rectangle();

        StackPane stackPane = new StackPane();
        TranslateTransition tt = new TranslateTransition();
        tt.setFromX(0);
        tt.setFromY(100);
        tt.setToX(1000);

        //установка скорости
        double speed = 80.0;

        tt.setRate(speed / 2000.0);


        ImagePattern ip = new ImagePattern(new Image(Main.class.getResourceAsStream("/image/auto1.jpg")));
        rectangle.setHeight(40);
        rectangle.setWidth(50);
        rectangle.setFill(ip);
        Text text = new Text(String.valueOf("123"));
        stackPane.getChildren().addAll(rectangle, text);
        stackPane.setAlignment(Pos.TOP_CENTER);

        tt.setNode(stackPane);
        tt.setByX(10f);
        tt.setByY(10f);
        tt.setCycleCount(1);
        tt.setInterpolator(Interpolator.LINEAR);
        tt.play();

        Scene scene = new Scene(stackPane, 800, 600);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
*/
    }


    @Override
    public void stop() {
        System.exit(0);
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        launch(args);
        /*ConcurrentMap<String, Transport> map2 = new ConcurrentHashMap<>();
        ConcurrentMap<String, Transport> map = new ConcurrentHashMap<>();
        Transport t1 = new Transport(1, new TranslateTransition());
        Transport t2 = new Transport(2, new TranslateTransition());
        Transport t3 = new Transport(3, new TranslateTransition());
        Transport t4 = new Transport(4, new TranslateTransition());
        Transport t5 = new Transport(5, new TranslateTransition());
        Transport t6 = new Transport(6, new TranslateTransition());

        map.put(String.valueOf(t1.getIdNode()), t1);
        map.put(String.valueOf(t2.getIdNode()), t2);
        map.put(String.valueOf(t3.getIdNode()), t3);
        map.put(String.valueOf(t4.getIdNode()), t4);
        map.put(String.valueOf(t5.getIdNode()), t5);

        int id = 3;
        t6.setIdNode(id);

        for(Transport transport: map.values()){
            System.out.print(transport.getIdNode() + " ");
        }

        System.out.println();

        //увеличиваем айди элементов в списке, которые будут ехать за нашей машиной
        for(Transport transport: map.values()){
            if(id <= transport.getIdNode()){
                transport.setIdNode(transport.getIdNode() + 1);
            }
        }

        for(Transport transport: map.values()){
            System.out.print(transport.getIdNode() + " ");
        }

        System.out.println();

        for(Transport transport: map.values()){
                map2.put(String.valueOf(transport.getIdNode()), transport);

        }

        map2.put(String.valueOf(t6.getIdNode()), t6);
        map.values().clear();
        map.putAll(map2);


        for(Map.Entry<String, Transport> element: map.entrySet()){
            System.out.print(element.getValue().getIdNode() + " ");
            System.out.print(element.getKey() + " ");
        }*/


    }
}
