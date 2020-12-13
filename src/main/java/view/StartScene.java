package main.java.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.controller.ControllerStartScene;
import main.java.model.BuilderRoad;
import main.java.model.Highway;
import main.java.model.Tunnel;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Класс представления начальной сцены, с выбором типа дороги
 */
public class StartScene {

    private Stage stage;
    private BuilderRoad road;

    public StartScene(Stage stage) {
        this.stage = stage;
        stage.initStyle(StageStyle.UTILITY);
    }

    public void start() {
        Button infoInProgram = new Button();
        infoInProgram.setLayoutY(0);
        infoInProgram.setLayoutX(5);
        infoInProgram.setMinSize(30, 30);
        infoInProgram.setStyle("-fx-background-image: url(image/infoInProgram.jpg)");
        infoInProgram.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Информация о программе");
            alert.setContentText(
                    "Лабораторный практикум по дисциплине: \n" +
                            "       \" Технологии программирования \"\n" +
                            "Тема: " +
                            "\"Система моделирования движения транспорта\n" +
                            "                  в тоннеле и на автостраде\"\n" +
                            "\n" +
                            "Разработчики:\n" +
                            "   Студенты группы 6404-090301D:\n" +
                            "   Кашкинов Сергей \n" +
                            "   Балаев Рафаил\n" +
                            "Руководитель:\n" +
                            "   Зеленко Лариса Сергеевна\n" +
                            "               Самарский университет 2020");
            alert.showAndWait();
        });

        Button info = new Button();
        info.setLayoutY(0);
        info.setLayoutX(40);
        info.setMinSize(30, 30);
        info.setStyle("-fx-background-image: url(image/info.jpg)");
        info.setOnAction(event -> {
            Stage stageView = new Stage();
            Pane pane = new Pane();
            Scene scene = new Scene(pane, 800, 600);
            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            webEngine.load(Objects.requireNonNull(getClass().getClassLoader().getResource("info.html")).toString());
            pane.getChildren().addAll(webView);
            stageView.setScene(scene);
            stageView.centerOnScreen();
            stageView.showAndWait();

        });

        Label labelHello = new Label("Добро пожаловать!");
        labelHello.setLayoutY(70);
        labelHello.setLayoutX(100);
        labelHello.setFont(new Font("Aria", 30));

        Pane paneStart = new Pane();

        Button simulationSettings = new Button("Ок");
        simulationSettings.setLayoutX(170);
        simulationSettings.setLayoutY(245);
        simulationSettings.setMinSize(115, 25);

        ObservableList<String> listRoad = FXCollections.observableArrayList("Тоннель", "Автодорога");
        ComboBox<String> comboBox = new ComboBox<String>(listRoad);
        comboBox.setLayoutX(170);
        comboBox.setLayoutY(165);
        comboBox.setMinSize(100, 25);

        if (road == null || road instanceof Tunnel) {
            comboBox.setValue("Тоннель");
        } else {
            comboBox.setValue("Автодорога");
        }


        Scene sceneStart = new Scene(paneStart, 450, 300);

        paneStart.getChildren().addAll(simulationSettings, infoInProgram, info, comboBox, labelHello);


        simulationSettings.setOnMousePressed(event -> {
            if (comboBox.getValue().equals("Тоннель")) {
                road = new Tunnel();
            } else {
                road = new Highway();
            }
            simulationSettings.setOnAction(new ControllerStartScene(stage, road, this));
        });

        stage.setTitle("Моделирование движения автомобилей");


        stage.setX((Screen.getPrimary().getBounds().getMaxX() - 450.0) / 2.0);
        stage.setY((Screen.getPrimary().getBounds().getMaxY() - 300) / 2.0);
        stage.setResizable(false);
        stage.setScene(sceneStart);
        stage.show();

    }
}
