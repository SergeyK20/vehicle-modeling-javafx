package main.java.model;

import java.util.Map;

public class LogicTunnel implements Runnable {
    private Map<String, Transport> list;
    private boolean isClose;
    private BuilderRoad road;
    private int startFromX;

    public LogicTunnel(Map<String, Transport> list, BuilderRoad road, int startFromX) {
        this.list = list;
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
                    break;
                case 3:
                    break;
            }
        }
    }

    private void logicalForSingleLineTrafficFromLeftToRight() {
        while (!isClose) {
            for (Transport transport : list.values()) {
                try {
                    if (transport.getIdNode() != 0) {
                        if (transport.getTranslateX() != 0.0) {
                            if (transport.getTranslateX() >= ((list.get(Long.toString(transport.getIdNode() - 1)).getTranslateX() < 0) ?
                                    list.get(Long.toString(transport.getIdNode() - 1)).getTranslateX() + 60 :
                                    list.get(Long.toString(transport.getIdNode() - 1)).getTranslateX() - 60)) {

                                    universalLogicOfMovementInOneLine(transport);
                            }
                        }
                    }
                } catch (Exception e) {
                    //System.out.println(e.getMessage());
                }
            }
        }
    }

    private void logicalForSingleLineTrafficFromRightToLeft() {
        while (!isClose) {
            for (Transport transport : list.values()) {
                try {
                    if (transport.getIdNode() != 0) {
                        if (transport.getTranslateX() != 1000.0) {
                            if (transport.getTranslateX() <= ((list.get(Long.toString(transport.getIdNode() - 1)).getTranslateX() > 900) ?
                                    list.get(Long.toString(transport.getIdNode() - 1)).getTranslateX() - 60 :
                                    list.get(Long.toString(transport.getIdNode() - 1)).getTranslateX() + 60)) {

                                    universalLogicOfMovementInOneLine(transport);
                            }
                        }
                    }
                } catch (Exception e) {
                    //System.out.println(e.getMessage());
                }
            }
        }
    }

    private void universalLogicOfMovementInOneLine(Transport transport){
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

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }
}
