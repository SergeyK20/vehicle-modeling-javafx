package main.java.view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.java.controller.*;
import main.java.model.BuilderRoad;
import main.java.model.ControlOneRoad;
import main.java.model.Transport;
import main.java.model.Tunnel;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * Представление на котором генерируется моделируемая система, по настройкам, которые были указанны раньше
 */
public class GenericSceneTunnel {

    private Stage stage;
    private List<ControlOneRoad> controlOneRoadList;
    private Button btnChangeSpeed;
    private TextField speedKmCh;
    private Transport transportChangeSpeed;
    private BuilderRoad road;
    private ExecutorService executorService;
    private SettingsScene settingsScene;
    //списки машин да на дорогах
    private List<CopyOnWriteArrayList<Transport>> listAutoInRoadsFromLeftToRight;
    private List<CopyOnWriteArrayList<Transport>> listAutoInRoadsFromRightToLeft;
    //списки машин да на дорогах
    private List<ControlOneRoad> listControlRoadsFromLeftToRight;
    private List<ControlOneRoad> listControlRoadsFromRightToLeft;
    private boolean flagPause;
    private boolean flagClose;


    public GenericSceneTunnel(Stage stage, BuilderRoad road, SettingsScene settingsScene) {
        this.stage = stage;
        this.road = road;
        this.settingsScene = settingsScene;
        listAutoInRoadsFromLeftToRight = new ArrayList<>();
        listAutoInRoadsFromRightToLeft = new ArrayList<>();
        listControlRoadsFromLeftToRight = new ArrayList<>();
        listControlRoadsFromRightToLeft = new ArrayList<>();
        flagPause = true;
    }

