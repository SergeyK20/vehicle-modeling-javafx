package main.java;


import javafx.application.Application;

import java.io.*;

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
    }
}
