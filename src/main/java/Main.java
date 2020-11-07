package main.java;


import javafx.application.Application;

import java.io.*;
import java.net.SocketOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

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


    public static void main(String[] args) throws IOException {
        launch(args);
        /*System.out.println(new Random().nextDouble());
        double[] mas = new double[1000];
        for (int i = 0; i < 999; i++) {
            System.out.println(mas[i] = (new Random().nextGaussian() * 6.2 + 60) / 2000.0);
            System.out.println(mas[i] = (20 + new Random().nextDouble() * 50 )/ 2000.0);
            System.out.println(mas[i] = Math.log(1 - new Random().nextDouble()) / -15.0);
        }
        Arrays.sort(mas);
        double raz = mas[999] - mas[0];
        double shag = raz / 25.0;
        int i = 0;
        double temp = mas[0];
        while(temp < mas[999]){
            temp += shag;
            while(i < 1000 && mas[i] <= temp) {
                System.out.print("*");
                i++;
            }
            System.out.println();
        };*/
    }
}
