package main.java.controller;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import main.java.model.BuilderRoad;
import main.java.model.Transport;
import main.java.view.GenericSceneTunnel;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Класс генерирующий автомобиль на одной из полос
 */
public class ControllerGenericAuto implements Runnable {

    private GenericSceneTunnel genericSceneTunnel;
    private Boolean isNewTimes;
    private boolean flagClose;
    private Boolean isPause;
    private Long newTimesSec;
    private Long startNewAuto;
    private BuilderRoad road;
    private CopyOnWriteArrayList<Transport> list;
    private Pane pane;
    private int fromY;
    private int fromX;
    private int toX;
    private int index;

    public ControllerGenericAuto(GenericSceneTunnel genericSceneTunnel,
                                 BuilderRoad road,
                                 CopyOnWriteArrayList<Transport> list,
                                 Pane pane,
                                 int fromY,
                                 int fromX,
                                 int toX) {
        this.genericSceneTunnel = genericSceneTunnel;
        this.road = road;
        this.list = list;
        this.pane = pane;
        this.fromY = fromY;
        this.fromX = fromX;
        this.toX = toX;
        newTimesSec = 0L;
        startNewAuto = 0L;
        isNewTimes = false;
        isPause = false;
        index = 0;
    }

    @Override
    public void run() {
        startMod();
    }

    private void startMod() {
        double speed;
        long times = 0L;
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
                try {
                    if (fromX == -100) {
                        while (index != 0 && findById(index - 1).getTranslateX() <= 0) {

                        }
                    } else {
                        while (index != 0 && findById(index - 1).getTranslateX() >= 900) {

                        }
                    }
                } catch (NullPointerException e) {
                    System.out.println("Ошибка пр соблюдении дистанции...");
                }

                tt.setFromX(fromX);
                tt.setFromY(fromY);
                tt.setToX(toX);

                //установка скорости
                speed = road.getSpeed().getSpeed();

                tt.setRate(speed / 2000.0);

                //создание новой машины
                Transport transport = new Transport(index++, tt);

                transport.setTextSpeed(tt.getRate());

                if (fromX == 1000) {
                    transport.getRectangle().setRotate(180);
                    System.out.println(transport.getSpeed());
                }

                //добавление ее в список для отслеживания правил пдд
                list.add(transport);


                //добавляет новую модель на сцену
                if (transport.getAnimation() != null) {
                    Platform.runLater(() -> {
                        pane.getChildren().add(transport);
                    });


                    //время создания новой машины (нужна, если будет пауза)
                    startNewAuto = System.nanoTime();

                    //выделяет каждую машину при нажатии
                    transport.setOnMousePressed(new ControllerObjectSelection(list, genericSceneTunnel));

                    //устанаввливает временную задежку между созданием машин
                    try {
                        TimeUnit.SECONDS.sleep(times);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }


                    //дейсвие перед окончанием анимации машины
                    transport.getAnimation().setOnFinished(event -> {
                        if (transport.getTranslateX() == 1000.0 || transport.getTranslateX() == -100.0) {
                            try {
                                list.remove(transport);
                                //Platform.runLater(() -> pane.getChildren().remove(transport));
                            } catch (NullPointerException e) {
                                // e.printStackTrace();
                            }

                        }
                        if (transport.getAnimation().getStatus() == Animation.Status.STOPPED && !(transport.getAnimation().getNode().getTranslateX() == 1000.0 || transport.getAnimation().getNode().getTranslateX() == -100.0)) {
                    /*System.out.println("index before: " + index);
                    //--index;
                    System.out.println("index after: " + index);*/
                        }
                    });
                }
            }
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex() {
        --this.index;
    }

    // получение времени прошедшего с начала ожидания создания до паузы
    public void initNewTimeSec() {
        this.newTimesSec = System.nanoTime() - startNewAuto;
    }


    public boolean isFlagClose() {
        return flagClose;
    }

    public void setFlagClose(boolean flagClose) {
        this.flagClose = flagClose;
    }

    public Boolean getPause() {
        return isPause;
    }

    public void setPause(Boolean pause) {
        isPause = pause;
    }

    public Boolean getNewTimes() {
        return isNewTimes;
    }

    public void setNewTimes(Boolean newTimes) {
        isNewTimes = newTimes;
    }

    public void setIncrementIndex() {
        System.out.println("До" + index);
        ++index;
        System.out.println("После" + index);
    }

    public void setDecrementIndex() {
        index--;
    }

    private Transport findById(long id) {
        for (Transport transport : list) {
            if (transport.getIdNode() == id) {
                return transport;
            }
        }
        return null;
    }
}
