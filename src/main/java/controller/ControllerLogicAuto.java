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
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Класс регулирующий логику езды списка автомобилей (на одной из полос)
 */
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
                        logicalForDoubleLineTrafficFromRightToLeft();
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

                for (int i = 1; i < listOfScannedAuto.size(); i++) {
                    try {
                        if (listOfScannedAuto.get(i).getIdNode() != 0) {
                            if (listOfScannedAuto.get(i).getTranslateX() != 0.0) {
                                if (listOfScannedAuto.get(i).getTranslateX() >= (listOfScannedAuto.get(i - 1).getTranslateX() < 0 ?
                                        listOfScannedAuto.get(i - 1).getTranslateX() + 80 :
                                        listOfScannedAuto.get(i - 1).getTranslateX() - 80)) {
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
                listOfScannedAuto.sort((o1, o2) -> (int) (o1.getIdNode() - o2.getIdNode()));
                for (int i = 1; i < listOfScannedAuto.size(); i++) {
                    try {
                        if (listOfScannedAuto.get(i).getIdNode() != 0) {
                            if (listOfScannedAuto.get(i).getTranslateX() != 1000.0) {
                                if (listOfScannedAuto.get(i).getTranslateX() <= listOfScannedAuto.get(i - 1).getTranslateX() + 80 && listOfScannedAuto.get(i).getTranslateX() != 0) {
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
            transport.getAnimation().setRate(beforeTransport.getAnimation().getRate());
            transport.setTextSpeed(transport.getAnimation().getRate());
        }
    }

    private void logicalForDoubleLineTrafficFromLeftToRight() {
        while (!isClose) {
            if (listOfScannedAuto != null) {
                listOfScannedAuto.sort((o1, o2) -> (int) (o1.getIdNode() - o2.getIdNode()));
                for (int i = 1; i < listOfScannedAuto.size(); i++) {
                    try {
                        if (listOfScannedAuto.get(i).getIdNode() != 0) {
                            if (listOfScannedAuto.get(i).getTranslateX() != 0.0) {
                                if (listOfScannedAuto.get(i).getTranslateX() >= listOfScannedAuto.get(i - 1).getTranslateX() - 70
                                ) {
                                    if (isLeftRoad) {
                                        //получаю список машин на левой дороге от меня
                                        CopyOnWriteArrayList<Transport> listLeftRoad = listOfRoads.get(numberInListRoads - 1);
                                        overtakingFromLeftToRight(i,
                                                listLeftRoad,
                                                numberInListRoads - 1,
                                                100,
                                                -55,
                                                -45.0,
                                                1100,
                                                0,
                                                -70,
                                                120);
                                    }
                                    if (isRightRoad) {
                                        CopyOnWriteArrayList<Transport> listRightRoad = listOfRoads.get(numberInListRoads + 1);
                                        overtakingFromLeftToRight(i,
                                                listRightRoad,
                                                numberInListRoads + 1,
                                                100,
                                                95,
                                                45.0,
                                                1100,
                                                0,
                                                -70,
                                                120);
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

    private void logicalForDoubleLineTrafficFromRightToLeft() {
        while (!isClose) {
            if (listOfScannedAuto != null) {
                listOfScannedAuto.sort((o1, o2) -> (int) (o1.getIdNode() - o2.getIdNode()));
                for (int i = 1; i < listOfScannedAuto.size(); i++) {
                    try {
                        if (listOfScannedAuto.get(i).getIdNode() != 0) {
                            if (listOfScannedAuto.get(i).getTranslateX() != 1000.0) {
                                if (listOfScannedAuto.get(i).getTranslateX() <= (listOfScannedAuto.get(i - 1).getTranslateX() + 80) && listOfScannedAuto.get(i).getTranslateX() != 0) {
                                    System.out.println(listOfScannedAuto.get(i).getTranslateX() + "<=" + (listOfScannedAuto.get(i - 1).getTranslateX() + 80));
                                    if (isLeftRoad) {
                                        //получаю список машин на левой дороге от меня
                                        CopyOnWriteArrayList<Transport> listLeftRoad = listOfRoads.get(numberInListRoads + 1);
                                        overtakingFromLeftToRight(i,
                                                listLeftRoad,
                                                numberInListRoads + 1,
                                                -80,
                                                95,
                                                -145.0,
                                                -1100,
                                                0,
                                                70,
                                                -120);
                                    }
                                    if (isRightRoad) {
                                        CopyOnWriteArrayList<Transport> listRightRoad = listOfRoads.get(numberInListRoads - 1);
                                        overtakingFromLeftToRight(i,
                                                listRightRoad,
                                                numberInListRoads - 1,
                                                -80,
                                                -55,
                                                -225.0,
                                                -1100,
                                                0,
                                                70,
                                                -120);
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

    private void overtakingFromLeftToRight(int index,
                                           CopyOnWriteArrayList<Transport> listNeighborRoad,
                                           int indexNeighborRoad,
                                           int updateTranslateX,
                                           int updateTranslateY,
                                           double rotate,
                                           int toX,
                                           double rotateTwo,
                                           int distanceToThePast,
                                           int distanceToTheNext) {

        long id;

        if (updateTranslateX > 0) {
            id = idToGoFor(listOfScannedAuto.get(index).getTranslateX(), listNeighborRoad, distanceToThePast, distanceToTheNext);
        } else {
            id = idToGoForTwo(listOfScannedAuto.get(index).getTranslateX(), listNeighborRoad, distanceToThePast, distanceToTheNext);
        }

        if (id > -1) {
            //удаляем из транспорт из главной дороги
            Transport transport = listOfScannedAuto.get(index);
            listOfScannedAuto.remove(index);

            //изменяем id машины, которая обгоняет
            transport.setIdNode(id);

            System.out.println("Обгоняю...");
            //animation
            double speed = transport.getAnimation().getRate();
            PathTransition pathTransition = new PathTransition();
            pathTransition.setPath(new Path(new MoveTo(transport.getTranslateX(), transport.getTranslateY()),
                    new LineTo(transport.getTranslateX() + updateTranslateX, transport.getTranslateY() + updateTranslateY)));
            pathTransition.setRate(1);
            transport.setRotate(rotate);
            transport.getAnimation().stop();
            pathTransition.setNode(transport);

            pathTransition.play();

            pathTransition.setOnFinished(actionEvent -> {
                System.out.println("Обгон завершен, продолжаю движение...");
                TranslateTransition translateTransition = new TranslateTransition();
                transport.setRotate(rotateTwo);
                translateTransition.setFromY(transport.getTranslateY());
                translateTransition.setFromX(transport.getTranslateX());
                translateTransition.setToX(translateTransition.getFromX() + toX);
                translateTransition.setRate(speed);
                translateTransition.setNode(transport);
                translateTransition.setCycleCount(1);
                translateTransition.setInterpolator(Interpolator.LINEAR);

                transport.setAnimation(translateTransition);
                transport.getAnimation().play();

                //изменяем id машин, которые будут ехать после нее
                for (Transport element : listNeighborRoad) {
                    long idLong;
                    if (id <= (idLong = element.getIdNode())) {
                        listNeighborRoad.remove(element);
                        element.setIdNode(++idLong);
                        listNeighborRoad.add(element);
                    }
                }

                listControlTheRoadSingleDirection.get(indexNeighborRoad).getControllerGenericAuto().setIncrementIndex();
                transport.setOvertaking(true);
                //добавляем ее в список машин из другой дороги
                listNeighborRoad.add(transport);

                translateTransition.setOnFinished(actionEvent1 -> {
                    listNeighborRoad.remove(transport);
                });
            });
        } else {
            universalLogicOfMovementInOneLine(listOfScannedAuto.get(index), index - 1);
        }
    }

    private long idToGoFor(double translateX, CopyOnWriteArrayList<Transport> list, int distanceToThePast, int distanceToTheNext) {
        for (int i = 1; i < list.size(); i++) {
            if ((list.get(i).getTranslateX() < (translateX + distanceToThePast)) && (list.get(i - 1).getTranslateX() > (translateX + distanceToTheNext))) {
                return list.get(i).getIdNode();
            }
        }
        return -1;
    }

    private long idToGoForTwo(double translateX, CopyOnWriteArrayList<Transport> list, int distanceToThePast, int distanceToTheNext) {
        for (int i = 1; i < list.size(); i++) {
            if ((list.get(i).getTranslateX() > (translateX + distanceToThePast)) && (list.get(i - 1).getTranslateX() < (translateX + distanceToTheNext))) {
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

}
