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
import main.java.model.Transport;
import main.java.model.Tunnel;

import java.util.concurrent.*;

public class GenericScene {

    private Stage stage;
    private static long index = 0;
    private boolean flagClose = false;
    private Button btnChangeSpeed;
    private TextField speedKmCh;
    private Transport transportChangeSpeed;
    private long times;
    private Long newTimesSec = 0L;
    private Long startNewAuto;
    private Boolean isPause;
    private Boolean isNewTimes;
    private BuilderRoad road;
    ExecutorService executorService;

    public GenericScene(Stage stage, BuilderRoad road) {
        this.stage = stage;
        this.road = road;
    }

    public void start() {
        //пеердается с объектом моделирования
        Background bk = road.getCountRoadBackground().getBackground();

        //панель моделирования
        Pane pane = new Pane();
        pane.setBackground(bk);

        //инициализация
        isPause = false;
        isNewTimes = false;

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
            executorService = Executors.newFixedThreadPool(2);
        }

        //список
        ConcurrentMap<String, Transport> list = new ConcurrentHashMap<String, Transport>();

        //поток нужный для старта моделирования
        //ExecutorService exStart = Executors.newSingleThreadExecutor();

        //три кнопки внизу
        Button pause = new Button("Пауза");
        pause.setLayoutY(550);
        pause.setLayoutX(400);
        pause.setOnMousePressed(event -> {
            ControllerPause controllerPause = new ControllerPause(list, this);
            pause.setOnAction(controllerPause);
            isPause = true;
            isNewTimes = true;
        });
        Button proceed = new Button("Продолжить");
        proceed.setLayoutX(500);
        proceed.setLayoutY(550);
        proceed.setOnMousePressed(event -> {
            ControllerProceed controllerProceed = new ControllerProceed(list);
            proceed.setOnAction(controllerProceed);
            isPause = false;
        });
        Button stop = new Button("Остановить");
        stop.setLayoutY(550);
        stop.setLayoutX(600);
        pane.getChildren().addAll(pause, stop, proceed);


        //при каждом новом нажатие потонову регистрируется метод изменения скорости
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
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        //запуск моделироавния
        executorService.execute(() -> {
            startMod(list, pane);
        });

        // поток запускающий проверку на столкновение
        executorService.execute(() -> {
            while (true) {
                for (Transport transport : list.values()) {
                    try {
                        if (transport.getIdNode() != 0) {
                            if (transport.getTranslateX() >= list.get(Long.toString(transport.getIdNode() - 1)).getTranslateX() - 60) {
                                if (list.get(Long.toString(transport.getIdNode() - 1)).isFlagPause()) {
                                    transport.setFlagPause(true);
                                    transport.getAnimation().pause();
                                } else {
                                    if (transport.isFlagPause()) {
                                        transport.setFlagPause(false);
                                        transport.getAnimation().play();
                                    }
                                    transport.getAnimation().setRate(list.get(Long.toString(transport.getIdNode() - 1)).getAnimation().getRate());
                                }
                            }
                        }
                    } catch (Exception e) {
                        //System.out.println(e.getMessage());
                    }
                }
            }
        });


    }


    // старт моделирования
    private void startMod(
            ConcurrentMap<String, Transport> list,
            Pane pane) {

        while (!flagClose) {
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
                times = road.getStreamTransport().getTimes();


                TranslateTransition tt = new TranslateTransition();

                // следит за тем, чтобы модели не создавались друг на друге
                while (index != 0 && list.get(Long.toString(index - 1)).getTranslateX() <= 100) {

                }

                //создание новой машины
                Transport transport = new Transport(index, tt);
                transport.setSpeed(road.getSpeed().getSpeed());

                //добавление ее в список для отслеживания правил пдд
                list.put(Long.toString(index++), transport);

                //время создания новой машины (нужна, если будет пауза)
                startNewAuto = System.nanoTime();

                //добавляет новую модель на сцену
                Platform.runLater(() -> {
                    pane.getChildren().add(transport);
                });

                //выделяет каждую машину при нажатии
                transport.addEventHandler(MouseEvent.MOUSE_PRESSED,
                        new ControllerObjectSelection(list, this)
                );

                //устанаввливает временную задежку между созданием машин
                try {
                    TimeUnit.SECONDS.sleep(times);
                    System.out.println(times);
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
