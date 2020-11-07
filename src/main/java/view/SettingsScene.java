package main.java.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.java.controller.ControllerSettingsSimulation;
import main.java.model.BuilderRoad;
import main.java.model.Distribution;
import main.java.model.KindModeling;
import main.java.model.Tunnel;

import java.util.Objects;

public class SettingsScene {

    private Stage stage;
    private Button startSimulation;
    private BuilderRoad road;
    private StartScene startScene;
    private static final int LAYOUT_X_LEFT_PANE = 100;
    private static final int LAYOUT_X_RIGHT_PANE = 500;

    public SettingsScene(Stage stage, BuilderRoad road, StartScene startScene) {
        this.stage = stage;
        this.road = road;
        this.startScene = startScene;
    }

    public void start() {
        Pane paneSettings = new Pane();
        paneSettings.maxHeight(600.0);
        paneSettings.maxWidth(800.0);
        startSimulation = new Button("Начать моелирование");
        startSimulation.setLayoutX(400);
        startSimulation.setLayoutY(500);

        Button backBtn = new Button("Назад");
        backBtn.setLayoutX(300);
        backBtn.setLayoutY(500);
        backBtn.setOnAction(event -> {
            startScene.start();
        });

        ObservableList<String> listKindModeling =
                FXCollections.observableArrayList(
                        KindModeling.DETERMINISTIC.getNameKindModeling(),
                        KindModeling.ACCIDENTAL.getNameKindModeling()
                );
        ComboBox<String> comboBox = new ComboBox<>(listKindModeling);
        comboBox.setLayoutX(300);
        comboBox.setLayoutY(100);

        Label label = new Label();
        label.setLayoutX(200);
        label.setLayoutY(50);
        label.setFont(new Font("Arial", 30));

        paneSettings.getChildren().addAll(label, startSimulation, backBtn, comboBox);

        Scene sceneSettings = new Scene(paneSettings, 800, 600);

        if (road instanceof Tunnel) {
            label.setText("Настрока режима тоннель");
        } else {
            label.setText("Настрока режима автомагистраль");
        }


        if (road.getSpeed() == null || road.getSpeed().getNameDistributionLow().equals(Distribution.DETERMINISTIC.getNameDistribution())) {
            comboBox.setValue(KindModeling.DETERMINISTIC.getNameKindModeling());
            discreteTunnel(paneSettings);
        } else {
            comboBox.setValue(KindModeling.ACCIDENTAL.getNameKindModeling());
            Objects.requireNonNull(comboBox, "ComboBox еще не создан...");
            selectionOfKindDistribution(sceneSettings, comboBox, label, backBtn);
        }

        comboBox.setOnAction(event ->
                selectionOfKindDistribution(sceneSettings, comboBox, label, backBtn)
        );

        stage.centerOnScreen();
        stage.setScene(sceneSettings);
        stage.show();
    }

