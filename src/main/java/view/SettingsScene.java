package main.java.view;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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
        if (road instanceof Tunnel) {
            discrete(paneSettings);
        }
        stage.centerOnScreen();
        stage.setScene(sceneSettings);
        stage.show();
    }

    private void discrete(Pane paneSettings) {
        try {
            Label label1 = new Label("Детерменированный поток");
            label1.setLayoutX(300);
            label1.setLayoutY(200);
            Label label = new Label("Установите время между появлением автомобилей (от 5 - 50 секунд)");
            label.setLayoutY(250);
            label.setLayoutX(280);
            TextField textField = new TextField();
            textField.setLayoutY(400);
            textField.setLayoutX(300);
            paneSettings.getChildren().addAll(label, label1, textField);
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
}
