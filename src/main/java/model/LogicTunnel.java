package main.java.model;

import java.util.Map;

public class LogicTunnel implements Runnable {
    private Map<String, Transport> list;

    public LogicTunnel(Map<String, Transport> list) {
        this.list = list;
    }

    @Override
    public void run() {
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
    }
}
