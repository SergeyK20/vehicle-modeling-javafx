package main.java;


import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;


import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import main.java.model.Transport;
import main.java.view.StartScene;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        StartScene startScene = new StartScene(stage);
        startScene.start();
       /* Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.MAGENTA);
        rectangle.setWidth(100);
        rectangle.setHeight(50);
        Pane pane = new Pane();
        pane.getChildren().add(rectangle);
        pane.setMaxHeight(600);
        pane.setMaxWidth(800);


        Scene scene = new Scene(pane, 800, 600);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setFromY(300);
        translateTransition.setFromX(0);
        translateTransition.setToX(800);
        translateTransition.setCycleCount(1);
        translateTransition.setInterpolator(Interpolator.LINEAR);
        translateTransition.setNode(rectangle);
        translateTransition.setRate(0.025);
        translateTransition.play();

        rectangle.setOnMousePressed(mouseEvent -> {
            PathTransition pathTransition = new PathTransition();
            System.out.println(rectangle.getTranslateX());
            System.out.println(rectangle.getTranslateY());
            pathTransition.setPath(new Path(new MoveTo(rectangle.getTranslateX() + 20, rectangle.getTranslateY() + 20),
                    new LineTo(rectangle.getTranslateX() + 100, rectangle.getTranslateY() + 100)));
            pathTransition.setRate(0.025 * 8);
            rectangle.setRotate(45.0);
            pathTransition.setNode(rectangle);
            pathTransition.play();

            pathTransition.setOnFinished(actionEvent -> {
                rectangle.setRotate(0);
                TranslateTransition translateTransition1 = new TranslateTransition();
                translateTransition1.setFromY(rectangle.getTranslateY());
                translateTransition1.setFromX(rectangle.getTranslateX());
                translateTransition1.setToX(800);
                translateTransition1.setCycleCount(1);
                translateTransition1.setInterpolator(Interpolator.LINEAR);
                translateTransition1.setNode(rectangle);
                translateTransition1.setRate(0.025 * (800 / rectangle.getTranslateX()));
                translateTransition1.play();
                translateTransition.stop();
            });

        });
*/
    }


    @Override
    public void stop() {
        /*flagClose = true;
        es.shutdown();
        exStart.shutdown();*/
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

        /*System.out.println(new Random().nextDouble());
        double[] mas = new double[10];
        for (int i = 0; i < 100; i++) {*//*
            System.out.println(mas[i] = (new Random().nextGaussian()  + 3));
            System.out.println(mas[i] = (1 + new Random().nextDouble() * 8 ));
            System.out.println(mas[i] = Math.log(1 - new Random().nextDouble()) / -3);
            System.out.println(mas[i] = 1-1/Math.pow(2.71828, new Random().nextDouble() * 60));*//*
            //машин в минуту / минуту
            *//*System.out.println(mas[i] = -1 / (30 / 60.0) * Math.log(new Random().nextDouble()));
            mas[i] = mas[i] + 1;*//*
            System.out.println(mas[i] = -1 / (1 / (1000/ 50.0)) * Math.log(new Random().nextDouble()));
            mas[i] = Math.round(mas[i]) + 20;
            *//*TimeUnit.SECONDS.sleep(Math.round(mas[i]));*//*
        }
        Arrays.sort(mas);
        double raz = mas[99] - mas[0];
        double shag = raz / 8.0;
        int i = 0;
        double temp = mas[0];
        while (temp < mas[99]) {
            temp += shag;
            while (i < 100 && mas[i] <= temp) {
                System.out.print("*");
                i++;
            }
            System.out.println();
        }*/
    }
}
