package main.java.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Style;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.java.controller.ControllerSettingsSimulation;
import main.java.model.*;

import java.util.Objects;

public class SettingsScene {

    private Stage stage;
    private Button startSimulation;
    private BuilderRoad road;
    private StartScene startScene;
    private Spinner<Integer> countRoad;
    private static final int LAYOUT_X_LEFT_PANE = 100;
    private static final int LAYOUT_X_RIGHT_PANE = 320;

    public SettingsScene(Stage stage, BuilderRoad road, StartScene startScene) {
        this.stage = stage;
        this.road = road;
        this.startScene = startScene;
    }

    public void start() {
        Pane paneSettings = new Pane();
        paneSettings.maxHeight(400.0);
        paneSettings.maxWidth(600.0);
        startSimulation = new Button("Начать моделирование");
        startSimulation.setLayoutX(300);
        startSimulation.setLayoutY(325);
        startSimulation.setMaxSize(150, 50);
        startSimulation.setMinSize(150, 50);
        startSimulation.setFocusTraversable(false);

        Button backBtn = new Button("⟵");
        backBtn.setLayoutX(150);
        backBtn.setLayoutY(325);
        backBtn.setStyle("-fx-font-size: 20px");
        backBtn.setFocusTraversable(false);
        backBtn.setMinSize(100, 50);
        backBtn.setOnAction(event -> {
            startScene.start();
        });

        ObservableList<String> listKindModeling =
                FXCollections.observableArrayList(
                        KindModeling.DETERMINISTIC.getNameKindModeling(),
                        KindModeling.ACCIDENTAL.getNameKindModeling()
                );
        ComboBox<String> comboBoxListModeling = new ComboBox<>(listKindModeling);
        comboBoxListModeling.setLayoutX(225);
        comboBoxListModeling.setLayoutY(75);
        comboBoxListModeling.setMaxSize(150, 25);

        Label label = new Label();

        label.setFont(new Font("Arial", 30));

        if (road instanceof Tunnel) {
            label.setText("Настрока режима тоннель");
            label.setLayoutX(125);
            label.setLayoutY(25);
        } else {
            label.setText("Настрока режима автомагистраль");
            label.setLayoutX(100);
            label.setLayoutY(25);

            countRoad = new Spinner<>();
            SpinnerValueFactory<Integer> spinnerValue;
            try {
                Objects.requireNonNull(road.getCountRoadBackground());
                spinnerValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, road.getCountRoadBackground().getCountRoad());
            } catch (NullPointerException e) {
                spinnerValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 1);
            }
            countRoad.setLayoutX(225);
            countRoad.setLayoutY(110);
            countRoad.setValueFactory(spinnerValue);
            paneSettings.getChildren().add(countRoad);
        }

        paneSettings.getChildren().addAll(label, startSimulation, backBtn, comboBoxListModeling);

        Scene sceneSettings = new Scene(paneSettings, 600, 400);


        if (road.getSpeed() == null || road.getSpeed().getNameDistributionLow().equals(Distribution.DETERMINISTIC.getNameDistribution())) {
            comboBoxListModeling.setValue(KindModeling.DETERMINISTIC.getNameKindModeling());
            discreteTunnel(paneSettings);
        } else {
            comboBoxListModeling.setValue(KindModeling.ACCIDENTAL.getNameKindModeling());
            Objects.requireNonNull(comboBoxListModeling, "ComboBox еще не создан...");
            selectionOfKindDistribution(sceneSettings, comboBoxListModeling, label, backBtn);
        }

        comboBoxListModeling.setOnAction(event ->
                selectionOfKindDistribution(sceneSettings, comboBoxListModeling, label, backBtn)
        );

        stage.setX((Screen.getPrimary().getBounds().getMaxX() - 600.0) / 2.0);
        stage.setY((Screen.getPrimary().getBounds().getMaxY() - 400) / 2.0);
        stage.setScene(sceneSettings);
        stage.show();
    }

    private void discreteTunnel(Pane paneSettings) {
        try {
            VBox vBox = new VBox();
            vBox.setLayoutX(125);
            vBox.setLayoutY(200);
            vBox.setMinWidth(150);

            Label labelAppointmentTime = new Label("Установите время между появлением автомобилей (от 5 - 50 секунд)");
            Label labelAppointmentSpeed = new Label("Скорость движения автомобилей (от 20-80 км/ч)");
            TextField textFieldForTime = new TextField();
            TextField textField2ForSpeed = new TextField();

            if (road.getSpeed() != null && road.getSpeed().getNameDistributionLow().equals(Distribution.DETERMINISTIC.getNameDistribution())) {
                textField2ForSpeed.setText(String.valueOf(Math.round(road.getSpeed().getSpeed())));
                textFieldForTime.setText(String.valueOf(Math.round(road.getStreamTransport().getTimes())));
            }

            vBox.getChildren().addAll(labelAppointmentTime, textFieldForTime, labelAppointmentSpeed, textField2ForSpeed);
            paneSettings.getChildren().add(vBox);

            startSimulation.setOnMousePressed(event -> {
                try {
                    if (Integer.parseInt(textFieldForTime.getText()) >= 5 && Integer.parseInt(textFieldForTime.getText()) <= 50) {
                        road.setStreamTransport(Distribution.DETERMINISTIC.getNameDistribution(), Integer.parseInt(textFieldForTime.getText()));
                        if (Integer.parseInt(textField2ForSpeed.getText()) >= 20 && Integer.parseInt(textFieldForTime.getText()) <= 80) {
                            road.setSpeed(Distribution.DETERMINISTIC.getNameDistribution(), Integer.parseInt(textField2ForSpeed.getText()));
                            if (road instanceof Tunnel) {
                                road.setCountRoadBackground(1);
                            } else {
                                road.setCountRoadBackground(countRoad.getValue());
                            }
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

    private void norm(Pane paneSettings, String inscriptionOne, String inscriptionTwo, int layoutX, Spinner<Integer> spinnerOne, Spinner<Integer> spinnerTwo) {
        VBox normDistributionBox = new VBox();
        normDistributionBox.setLayoutX(layoutX);
        normDistributionBox.setLayoutY(300);

        spinnerOne.setMaxSize(150, 25);
        spinnerTwo.setMaxSize(150, 25);

        if (layoutX == 100) {
            try {
                Objects.requireNonNull(road.getCountRoadBackground());
                spinnerOne.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 12, road.getStreamTransport().getExpectedValue()));
                spinnerTwo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 6, road.getStreamTransport().getDispersion()));
            } catch (NullPointerException e) {
                spinnerOne.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 12, 4));
                spinnerTwo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 6, 2));
            }
        } else{
            try {
                Objects.requireNonNull(road.getCountRoadBackground());
                spinnerOne.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 70, road.getSpeed().getExpectedValue()));
                spinnerTwo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 35, road.getSpeed().getDispersion()));
            } catch (NullPointerException e) {
                spinnerOne.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 70, 4));
                spinnerTwo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 35, 2));
            }
        }

        Label label1 = new Label(inscriptionOne);
        Label label2 = new Label(inscriptionTwo);

        normDistributionBox.getChildren().addAll(label1, spinnerOne, label2, spinnerTwo);
        paneSettings.getChildren().add(normDistributionBox);
    }

    private void uniform(Pane paneSettings, String inscriptionOne, String inscriptionTwo, int layoutX, Spinner<Integer>  spinnerOne, Spinner<Integer>  spinnerTwo) {
        VBox uniformDistributionBox = new VBox();
        uniformDistributionBox.setLayoutX(layoutX);
        uniformDistributionBox.setLayoutY(300);

        spinnerOne.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 12));
        spinnerOne.setMaxSize(150, 25);
        spinnerTwo.setMaxSize(150, 25);



        if (layoutX == 100) {
            try {
                Objects.requireNonNull(road.getCountRoadBackground());
                spinnerOne.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15, road.getStreamTransport().getStartBoundary()));
                spinnerTwo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15, road.getStreamTransport().getEndBoundary()));
            } catch (NullPointerException e) {
                spinnerOne.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15, 1));
                spinnerTwo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15, 15));
            }
        } else{
            try {
                Objects.requireNonNull(road.getCountRoadBackground());
                spinnerOne.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 100, road.getSpeed().getStartBoundary()));
                spinnerTwo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 100, road.getSpeed().getEndBoundary()));
            } catch (NullPointerException e) {
                spinnerOne.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 100, 20));
                spinnerTwo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 100, 100));
            }
        }

        Label label1 = new Label(inscriptionOne);
        Label label2 = new Label(inscriptionTwo);

        uniformDistributionBox.getChildren().addAll(label1, spinnerOne, label2, spinnerTwo);
        paneSettings.getChildren().add(uniformDistributionBox);
    }

    private void exponential(Pane paneSettings, String inscriptionOne, int layoutX, Spinner<Integer>  spinnerOne) {
        VBox exponentialDistributionBox = new VBox();
        exponentialDistributionBox.setLayoutX(layoutX);
        exponentialDistributionBox.setLayoutY(300);

        spinnerOne.setMaxSize(150, 25);

        if (layoutX == 100) {
            try {
                Objects.requireNonNull(road.getCountRoadBackground());
                spinnerOne.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(30, 120, road.getSpeed().getStartBoundary()));
            } catch (NullPointerException e) {
                spinnerOne.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(30, 120, 30));
            }
        } else {
            try {
                Objects.requireNonNull(road.getCountRoadBackground());
                spinnerOne.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(500, 1500, road.getSpeed().getStartBoundary()));
            } catch (NullPointerException e) {
                spinnerOne.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(500, 1500, 500));
            }
        }

        Label label1 = new Label(inscriptionOne);

        exponentialDistributionBox.getChildren().addAll(label1, spinnerOne);
        paneSettings.getChildren().add(exponentialDistributionBox);
    }

    private void selectionOfKindDistribution(Scene sceneSettings, ComboBox<String> comboBox, Label heading, Button backBtn) {
        switch (Objects.requireNonNull(KindModeling.ACCIDENTAL.getEnumsKindModeling(comboBox.getValue()))) {
            case DETERMINISTIC:
                Pane paneDiscrete = new Pane();
                paneDiscrete.maxHeight(400.0);
                paneDiscrete.maxWidth(600.0);
                if (road instanceof Highway) {
                    paneDiscrete.getChildren().add(countRoad);
                }
                paneDiscrete.getChildren().addAll(heading, startSimulation, backBtn, comboBox);
                sceneSettings.setRoot(paneDiscrete);
                discreteTunnel(paneDiscrete);
                break;
            case ACCIDENTAL:
                Pane paneAccidentalDistribution = new Pane();
                paneAccidentalDistribution.maxHeight(400.0);
                paneAccidentalDistribution.maxWidth(600.0);

                VBox paneForDistributionTime = new VBox();
                paneForDistributionTime.setLayoutX(100);
                paneForDistributionTime.setLayoutY(150);
                paneForDistributionTime.setStyle("-fx-border-style: solid; -fx-border-width: 1px; -fx-border-color: black;" +
                        "-fx-padding: 10px");


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
                paneForDistributionSpeed.setLayoutX(320);
                paneForDistributionSpeed.setLayoutY(150);
                paneForDistributionSpeed.setStyle("-fx-border-style: solid; -fx-border-width: 1px; -fx-border-color: black;" +
                        "-fx-padding: 10px");

                Label labelTittleSpeed = new Label("Параметры для скорости");

                ComboBox<String> comboBoxAccidentalSpeed = new ComboBox<>(listAccidentalDistribution);

                paneForDistributionSpeed.getChildren().addAll(labelTittleSpeed, comboBoxAccidentalSpeed);

                if (road instanceof Highway) {
                    paneAccidentalDistribution.getChildren().add(countRoad);
                }

                paneAccidentalDistribution.getChildren().addAll(heading, startSimulation, backBtn, comboBox, paneForDistributionSpeed, paneForDistributionTime);

                Spinner<Integer> spinnerNormTimeMat = new Spinner<>();
                Spinner<Integer> spinnerNormTimeDis = new Spinner<>();
                Spinner<Integer> spinnerNormSpeedMat = new Spinner<>();
                Spinner<Integer> spinnerNormSpeedDis = new Spinner<>();
                Spinner<Integer> spinnerStartSpeed = new Spinner<>();
                Spinner<Integer> spinnerEndSpeed = new Spinner<>();
                Spinner<Integer> spinnerIntensitySpeed = new Spinner<>();
                Spinner<Integer> spinnerStartTime = new Spinner<>();
                Spinner<Integer> spinnerEndTime = new Spinner<>();
                Spinner<Integer> spinnerIntensityTime = new Spinner<>();

                //регистрация события при нажатие кнопки "Cтарт моделирования"
                startSimulation.setOnMousePressed(actionEvent -> {
                    try {
                        switch (Objects.requireNonNull(Distribution.EXPONENTIAL.getEnumsDistribution(comboBoxAccidentalSpeed.getValue()))) {
                            case NORM:
                                road.setSpeed(
                                        Distribution.NORM.getNameDistribution(),
                                        spinnerNormSpeedMat.getValue(),
                                        spinnerNormSpeedDis.getValue());
                                break;
                            case UNIFORM:
                                road.setSpeed(
                                        Distribution.UNIFORM.getNameDistribution(),
                                        spinnerStartSpeed.getValue(),
                                        spinnerEndSpeed.getValue()
                                );
                                break;
                            case EXPONENTIAL:
                                road.setSpeed(
                                        Distribution.EXPONENTIAL.getNameDistribution(),
                                        spinnerIntensitySpeed.getValue()
                                );
                                break;
                        }

                        switch (Objects.requireNonNull(Distribution.EXPONENTIAL.getEnumsDistribution(comboBoxAccidentalTime.getValue()))) {
                            case NORM:
                                road.setStreamTransport(
                                        Distribution.NORM.getNameDistribution(),
                                        spinnerNormTimeMat.getValue(),
                                        spinnerNormTimeDis.getValue()
                                );
                                break;
                            case UNIFORM:
                                road.setStreamTransport(Distribution.UNIFORM.getNameDistribution(),
                                        spinnerStartTime.getValue(),
                                        spinnerEndTime.getValue()
                                );
                                break;
                            case EXPONENTIAL:
                                road.setStreamTransport(Distribution.EXPONENTIAL.getNameDistribution(),
                                        spinnerIntensityTime.getValue()
                                );
                                break;
                        }

                        if (road instanceof Tunnel) {
                            road.setCountRoadBackground(1);
                        } else {
                            road.setCountRoadBackground(countRoad.getValue());
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

                if (road.getSpeed() != null && !road.getSpeed().getNameDistributionLow().equals(Distribution.DETERMINISTIC.getNameDistribution())) {
                    comboBoxAccidentalTime.setValue(road.getStreamTransport().getNameDistributionLow());
                    comboBoxAccidentalSpeed.setValue(road.getSpeed().getNameDistributionLow());

                    selectionOfDistribution(
                            comboBoxAccidentalTime,
                            paneForDistributionTime,
                            spinnerNormTimeMat,
                            spinnerNormTimeDis,
                            spinnerStartTime,
                            spinnerEndTime,
                            spinnerIntensityTime,
                            LAYOUT_X_LEFT_PANE,
                            LAYOUT_X_RIGHT_PANE,
                            "времени"
                    );

                    selectionOfDistribution(
                            comboBoxAccidentalSpeed,
                            paneForDistributionSpeed,
                            spinnerNormSpeedMat,
                            spinnerNormSpeedDis,
                            spinnerStartSpeed,
                            spinnerEndSpeed,
                            spinnerIntensitySpeed,
                            LAYOUT_X_RIGHT_PANE,
                            LAYOUT_X_LEFT_PANE,
                            "скорости"
                    );
                } else {
                    comboBoxAccidentalTime.setValue(Distribution.NORM.getNameDistribution());
                    comboBoxAccidentalSpeed.setValue(Distribution.NORM.getNameDistribution());
                    norm(
                            paneForDistributionTime,
                            "Мат. ожидание для времени",
                            "Дисперсия для времени",
                            LAYOUT_X_LEFT_PANE,
                            spinnerNormTimeMat,
                            spinnerNormTimeDis
                    );

                    norm(
                            paneForDistributionSpeed,
                            "Мат. ожидание для скорости",
                            "Дисперсия для скорости",
                            LAYOUT_X_RIGHT_PANE,
                            spinnerNormSpeedMat,
                            spinnerNormSpeedDis
                    );
                }


                comboBoxAccidentalTime.setOnAction(event1 -> {
                    selectionOfDistribution(
                            comboBoxAccidentalTime,
                            paneForDistributionTime,
                            spinnerNormTimeMat,
                            spinnerNormTimeDis,
                            spinnerStartTime,
                            spinnerEndTime,
                            spinnerIntensityTime,
                            LAYOUT_X_LEFT_PANE,
                            LAYOUT_X_RIGHT_PANE,
                            "времени"
                    );
                });

                comboBoxAccidentalSpeed.setOnAction(event2 -> {
                    selectionOfDistribution(
                            comboBoxAccidentalSpeed,
                            paneForDistributionSpeed,
                            spinnerNormSpeedMat,
                            spinnerNormSpeedDis,
                            spinnerStartSpeed,
                            spinnerEndSpeed,
                            spinnerIntensitySpeed,
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
            Spinner<Integer> spinnerNormMat,
            Spinner<Integer> spinnerNormDis,
            Spinner<Integer> spinnerStart,
            Spinner<Integer> spinnerEnd,
            Spinner<Integer> spinnerIntensity,
            int ourLayoutX,
            int noOurLayoutX,
            String magnitude
    ) {
        switch (Objects.requireNonNull(
                Distribution.EXPONENTIAL.getEnumsDistribution(
                        comboBox.getValue())
        )) {
            case NORM:
                checkingAndDeletingChildPanel(paneAccidentalDistribution, noOurLayoutX, ourLayoutX);
                norm(
                        paneAccidentalDistribution,
                        "Мат. ожидание для " + magnitude,
                        "Дисперсия для " + magnitude,
                        ourLayoutX,
                        spinnerNormMat,
                        spinnerNormDis
                );
                break;
            case UNIFORM:
                checkingAndDeletingChildPanel(paneAccidentalDistribution, noOurLayoutX, ourLayoutX);
                uniform(
                        paneAccidentalDistribution,
                        "Минимальное значение " + magnitude,
                        "Максимальное значение " + magnitude,
                        ourLayoutX,
                        spinnerStart,
                        spinnerEnd
                );
                break;
            case EXPONENTIAL:
                checkingAndDeletingChildPanel(paneAccidentalDistribution, noOurLayoutX, ourLayoutX);
                exponential(
                        paneAccidentalDistribution,
                        ourLayoutX == 100 ? "Количество автомобилей в минуту \n " :
                                "Среднее расстояние проходимое машинами\n" +
                                        "за одинаковый промежуток времени",
                        ourLayoutX,
                        spinnerIntensity
                );
                break;
        }
    }

    private void checkingAndDeletingChildPanel(Pane paneAccidentalDistribution, int noOurLayoutX, int ourLayoutX) {
        if (paneAccidentalDistribution.getChildren().size() > 2) {
            paneAccidentalDistribution.getChildren().remove(2);
        }
    }
}
