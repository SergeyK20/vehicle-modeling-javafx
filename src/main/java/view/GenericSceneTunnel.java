package main.java.view;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.java.controller.ControllerChangeSpeed;
import main.java.controller.ControllerObjectSelection;
import main.java.controller.ControllerPause;
import main.java.controller.ControllerProceed;
import main.java.model.BuilderRoad;
import main.java.model.LogicTunnel;
import main.java.model.Transport;
import main.java.model.Tunnel;

import javax.naming.PartialResultException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

public class GenericSceneTunnel {

    private Stage stage;
    private long index;
    private boolean flagClose;
    private Button btnChangeSpeed;
    private TextField speedKmCh;
    private Transport transportChangeSpeed;
    private long times;
    private Long newTimesSec = 0L;
    private Long startNewAuto;
    private Boolean isPause;
    private Boolean isNewTimes;
    private BuilderRoad road;
    private ExecutorService executorService;
    private SettingsScene settingsScene;
    private LogicTunnel logicTunnel;
    private List<Double> testList = new ArrayList<Double>();

    public GenericSceneTunnel(Stage stage, BuilderRoad road, SettingsScene settingsScene) {
        this.stage = stage;
        this.road = road;
        this.settingsScene = settingsScene;
        isPause = false;
        isNewTimes = false;
        flagClose = false;
        index = 0;
    }

    private void setFlagClose(){
        this.flagClose = true;
    }

