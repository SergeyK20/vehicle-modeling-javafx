package main.java.controller;

import javafx.animation.*;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import main.java.model.BuilderRoad;
import main.java.model.ControlOneRoad;
import main.java.model.Transport;
import main.java.model.Tunnel;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ControllerLogicAuto implements Runnable {
    private List<ControlOneRoad> listControlTheRoadSingleDirection;
    private CopyOnWriteArrayList<Transport> listOfScannedAuto;
    private List<CopyOnWriteArrayList<Transport>> listOfRoads;
    private boolean isLeftRoad;
    private boolean isRightRoad;
    private boolean isClose;
    private BuilderRoad road;
    private int startFromX;
    private int numberInListRoads;

    public ControllerLogicAuto(int numberInListRoads, BuilderRoad road, int startFromX) {
        this.numberInListRoads = numberInListRoads < road.getCountRoadBackground().getCountRoad() ?
                numberInListRoads :
                numberInListRoads - road.getCountRoadBackground().getCountRoad();
        this.isClose = false;
        this.road = road;
        this.startFromX = startFromX;
    }

    @Override
    public void run() {
        if (road instanceof Tunnel) {
            logicalForSingleLineTrafficFromLeftToRight();
        } else {
            switch (road.getCountRoadBackground().getCountRoad()) {
                case 1:
                    if (startFromX == -100) {
                        logicalForSingleLineTrafficFromLeftToRight();
                    } else {
                        logicalForSingleLineTrafficFromRightToLeft();
                    }
                    break;
                case 2:
                    if (startFromX == -100) {
                        if (numberInListRoads == 0) {
                            isRightRoad = true;
                        } else {
                            isLeftRoad = true;
                        }
                        logicalForDoubleLineTrafficFromLeftToRight();
                    } else {
                        if (numberInListRoads == 0) {
                            isLeftRoad = true;
                        } else {
                            isRightRoad = true;
                        }
                        logicalForSingleLineTrafficFromRightToLeft();
                    }
                    break;
                case 3:
                    if (startFromX == -100) {
                        if (numberInListRoads == 0) {
                            isRightRoad = true;
                        } else {
                            if (numberInListRoads == 1) {
                                isRightRoad = true;
                            }
                            isLeftRoad = true;
                        }
                        logicalForSingleLineTrafficFromLeftToRight();
                    } else {
                        if (numberInListRoads == 0) {
                            isLeftRoad = true;
                        } else {
                            if (numberInListRoads == 1) {
                                isLeftRoad = true;
                            }
                            isRightRoad = true;
                        }
                        logicalForSingleLineTrafficFromRightToLeft();
                    }
                    break;
            }
        }
    }

    private void logicalForSingleLineTrafficFromLeftToRight() {
        while (!isClose) {
            if (listOfScannedAuto != null) {
                listOfScannedAuto.sort(new Comparator<Transport>() {
                    @Override
                    public int compare(Transport o1, Transport o2) {
                        return (int) (o1.getIdNode() - o2.getIdNode());
                    }
                });
                System.out.println(listOfScannedAuto.toString());
                for (int i = 1; i < listOfScannedAuto.size(); i++) {
                    try {
                        if (listOfScannedAuto.get(i).getIdNode() != 0) {
                            if (listOfScannedAuto.get(i).getTranslateX() != 0.0) {
                                if (listOfScannedAuto.get(i).getTranslateX() >= (listOfScannedAuto.get(i - 1).getTranslateX() < 0 ?
                                        listOfScannedAuto.get(i - 1).getTranslateX() + 70 :
                                        listOfScannedAuto.get(i - 1).getTranslateX() - 70)) {
                                    universalLogicOfMovementInOneLine(listOfScannedAuto.get(i), i - 1);
                                }
                            }
                        }
                    } catch (Exception e) {
                        //System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    private void logicalForSingleLineTrafficFromRightToLeft() {
        while (!isClose) {
            if (listOfScannedAuto != null) {
                listOfScannedAuto.sort(new Comparator<Transport>() {
                    @Override
                    public int compare(Transport o1, Transport o2) {
                        return (int) (o1.getIdNode() - o2.getIdNode());
                    }
                });
                for (int i = 1; i < listOfScannedAuto.size(); i++) {
                    try {
                        if (listOfScannedAuto.get(i).getIdNode() != 0) {
                            if (listOfScannedAuto.get(i).getTranslateX() != 1000.0) {
                                if (listOfScannedAuto.get(i).getTranslateX() >= (listOfScannedAuto.get(i - 1).getTranslateX() > 900 ?
                                        listOfScannedAuto.get(i - 1).getTranslateX() - 70 :
                                        listOfScannedAuto.get(i - 1).getTranslateX() + 70)
                                ) {

                                    universalLogicOfMovementInOneLine(listOfScannedAuto.get(i), i - 1);
                                }
                            }
                        }
                    } catch (Exception e) {
                        // Похоже там нууул
                    }
                }
            }
        }
    }

    private void universalLogicOfMovementInOneLine(Transport transport, int beforeTransportIndex) {
        Transport beforeTransport = listOfScannedAuto.get(beforeTransportIndex);
        if (beforeTransport.isFlagPause()) {
            transport.setFlagPause(true);
            transport.getAnimation().pause();
        } else {
            if (transport.isFlagPause()) {
                transport.setFlagPause(false);
                transport.getAnimation().play();
            }
            //если обогнал
           /* if(transport.isOvertaking()){
                //если приближаемся к тому кто не обгонял
                if(!beforeTransport.isOvertaking()) {
                    transport.getAnimation().setRate(beforeTransport.getAnimation().getRate() * (1100 / (900 - transport.getAnimation().getFromX())));
                } //если приближаемся к тому кто обгонял
               *//* else {
                    transport.getAnimation().setRate(beforeTransport.getAnimation().getRate() *
                            ((beforeTransport.getAnimation().getToX() - beforeTransport.getAnimation().getFromX())
                                    /
                                    ()

                            );
                }*//*
            } else {
                if (!beforeTransport.isOvertaking()) {*/
                    transport.getAnimation().setRate(beforeTransport.getAnimation().getRate());
                /*} else {
                    transport.getAnimation().setRate(beforeTransport.getAnimation().getRate() / (1100 / (1100 - beforeTransport.getAnimation().getFromX())));
                }
            }*/

        }
    }

    private void logicalForDoubleLineTrafficFromLeftToRight() {
        long id;
        while (!isClose) {
            if (listOfScannedAuto != null) {
                listOfScannedAuto.sort(new Comparator<Transport>() {
                    @Override
                    public int compare(Transport o1, Transport o2) {
                        return (int) (o1.getIdNode() - o2.getIdNode());
                    }
                });

                for (int i = 1; i < listOfScannedAuto.size(); i++) {
                    try {
                        if (listOfScannedAuto.get(i).getIdNode() != 0) {
                            if (listOfScannedAuto.get(i).getTranslateX() != 0.0) {
                                if (listOfScannedAuto.get(i).getTranslateX() >= (listOfScannedAuto.get(i - 1).getTranslateX() < 0 ?
                                        listOfScannedAuto.get(i - 1).getTranslateX() + 70 :
                                        listOfScannedAuto.get(i - 1).getTranslateX() - 70)
                                ) {
                                    if (isLeftRoad) {
                                        //System.out.println("Хочу обогнать...");
                                        //получаю список машин на левой дороге от меня
                                        CopyOnWriteArrayList<Transport> listLeftRoad = listOfRoads.get(numberInListRoads - 1);
                                        if (isCanMove(listOfScannedAuto.get(i).getTranslateX(), listLeftRoad)) {
                                            if ((id = idToGoFor(listOfScannedAuto.get(i).getTranslateX(), listLeftRoad)) > -1) {
                                                //удаляем из транспорт из главной дороги
                                                Transport transport = listOfScannedAuto.get(i);
                                                listOfScannedAuto.remove(i);

                                                //изменяем id машины, которая обгоняет
                                                transport.setIdNode(id);

                                                System.out.println("Обгоняю...");
                                                //animation
                                                double speed = transport.getAnimation().getRate();
                                                PathTransition pathTransition = new PathTransition();
                                                pathTransition.setPath(new Path(new MoveTo(transport.getTranslateX(), transport.getTranslateY()),
                                                        new LineTo(transport.getTranslateX() + 70, transport.getTranslateY() - 68)));
                                                pathTransition.setRate(1);
                                                transport.setRotate(-45.0);
                                                transport.getAnimation().stop();
                                                pathTransition.setNode(transport);

                                                pathTransition.play();



                                                pathTransition.setOnFinished(actionEvent -> {
                                                    System.out.println("Обгон завершен, продолжаю движение...");
                                                    TranslateTransition translateTransition = new TranslateTransition();
                                                    transport.setRotate(0);
                                                    translateTransition.setFromY(transport.getTranslateY());
                                                    translateTransition.setFromX(transport.getTranslateX());
                                                    translateTransition.setToX(1100 + translateTransition.getFromX());
                                                    translateTransition.setRate(speed);
                                                    translateTransition.setNode(transport);
                                                    translateTransition.setCycleCount(1);
                                                    translateTransition.setInterpolator(Interpolator.LINEAR);

                                                    transport.setAnimation(translateTransition);
                                                    transport.getAnimation().play();

                                                    translateTransition.setOnFinished(actionEvent1 -> {
                                                        listLeftRoad.remove(transport);
                                                    });
                                                });

                                                System.out.println(id);
                                                for(Transport element : listLeftRoad){
                                                    System.out.print(element.getIdNode()  + " ");
                                                }


                                                //изменяем id машин, которые будут ехать после нее
                                                for(Transport element : listLeftRoad){
                                                    long idLong = 0;
                                                    if(id <= (idLong = element.getIdNode()) ){
                                                        listLeftRoad.remove(element);
                                                        element.setIdNode(++idLong);
                                                        listLeftRoad.add(element);
                                                    }
                                                }

                                                listControlTheRoadSingleDirection.get(0).getControllerGenericAuto().setIncrementIndex();
                                                transport.setOvertaking(true);

                                                System.out.println();
                                                for(Transport element : listLeftRoad){
                                                    System.out.print(element.getIdNode()  + " ");
                                                }

                                                //добавляем ее в список машин из другой дороги
                                                listLeftRoad.add(transport);

                                                System.out.println();
                                                for(Transport element : listLeftRoad){
                                                    System.out.print(element.getIdNode()  + " ");
                                                }

                                                System.out.println();

                                            } else {
                                                universalLogicOfMovementInOneLine(listOfScannedAuto.get(i), i - 1);
                                            }
                                        } else {
                                            universalLogicOfMovementInOneLine(listOfScannedAuto.get(i), i - 1);
                                        }
                                    } else {
                                        //universalLogicOfMovementInOneLine(listOfScannedAuto.get(i), i - 1);
                                        if(isRightRoad){
                                            CopyOnWriteArrayList<Transport> listRightRoad = listOfRoads.get(numberInListRoads + 1);
                                            if (isCanMove(listOfScannedAuto.get(i).getTranslateX(), listRightRoad)) {
                                                if ((id = idToGoFor(listOfScannedAuto.get(i).getTranslateX(), listRightRoad)) > -1) {
                                                    //удаляем из транспорт из главной дороги
                                                    Transport transport = listOfScannedAuto.get(i);
                                                    listOfScannedAuto.remove(i);

                                                    //изменяем id машины, которая обгоняет
                                                    transport.setIdNode(id);

                                                    System.out.println("Обгоняю...");
                                                    //animation
                                                    double speed = transport.getAnimation().getRate();
                                                    PathTransition pathTransition = new PathTransition();
                                                    pathTransition.setPath(new Path(new MoveTo(transport.getTranslateX(), transport.getTranslateY()),
                                                            new LineTo(transport.getTranslateX() + 70, transport.getTranslateY() + 100)));
                                                    pathTransition.setRate(1);
                                                    transport.setRotate(45.0);
                                                    transport.getAnimation().stop();
                                                    pathTransition.setNode(transport);

                                                    pathTransition.play();



                                                    pathTransition.setOnFinished(actionEvent -> {
                                                        System.out.println("Обгон завершен, продолжаю движение...");
                                                        TranslateTransition translateTransition = new TranslateTransition();
                                                        transport.setRotate(0);
                                                        translateTransition.setFromY(transport.getTranslateY());
                                                        translateTransition.setFromX(transport.getTranslateX());
                                                        translateTransition.setToX(1100 + translateTransition.getFromX());
                                                        translateTransition.setRate(speed);
                                                        translateTransition.setNode(transport);
                                                        translateTransition.setCycleCount(1);
                                                        translateTransition.setInterpolator(Interpolator.LINEAR);

                                                        transport.setAnimation(translateTransition);
                                                        transport.getAnimation().play();

                                                        translateTransition.setOnFinished(actionEvent1 -> {
                                                            listRightRoad.remove(transport);
                                                        });
                                                    });

                                                    System.out.println(id);
                                                    for(Transport element : listRightRoad){
                                                        System.out.print(element.getIdNode()  + " ");
                                                    }


                                                    //изменяем id машин, которые будут ехать после нее
                                                    for(Transport element : listRightRoad){
                                                        long idLong = 0;
                                                        if(id <= (idLong = element.getIdNode()) ){
                                                            listRightRoad.remove(element);
                                                            element.setIdNode(++idLong);
                                                            listRightRoad.add(element);
                                                        }
                                                    }

                                                    listControlTheRoadSingleDirection.get(0).getControllerGenericAuto().setIncrementIndex();
                                                    transport.setOvertaking(true);

                                                    System.out.println();
                                                    for(Transport element : listRightRoad){
                                                        System.out.print(element.getIdNode()  + " ");
                                                    }

                                                    //добавляем ее в список машин из другой дороги
                                                    listRightRoad.add(transport);

                                                    System.out.println();
                                                    for(Transport element : listRightRoad){
                                                        System.out.print(element.getIdNode()  + " ");
                                                    }

                                                    System.out.println();

                                                } else {
                                                    universalLogicOfMovementInOneLine(listOfScannedAuto.get(i), i - 1);
                                                }
                                            } else {
                                                universalLogicOfMovementInOneLine(listOfScannedAuto.get(i), i - 1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        //System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    private boolean isCanMove(double translateX, CopyOnWriteArrayList<Transport> list) {
        for (int i = 1; i < list.size(); i++) {
            if ((list.get(i).getTranslateX() < (translateX - 40)) && list.get(i - 1).getTranslateX() > (translateX + 120)) {
                return true;
            }
        }
        return false;
    }

    private long idToGoFor(double translateX, CopyOnWriteArrayList<Transport> list) {
        for (int i = 1; i < list.size(); i++) {
            if ((list.get(i).getTranslateX() < (translateX - 30)) && (list.get(i - 1).getTranslateX() > (translateX + 120))) {
                return list.get(i).getIdNode();
            }
        }
        return -1;
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }

    public CopyOnWriteArrayList<Transport> getListOfScannedAuto() {
        return listOfScannedAuto;
    }

    public void setListOfScannedAuto(CopyOnWriteArrayList<Transport> listOfScannedAuto) {
        this.listOfScannedAuto = listOfScannedAuto;
    }

    public List<CopyOnWriteArrayList<Transport>> getListOfRoads() {
        return listOfRoads;
    }

    public void setListOfRoads(List<CopyOnWriteArrayList<Transport>> listOfRoads) {
        this.listOfRoads = listOfRoads;
        listOfScannedAuto = listOfRoads.get(numberInListRoads);
    }

    public List<ControlOneRoad> getListControlTheRoadSingleDirection() {
        return listControlTheRoadSingleDirection;
    }

    public void setListControlTheRoadSingleDirection(List<ControlOneRoad> listControlTheRoadSingleDirection) {
        this.listControlTheRoadSingleDirection = listControlTheRoadSingleDirection;
    }

    public int getNumberInListRoads() {
        return numberInListRoads;
    }

    public void setNumberInListRoads(int numberInListRoads) {
        this.numberInListRoads = numberInListRoads;
    }

    private Transport findById(long id) {
        for (Transport transport : listOfScannedAuto) {
            if (transport.getIdNode() == id) {
                return transport;
            }
        }
        return null;
    }

    private Transport findByIdLeftRoad(long id, CopyOnWriteArrayList<Transport> list) {
        for (Transport transport : list) {
            if (transport.getIdNode() == id) {
                return transport;
            }
        }
        return null;
    }


}
