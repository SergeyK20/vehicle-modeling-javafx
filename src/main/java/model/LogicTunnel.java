package main.java.model;

import java.util.Map;

public class LogicTunnel implements Runnable {
    private Map<String, Transport> list;
    private boolean isClose;

    public LogicTunnel(Map<String, Transport> list) {
        this.list = list;
        this.isClose = false;
    }

    @Override
    public void run() {
        while (!isClose) {
            for (Transport transport : list.values()) {
                try {
                    if (transport.getIdNode() != 0) {
                        //переделать, ошибочно работает при TranslateX < 0 TODO
                        /*System.out.println("2 - " + transport.getTranslateX());
                        System.out.println("1 - " + list.get(Long.toString(transport.getIdNode() - 1)).getTranslateX());
                        */
                        if(transport.getTranslateX() != 0.0) {
                            if (transport.getTranslateX() >= ((list.get(Long.toString(transport.getIdNode() - 1)).getTranslateX() < 0) ?
                                    list.get(Long.toString(transport.getIdNode() - 1)).getTranslateX() + 60 :
                                    list.get(Long.toString(transport.getIdNode() - 1)).getTranslateX() - 60)) {
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
                    }
                } catch (Exception e) {
                    //System.out.println(e.getMessage());
                }
            }
        }
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }
}