    public void start() {
        //пеердается с объектом моделирования
        Background bk = road.getCountRoadBackground().getBackground();

        //панель моделирования
        Pane pane = new Pane();
        pane.setBackground(bk);



        //если туннель, то плявляется доболнительный функционал по изменениию скорости
        if (road instanceof Tunnel) {
            btnChangeSpeed = new Button();
            btnChangeSpeed.setLayoutY(500);
            btnChangeSpeed.setLayoutX(250);
            btnChangeSpeed.setText("Изменить скорость");
            pane.getChildren().add(btnChangeSpeed);
            speedKmCh = new TextField();
            speedKmCh.setLayoutX(400);
            speedKmCh.setLayoutY(500);
            pane.getChildren().add(speedKmCh);

        } else {

        }

        //список
        //ConcurrentMap<String, Transport> listAutoInRoad = new ConcurrentHashMap<String, Transport>();

        //поток нужный для старта моделирования

        //три кнопки внизу
        Button pause = new Button("Пауза");
        pause.setLayoutY(550);
        pause.setLayoutX(400);
       /* pause.setOnMousePressed(event -> {
            ControllerPause controllerPause = new ControllerPause( this, listAutoInRoad);
            pause.setOnAction(controllerPause);
            isPause = true;
            isNewTimes = true;
        });*/
        Button proceed = new Button("Продолжить");
        proceed.setLayoutX(500);
        proceed.setLayoutY(550);
        /*proceed.setOnMousePressed(event -> {
            ControllerProceed controllerProceed = new ControllerProceed(listAutoInRoad);
            proceed.setOnAction(controllerProceed);
            isPause = false;
        });*/
        Button stop = new Button("Остановить");
        stop.setLayoutY(550);
        stop.setLayoutX(600);
        stop.setOnAction(actionEvent -> {
            testList.sort(new Comparator<Double>() {
                @Override
                public int compare(Double o1, Double o2) {
                    return o1.compareTo(o2);
                }
            });

            int size = testList.size();
            double raz = testList.get(size - 1) - testList.get(0);
            double shag = raz / 25.0;
            int i = 0;
            double temp = testList.get(0);
            while(temp < testList.get(size - 1)) {
                temp += shag;
                while (i < size && testList.get(i) <= temp) {
                    System.out.print("*");
                    i++;
                }
                System.out.println();
            }
            setFlagClose();
            isPause = true;
            logicTunnel.setClose(true);
            executorService.shutdown();
            settingsScene.start();
        });

        pane.getChildren().addAll(pause, stop, proceed);


        //при каждом новом нажатии регистрируется метод изменения скорости
        btnChangeSpeed.setOnMousePressed(mouseEvent -> {
            try {
                if (Integer.parseInt(speedKmCh.getText()) >= 0 && Integer.parseInt(speedKmCh.getText()) <= 80) {
                    btnChangeSpeed.setOnAction(new ControllerChangeSpeed(speedKmCh.getText(), transportChangeSpeed));
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

        //сцена на которой все располагается
        Scene scene = new Scene(pane, 800, 600);
        /*stage.setResizable(false);*/
        stage.setScene(scene);
        stage.show();

        List<Integer> listFromY = road.getCountRoadBackground().getListFromY();

        //запуск моделироавния
        if (road instanceof Tunnel) {
            ConcurrentMap<String, Transport> listAutoInRoad = new ConcurrentHashMap<String, Transport>();

            executorService = Executors.newFixedThreadPool(2);

            executorService.execute(() -> {
                startMod(listAutoInRoad, pane, listFromY.get(0), -100, 1000);
            });

            logicTunnel = new LogicTunnel(listAutoInRoad);
            // поток запускающий проверку на столкновение
            executorService.execute(logicTunnel);

            pause.setOnMousePressed(event -> {
                ControllerPause controllerPause = new ControllerPause(this, listAutoInRoad);
                pause.setOnAction(controllerPause);
                isPause = true;
                isNewTimes = true;
            });

            proceed.setOnMousePressed(event -> {
                ControllerProceed controllerProceed = new ControllerProceed(listAutoInRoad);
                proceed.setOnAction(controllerProceed);
                isPause = false;
            });
        } else {
            switch (road.getCountRoadBackground().getCountLine()) {
                case 1:
                    ConcurrentMap<String, Transport> listAutoInOneRoadFromLeftToRight = new ConcurrentHashMap<String, Transport>();
                    ConcurrentMap<String, Transport> listAutoInOneRoadFromRightToLeft = new ConcurrentHashMap<String, Transport>();
                    executorService = Executors.newFixedThreadPool(3);

                    executorService.execute(() -> {
                        startMod(listAutoInOneRoadFromLeftToRight, pane, listFromY.get(0), -100, 1000);
                    });
                    executorService.execute(() -> {
                        startMod(listAutoInOneRoadFromRightToLeft, pane, listFromY.get(1), 1000, -100);
                    });

                    pause.setOnMousePressed(event -> {
                        ControllerPause controllerPause = new ControllerPause(this, listAutoInOneRoadFromLeftToRight, listAutoInOneRoadFromRightToLeft);
                        pause.setOnAction(controllerPause);
                        isPause = true;
                        isNewTimes = true;
                    });
                    proceed.setOnMousePressed(event -> {
                        ControllerProceed controllerProceed = new ControllerProceed(listAutoInOneRoadFromLeftToRight, listAutoInOneRoadFromRightToLeft);
                        proceed.setOnAction(controllerProceed);
                        isPause = false;
                    });
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
            }
        }


    }


    // старт моделирования
    private void startMod(
            ConcurrentMap<String, Transport> list,
            Pane pane,
            int fromY,
            int fromX,
            int toX
    ) {
        double speed;

        while (!flagClose) {
            System.out.println(flagClose);
            // проверка есть ли в данный момент пауза
            System.out.println("Pause");
            while (!isPause) {
                // если была пауза, то изменяем время генерации на один раз
                if (isNewTimes) {
                    times = TimeUnit.NANOSECONDS.convert(times, TimeUnit.SECONDS) - newTimesSec;
                    isNewTimes = false;
                    try {
                        TimeUnit.NANOSECONDS.sleep(times);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }

                //установка времени между которым появляется новая машина
                times = Math.round(road.getStreamTransport().getTimes());

                TranslateTransition tt = new TranslateTransition();

                // следит за тем, чтобы модели не создавались друг на друге
                while (index != 0 && list.get(Long.toString(index - 1)).getTranslateX() <= (fromX + 100)) {

                }

                tt.setFromX(fromX);
                tt.setFromY(fromY);
                tt.setToX(toX);
                //установка скорости
                speed = road.getSpeed().getSpeed();
                testList.add(speed);
                System.out.println("speed " + speed);
                System.out.println("times " + times);
                tt.setRate(speed / 2000.0);
                //создание новой машины
                Transport transport = new Transport(index, tt);

                //добавление ее в список для отслеживания правил пдд
                list.put(Long.toString(index++), transport);

                //добавляет новую модель на сцену
                Platform.runLater(() -> {
                    pane.getChildren().add(transport);
                });



                //время создания новой машины (нужна, если будет пауза)
                startNewAuto = System.nanoTime();


                System.out.println(transport.getTranslateX());

                System.out.println(transport.getTranslateX());

                //выделяет каждую машину при нажатии
                transport.addEventHandler(MouseEvent.MOUSE_PRESSED,
                        new ControllerObjectSelection(list, this)
                );
                System.out.println(transport.getTranslateX());
                //устанаввливает временную задежку между созданием машин
                try {
                    TimeUnit.SECONDS.sleep(times);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }



                //дейсвие перед окончанием анимации машины
                tt.setOnFinished(event -> {

                    System.out.println("Закончил");
                    list.remove(Long.toString(transport.getIdNode()));
                    pane.getChildren().remove(tt.getNode());
                    tt.stop();
                    tt.setDuration(Duration.ZERO);
                });
            }
        }
    }

    // получение информации какой транспорт выделен
    public void initButtonEvent(Transport transportChangeSpeed) {
        this.transportChangeSpeed = transportChangeSpeed;
    }

    // получение времени прошедшего с начала ожидания создания до паузы
    public void initNewTimeSec() {
        this.newTimesSec = System.nanoTime() - startNewAuto;
    }

}
