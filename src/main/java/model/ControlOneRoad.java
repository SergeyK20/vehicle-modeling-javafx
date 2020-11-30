package main.java.model;

import main.java.controller.ControllerGenericAuto;

import main.java.controller.ControllerLogicAuto;

/**
 * Класс вбирающий себя контроллер для генерации автомобилей и контроллер для логики езды, для одной полосы
 */
public class ControlOneRoad {
    private ControllerGenericAuto controllerGenericAuto;
    private ControllerLogicAuto controllerLogicAuto;

    public ControlOneRoad(ControllerGenericAuto controllerGenericAuto, ControllerLogicAuto controllerLogicAuto) {
        this.controllerGenericAuto = controllerGenericAuto;
        this.controllerLogicAuto = controllerLogicAuto;
    }

    public ControllerGenericAuto getControllerGenericAuto() {
        return controllerGenericAuto;
    }

    public void setControllerGenericAuto(ControllerGenericAuto controllerGenericAuto) {
        this.controllerGenericAuto = controllerGenericAuto;
    }


    public ControllerLogicAuto getControllerLogicAuto() {
        return controllerLogicAuto;
    }

    public void setControllerLogicAuto(ControllerLogicAuto controllerLogicAuto) {
        this.controllerLogicAuto = controllerLogicAuto;
    }
}