    public void start() {
        //пеердается с объектом моделирования
        Background bk = road.getCountRoadBackground().getBackground();

        //панель моделирования
        Pane paneModelingAuto = new Pane();
        paneModelingAuto.setBackground(bk);

        HBox hBox = new HBox();
        timer(hBox);


        //если туннель, то плявляется доболнительный функционал по изменениию скорости
        if (road instanceof Tunnel) {
            VBox vBoxUpdateSpeed = new VBox();
            vBoxUpdateSpeed.setSpacing(25);
            btnChangeSpeed = new Button();
            vBoxUpdateSpeed.setLayoutY(road.getCountRoadBackground().getHeightY() - 100);
            vBoxUpdateSpeed.setLayoutX(50);
            btnChangeSpeed.setText("Изменить скорость");
            speedKmCh = new TextField();
            vBoxUpdateSpeed.getChildren().addAll(speedKmCh, btnChangeSpeed);
            vBoxUpdateSpeed.setStyle("-fx-border-style: solid; -fx-border-width: 1px; -fx-border-color: black;" +
                    "-fx-padding: 10px");
            paneModelingAuto.getChildren().add(vBoxUpdateSpeed);

            //при каждом новом нажатии регистрируется метод изменения скорости
            btnChangeSpeed.setOnMousePressed(mouseEvent -> {
                try {
                    if (Integer.parseInt(speedKmCh.getText()) >= 0 && Integer.parseInt(speedKmCh.getText()) <= 80) {
                        btnChangeSpeed.setOnAction(new ControllerChangeSpeed(speedKmCh.getText(), transportChangeSpeed));
                        transportChangeSpeed.setTextSpeed(Double.parseDouble(speedKmCh.getText()) / 2000.0);
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
        } else {

        }

        //три кнопки внизу
        Button pauseAndProceed = new Button();
        pauseAndProceed.setLayoutY(road.getCountRoadBackground().getHeightY() - 25);
        pauseAndProceed.setLayoutX(345);
        pauseAndProceed.setStyle("-fx-background-image: url(image/activePlay.jpg) ");
        pauseAndProceed.setMinSize(25, 25);

        Button stop = new Button();
        stop.setLayoutY(road.getCountRoadBackground().getHeightY() - 25);
        stop.setLayoutX(405);
        stop.setStyle("-fx-background-image: url(image/activeStop.jpg) ");
        stop.setMinSize(25, 25);

        Label label = new Label();
        label.setFont(new Font("Arial", 20));
        label.setLayoutY(road.getCountRoadBackground().getHeightY() - 50);
        label.setLayoutX(250);

        if (transportChangeSpeed != null) {
            label.setText(transportChangeSpeed.getIdNode() + " " + transportChangeSpeed.getAnimation().getRate() * 2000.0);
        } else {
            label.setText("");
        }

        paneModelingAuto.getChildren().addAll(hBox, pauseAndProceed, stop, /*proceed,*/ label);


        //сцена на которой все располагается
        Scene scene = new Scene(paneModelingAuto, 800, road.getCountRoadBackground().getHeightY());
        stage.setX((Screen.getPrimary().getBounds().getMaxX() - 800.0) / 2.0);
        stage.setY((Screen.getPrimary().getBounds().getMaxY() - 600.0) / 2.0);
        stage.setScene(scene);
        stage.show();

        List<Integer> listFromY = road.getCountRoadBackground().getListFromY();

        //запуск моделироавния
        if (road instanceof Tunnel) {
            modelingAuto(
                    2 * road.getCountRoadBackground().getCountRoad(),
                    paneModelingAuto,
                    listFromY,
                    pauseAndProceed,
                    stop
            );
        } else {
            modelingAuto(
                    2 * road.getCountRoadBackground().getCountRoad() * 2,
                    paneModelingAuto,
                    listFromY,
                    pauseAndProceed,
                    stop
            );
        }

    }

    private void timer(HBox hBox) {
        hBox.setLayoutX(345);
        hBox.setLayoutY(5);
        Label time = new Label();
        time.setFont(new Font("Arial", 20));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        hBox.getChildren().addAll(time);
        Thread th = new Thread(() -> {
            while(!flagClose) {
                Platform.runLater(() -> time.setText(LocalTime.now().format(dateTimeFormatter)));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        th.start();
    }

    // получение информации какой транспорт выделен
    public void initButtonEvent(Rectangle transportChangeSpeed) {
        if (road instanceof Tunnel) {
            for (CopyOnWriteArrayList<Transport> element : listAutoInRoadsFromLeftToRight) {
                for (Transport rec : element) {
                    if (rec.getRectangle().equals(transportChangeSpeed)) {
                        this.transportChangeSpeed = rec;
                    }
                }
            }
        }
    }

    private void modelingAuto(int nThreads, Pane paneModelingAuto, List<Integer> listFromY, Button pauseAndProceed, Button stop) {
        //сисок содержащий списки автомобилей на каждой дороге
        List<CopyOnWriteArrayList<Transport>> listRoads = new ArrayList<CopyOnWriteArrayList<Transport>>();

        //список сожержащий элементы(генерация , логика)
        controlOneRoadList = new ArrayList<>();
        //создаем фиксированный пул потоков
        executorService = Executors.newFixedThreadPool(nThreads);

        //создаем список со всеми объектами для генерации и логики
        for (int i = 0; i < (nThreads / 2); i++) {
            //добавляем новый элемент(контроллер генерации машин на дороге, логика машин) в список
            CopyOnWriteArrayList<Transport> concurrentList = new CopyOnWriteArrayList<Transport>();
            controlOneRoadList.add(new ControlOneRoad(
                    new ControllerGenericAuto(
                            this,
                            road,
                            concurrentList,
                            paneModelingAuto,
                            listFromY.get(i),
                            road.getCountRoadBackground().getCountRoad() - i > 0 ?
                                    -100 :
                                    1000,
                            road.getCountRoadBackground().getCountRoad() - i > 0 ?
                                    1000 :
                                    -100),
                    new ControllerLogicAuto(
                            i,
                            road,
                            road.getCountRoadBackground().getCountRoad() - i > 0 ?
                                    -100 :
                                    1000))
            );

            //добавляем списки автомобилей в соответствующий им список(по направлению)
            if (road.getCountRoadBackground().getCountRoad() - i > 0) {
                listAutoInRoadsFromLeftToRight.add(concurrentList);
                listControlRoadsFromLeftToRight.add(controlOneRoadList.get(i));
            } else {
                listAutoInRoadsFromRightToLeft.add(concurrentList);
                listControlRoadsFromRightToLeft.add(controlOneRoadList.get(i));
            }

            int finalI = i;

            executorService.execute(() ->
                    controlOneRoadList.get(finalI).getControllerGenericAuto().run()
            );

            executorService.execute(() ->
                    controlOneRoadList.get(finalI).getControllerLogicAuto().run()
            );
        }

        for (int i = 0; i < (nThreads / 2); i++) {
            controlOneRoadList.get(i).getControllerLogicAuto().setListOfRoads(
                    (road.getCountRoadBackground().getCountRoad() - i > 0) ? listAutoInRoadsFromLeftToRight
                            : listAutoInRoadsFromRightToLeft
            );

        }

        for (int i = 0; i < (nThreads / 2); i++) {
            controlOneRoadList.get(i).getControllerLogicAuto().setListControlTheRoadSingleDirection(
                    (road.getCountRoadBackground().getCountRoad() - i > 0) ? listControlRoadsFromLeftToRight
                            : listControlRoadsFromRightToLeft
            );

        }

        listRoads.addAll(listAutoInRoadsFromLeftToRight);
        listRoads.addAll(listAutoInRoadsFromRightToLeft);



        //событие при нажатие кнопки пауза
        pauseAndProceed.setOnMousePressed(event -> {
            onActionProceed(nThreads, pauseAndProceed, listRoads);
        });



        stop.setOnMousePressed(event -> {
            for (int i = 0; i < (nThreads / 2); i++) {
                controlOneRoadList.get(i).getControllerGenericAuto().setFlagClose(true);
                controlOneRoadList.get(i).getControllerGenericAuto().setPause(true);
                controlOneRoadList.get(i).getControllerLogicAuto().setClose(true);
            }

            flagClose = true;

            executorService.shutdown();
            settingsScene.start();
        });


    }

    private void onActionPause(int nThreads, Button pause, List<CopyOnWriteArrayList<Transport>> listRoads){
        if (!flagPause) {
            if(!listRoads.isEmpty()) {
                //сообщаем контроллеру генерации, чтобы не создавал новые машины
                for (int i = 0; i < (nThreads / 2); i++) {
                    controlOneRoadList.get(i).getControllerGenericAuto().setPause(true);
                    controlOneRoadList.get(i).getControllerGenericAuto().setNewTimes(true);
                }
            }

            pause.setStyle("-fx-background-image: url(image/activePlay.jpg)");
            pause.setOnMousePressed(event -> onActionProceed(nThreads, pause, listRoads));

            flagPause = true;
            ControllerPause controllerPause = new ControllerPause(controlOneRoadList, listRoads);
            pause.setOnAction(controllerPause);
        }
    }

    private void onActionProceed(int nThreads, Button proceed, List<CopyOnWriteArrayList<Transport>> listRoads){
        if (flagPause) {
            for (int i = 0; i < (nThreads / 2); i++) {
                controlOneRoadList.get(i).getControllerGenericAuto().setPause(false);
            }

            proceed.setStyle("-fx-background-image: url(image/activePause.jpg)");
            proceed.setOnMousePressed(event -> onActionPause(nThreads, proceed, listRoads));

            flagPause = false;

            ControllerProceed controllerProceed = new ControllerProceed(listRoads);
            proceed.setOnAction(controllerProceed);
        }
    }

}