    private void discreteTunnel(Pane paneSettings) {
        try {
            VBox vBox = new VBox();
            vBox.setLayoutX(200);
            vBox.setLayoutY(300);
            vBox.setMinWidth(150);

            Label labelNameFlow = new Label("Детерменированный поток");
            Label labelAppointmentTime = new Label("Установите время между появлением автомобилей (от 5 - 50 секунд)");
            Label labelAppointmentSpeed = new Label("Скорость движения автомобилей (от 20-80 км/ч)");
            TextField textFieldForTime = new TextField();
            TextField textField2ForSpeed = new TextField();

            if (road.getSpeed() != null && road.getSpeed().getNameDistributionLow().equals(Distribution.DETERMINISTIC.getNameDistribution())) {
                textField2ForSpeed.setText(String.valueOf(Math.round(road.getSpeed().getSpeed())));
                textFieldForTime.setText(String.valueOf(Math.round(road.getStreamTransport().getTimes())));
            }

            vBox.getChildren().addAll(labelNameFlow, labelAppointmentTime, textFieldForTime, labelAppointmentSpeed, textField2ForSpeed);
            paneSettings.getChildren().add(vBox);

            startSimulation.setOnMousePressed(event -> {
                try {
                    if (Integer.parseInt(textFieldForTime.getText()) >= 5 && Integer.parseInt(textFieldForTime.getText()) <= 50) {
                        road.setStreamTransport(Distribution.DETERMINISTIC.getNameDistribution(), Integer.parseInt(textFieldForTime.getText()));
                        if (Integer.parseInt(textField2ForSpeed.getText()) >= 20 && Integer.parseInt(textFieldForTime.getText()) <= 80) {
                            road.setSpeed(Distribution.DETERMINISTIC.getNameDistribution(), Integer.parseInt(textField2ForSpeed.getText()));
                            startSimulation.setOnAction(new ControllerSettingsSimulation(stage, road, this));
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setHeaderText("Ошибка");
                            alert.setContentText("Ввели значения в неверном диапазоне");
                            alert.showAndWait();
                        }
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void norm(Pane paneSettings, String inscriptionOne, String inscriptionTwo, int layoutX, TextField textFieldOne, TextField textFieldTwo) {
        VBox normDistributionBox = new VBox();
        normDistributionBox.setLayoutX(layoutX);
        normDistributionBox.setLayoutY(300);

        textFieldOne.setMinSize(150, 25);
        textFieldTwo.setMinSize(150, 25);

        if(road.getSpeed() != null){
            if(layoutX == 100) {
                textFieldOne.setText(String.valueOf(road.getStreamTransport().getExpectedValue()));
                textFieldTwo.setText(String.valueOf(road.getStreamTransport().getDispersion()));
            } else {
                textFieldOne.setText(String.valueOf(road.getSpeed().getExpectedValue()));
                textFieldTwo.setText(String.valueOf(road.getSpeed().getDispersion()));
            }
        }

        Label label1 = new Label(inscriptionOne);
        Label label2 = new Label(inscriptionTwo);

        normDistributionBox.getChildren().addAll(label1, label2, textFieldOne, textFieldTwo);
        paneSettings.getChildren().add(normDistributionBox);
    }

    private void uniform(Pane paneSettings, String inscriptionOne, String inscriptionTwo, int layoutX, TextField textFieldOne, TextField textFieldTwo) {
        VBox uniformDistributionBox = new VBox();
        uniformDistributionBox.setLayoutX(layoutX);
        uniformDistributionBox.setLayoutY(300);

        textFieldOne.setMinSize(150, 25);
        textFieldTwo.setMinSize(150, 25);

        if(road.getSpeed() != null){
            if(layoutX == 100) {
                textFieldOne.setText(String.valueOf(road.getStreamTransport().getStartBoundary()));
                textFieldTwo.setText(String.valueOf(road.getStreamTransport().getEndBoundary()));
            } else {
                textFieldOne.setText(String.valueOf(road.getSpeed().getStartBoundary()));
                textFieldTwo.setText(String.valueOf(road.getSpeed().getEndBoundary()));
            }
        }

        Label label1 = new Label(inscriptionOne);
        Label label2 = new Label(inscriptionTwo);

        uniformDistributionBox.getChildren().addAll(label1, textFieldOne, label2, textFieldTwo);
        paneSettings.getChildren().add(uniformDistributionBox);
    }

    private void exponential(Pane paneSettings, String inscriptionOne, int layoutX, TextField textFieldOne) {
        VBox exponentialDistributionBox = new VBox();
        exponentialDistributionBox.setLayoutX(layoutX);
        exponentialDistributionBox.setLayoutY(300);

        textFieldOne.setMinSize(150, 25);

        if(road.getSpeed() != null){
            if(layoutX == 100) {
                textFieldOne.setText(String.valueOf(road.getStreamTransport().getIntensity()));
            } else {
                textFieldOne.setText(String.valueOf(road.getSpeed().getIntensity()));
            }
        }

        Label label1 = new Label(inscriptionOne);

        exponentialDistributionBox.getChildren().addAll(label1, textFieldOne);
        paneSettings.getChildren().add(exponentialDistributionBox);
    }

    private void selectionOfKindDistribution(Scene sceneSettings, ComboBox<String> comboBox, Label label, Button backBtn) {
        switch (Objects.requireNonNull(KindModeling.ACCIDENTAL.getEnumsKindModeling(comboBox.getValue()))) {
            case DETERMINISTIC:
                Pane paneDiscrete = new Pane();
                paneDiscrete.maxHeight(600.0);
                paneDiscrete.maxWidth(800.0);
                paneDiscrete.getChildren().addAll(label, startSimulation, backBtn, comboBox);
                sceneSettings.setRoot(paneDiscrete);
                discreteTunnel(paneDiscrete);
                break;
            case ACCIDENTAL:
                Pane paneAccidentalDistribution = new Pane();
                paneAccidentalDistribution.maxHeight(600.0);
                paneAccidentalDistribution.maxWidth(800.0);

                VBox paneForDistributionTime = new VBox();
                paneForDistributionTime.setLayoutX(100);
                paneForDistributionTime.setLayoutY(250);

                Label labelTittleTime = new Label("Параметры для времени");

                ObservableList<String> listAccidentalDistribution =
                        FXCollections.observableArrayList(
                                Distribution.NORM.getNameDistribution(),
                                Distribution.UNIFORM.getNameDistribution(),
                                Distribution.EXPONENTIAL.getNameDistribution()
                        );

                ComboBox<String> comboBoxAccidentalTime = new ComboBox<>(listAccidentalDistribution);


                paneForDistributionTime.getChildren().addAll(labelTittleTime, comboBoxAccidentalTime);

                VBox paneForDistributionSpeed = new VBox();
                paneForDistributionSpeed.setLayoutX(500);
                paneForDistributionSpeed.setLayoutY(250);
                paneForDistributionSpeed.setMinSize(300, 350);

                Label labelTittleSpeed = new Label("Параметры для скорости");

                ComboBox<String> comboBoxAccidentalSpeed = new ComboBox<>(listAccidentalDistribution);

                paneForDistributionSpeed.getChildren().addAll(labelTittleSpeed, comboBoxAccidentalSpeed);

                paneAccidentalDistribution.getChildren().addAll(label, startSimulation, backBtn, comboBox, paneForDistributionSpeed, paneForDistributionTime);

                TextField textFieldNormTimeMat = new TextField();
                TextField textFieldNormTimeDis = new TextField();
                TextField textFieldNormSpeedMat = new TextField();
                TextField textFieldNormSpeedDis = new TextField();
                TextField textFieldStartSpeed = new TextField();
                TextField textFieldEndSpeed = new TextField();
                TextField textFieldIntensitySpeed = new TextField();
                TextField textFieldStartTime = new TextField();
                TextField textFieldEndTime = new TextField();
                TextField textFieldIntensityTime = new TextField();

                //регистрация события при нажатие кнопки "Cтарт моделирования"
                startSimulation.setOnMousePressed(actionEvent -> {
                    try {
                        switch (Objects.requireNonNull(Distribution.EXPONENTIAL.getEnumsDistribution(comboBoxAccidentalSpeed.getValue()))) {
                            case NORM:
                                road.setSpeed(
                                        Distribution.NORM.getNameDistribution(),
                                        Integer.parseInt(textFieldNormSpeedMat.getText()),
                                        Integer.parseInt(textFieldNormSpeedDis.getText())
                                );
                                break;
                            case UNIFORM:
                                road.setSpeed(
                                        Distribution.UNIFORM.getNameDistribution(),
                                        Integer.parseInt(textFieldStartSpeed.getText()),
                                        Integer.parseInt(textFieldEndSpeed.getText())
                                );
                                break;
                            case EXPONENTIAL:
                                road.setSpeed(
                                        Distribution.EXPONENTIAL.getNameDistribution(),
                                        Integer.parseInt(textFieldIntensitySpeed.getText())
                                );
                                break;
                        }

                        switch (Objects.requireNonNull(Distribution.EXPONENTIAL.getEnumsDistribution(comboBoxAccidentalTime.getValue()))) {
                            case NORM:
                                road.setStreamTransport(Distribution.NORM.getNameDistribution(), Integer.parseInt(textFieldNormTimeMat.getText()), Integer.parseInt(textFieldNormTimeDis.getText()));
                                break;
                            case UNIFORM:
                                road.setStreamTransport(Distribution.UNIFORM.getNameDistribution(), Integer.parseInt(textFieldStartTime.getText()), Integer.parseInt(textFieldEndTime.getText()));
                                break;
                            case EXPONENTIAL:
                                road.setStreamTransport(Distribution.EXPONENTIAL.getNameDistribution(), Integer.parseInt(textFieldIntensityTime.getText()));
                                break;
                        }
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Ошибка");
                        alert.setContentText("Важные поля остались пустыми, пожалуйста заполните их");
                        alert.showAndWait();
                    }

                    startSimulation.setOnAction(new ControllerSettingsSimulation(stage, road, this));
                });

                if (road.getSpeed() != null) {
                    comboBoxAccidentalTime.setValue(road.getStreamTransport().getNameDistributionLow());
                    comboBoxAccidentalSpeed.setValue(road.getSpeed().getNameDistributionLow());

                    selectionOfDistribution(
                            comboBoxAccidentalTime,
                            paneAccidentalDistribution,
                            textFieldNormTimeMat,
                            textFieldNormTimeDis,
                            textFieldStartTime,
                            textFieldEndTime,
                            textFieldIntensityTime,
                            LAYOUT_X_LEFT_PANE,
                            LAYOUT_X_RIGHT_PANE,
                            "времени"
                    );

                    selectionOfDistribution(
                            comboBoxAccidentalSpeed,
                            paneAccidentalDistribution,
                            textFieldNormSpeedMat,
                            textFieldNormSpeedDis,
                            textFieldStartSpeed,
                            textFieldEndSpeed,
                            textFieldIntensitySpeed,
                            LAYOUT_X_RIGHT_PANE,
                            LAYOUT_X_LEFT_PANE,
                            "скорости"
                    );
                } else {
                    comboBoxAccidentalTime.setValue(Distribution.NORM.getNameDistribution());
                    comboBoxAccidentalSpeed.setValue(Distribution.NORM.getNameDistribution());
                    norm(
                            paneAccidentalDistribution,
                            "Мат. ожидание для времени",
                            "Дисперсия для времени",
                            LAYOUT_X_LEFT_PANE,
                            textFieldNormTimeMat,
                            textFieldNormTimeDis
                    );

                    norm(
                            paneAccidentalDistribution,
                            "Мат. ожидание для скорости",
                            "Дисперсия для скорости",
                            LAYOUT_X_RIGHT_PANE,
                            textFieldNormSpeedMat,
                            textFieldNormSpeedDis
                    );
                }


                comboBoxAccidentalTime.setOnAction(event1 -> {
                    selectionOfDistribution(
                            comboBoxAccidentalTime,
                            paneAccidentalDistribution,
                            textFieldNormTimeMat,
                            textFieldNormTimeDis,
                            textFieldStartTime,
                            textFieldEndTime,
                            textFieldIntensityTime,
                            LAYOUT_X_LEFT_PANE,
                            LAYOUT_X_RIGHT_PANE,
                            "времени"
                    );
                });

                comboBoxAccidentalSpeed.setOnAction(event2 -> {
                    selectionOfDistribution(
                            comboBoxAccidentalSpeed,
                            paneAccidentalDistribution,
                            textFieldNormSpeedMat,
                            textFieldNormSpeedDis,
                            textFieldStartSpeed,
                            textFieldEndSpeed,
                            textFieldIntensitySpeed,
                            LAYOUT_X_RIGHT_PANE,
                            LAYOUT_X_LEFT_PANE,
                            "скорости"
                    );
                });
                sceneSettings.setRoot(paneAccidentalDistribution);
                break;
        }
    }

    private void selectionOfDistribution(
            ComboBox<String> comboBox,
            Pane paneAccidentalDistribution,
            TextField textFieldNormTimeMat,
            TextField textFieldNormTimeDis,
            TextField textFieldStartTime,
            TextField textFieldEndTime,
            TextField textFieldIntensityTime,
            int ourLayoutX,
            int noOurLayoutX,
            String magnitude
    ) {
        switch (Objects.requireNonNull(
                Distribution.EXPONENTIAL.getEnumsDistribution(
                        comboBox.getValue())
        )) {
            case NORM:
                checkingAndDeletingChildPanel(paneAccidentalDistribution, noOurLayoutX);
                norm(
                        paneAccidentalDistribution,
                        "Мат. ожидание для " + magnitude,
                        "Дисперсия для " + magnitude,
                        ourLayoutX,
                        textFieldNormTimeMat,
                        textFieldNormTimeDis
                );
                break;
            case UNIFORM:
                checkingAndDeletingChildPanel(paneAccidentalDistribution, noOurLayoutX);
                uniform(
                        paneAccidentalDistribution,
                        "Минимальное значение Мат. ожидания для " + magnitude,
                        "Максимальное значение Мат. ожидания для " + magnitude,
                        ourLayoutX,
                        textFieldStartTime,
                        textFieldEndTime
                );
                break;
            case EXPONENTIAL:
                checkingAndDeletingChildPanel(paneAccidentalDistribution, noOurLayoutX);
                exponential(
                        paneAccidentalDistribution,
                        "Интенсивность для " + magnitude,
                        ourLayoutX,
                        textFieldIntensityTime
                );
                break;
        }
    }

    private void checkingAndDeletingChildPanel(Pane paneAccidentalDistribution, int noOurLayoutX){
        if(paneAccidentalDistribution.getChildren().size() > 7 ) {
            if (paneAccidentalDistribution.getChildren().get(7).getLayoutX() == noOurLayoutX) {
                paneAccidentalDistribution.getChildren().remove(6);
            } else {
                paneAccidentalDistribution.getChildren().remove(7);
            }
        }
    }
}
