package main.java;


import javafx.application.Application;

import java.io.*;
import java.net.SocketOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.stage.Stage;
import main.java.view.StartScene;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        StartScene startScene = new StartScene(stage);
        startScene.start();
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
        /*System.out.println(new Random().nextDouble());
        double[] mas = new double[100];
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
