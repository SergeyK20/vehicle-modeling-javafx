package main.java.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.controller.ControllerSettingsSimulation;
import main.java.model.BuilderRoad;
import main.java.model.Tunnel;

public class SettingsScene {

    private Stage stage;
    private Button startSimulation;
    private BuilderRoad road;

    public SettingsScene(Stage stage, BuilderRoad road) {
        this.stage = stage;
        this.road = road;
    }

    public void start() {
        Pane paneSettings = new Pane();
        paneSettings.maxHeight(600.0);
        paneSettings.maxWidth(800.0);
        startSimulation = new Button("Начать моелирование");
        startSimulation.setLayoutX(350);
        startSimulation.setLayoutY(500);

        Scene sceneSettings = new Scene(paneSettings, 800, 600);
        paneSettings.getChildren().addAll(startSimulation);
        ObservableList<String> list = FXCollections.observableArrayList("Детерменированное", "Нормальное", "Равномерное", "Экспоненциальное");
        ComboBox<String> comboBox = new ComboBox<>(list);
        Label label = new Label();
        label.setLayoutX(300);
        label.setLayoutY(50);
        comboBox.setLayoutX(300);
        comboBox.setLayoutY(100);
        comboBox.setValue("Детерменированное");
        if (road instanceof Tunnel) {
            discreteTunnel(paneSettings);
        } else {
            paneSettings.getChildren().addAll(label, comboBox);
            comboBox.setOnAction(event -> {
                switch (comboBox.getValue()) {
                    case "Детерменированное":
                        label.setText("Детерменированное");
                        break;
                    case "Нормальное":
                        label.setText("Нормальное");
                        break;
                    case "Равномерное":
                        label.setText("Равномерное");
                        break;
                    case "Экспоненциальное":
                        label.setText("Экспоненциальное");
                        break;
                }
            });

        }
        stage.centerOnScreen();
        stage.setScene(sceneSettings);
        stage.show();
    }

    private void discreteTunnel(Pane paneSettings) {
        try {
            VBox vBox = new VBox();
            vBox.setLayoutX(200);
            vBox.setLayoutY(300);
            Label label1 = new Label("Детерменированный поток");
            Label label = new Label("Установите время между появлением автомобилей (от 5 - 50 секунд)");
            TextField textField = new TextField();
            vBox.getChildren().addAll(label1, label, textField);
            paneSettings.getChildren().add(vBox);
            startSimulation.setOnMousePressed(event -> {
                try {
                    if (Integer.parseInt(textField.getText()) >= 5 && Integer.parseInt(textField.getText()) <= 50) {
                        road.setStreamTransport("discrete", Integer.parseInt(textField.getText()));
                        road.setSpeed("discrete", 60);
                        startSimulation.setOnAction(new ControllerSettingsSimulation(stage, road));
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Ошибка");
                        alert.setContentText("Ввели значения в неверном диапазоне");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Ошибка");
                    alert.setContentText("Неверное значение поля, пожалуйста введите его заново");
                    alert.showAndWait();
                }
            });
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void discreteHighway() {

    }
}
