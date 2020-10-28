package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;


public class Controller {

    @FXML
    private Rectangle rectangle1;

    @FXML
    public void onClick(javafx.scene.input.MouseEvent mouseEvent) {
        System.out.println("Опа опа опа па па ");
    }
}
