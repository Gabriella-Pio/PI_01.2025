package br.teatroabc.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller {

    @FXML
    public void switchToLoginEntrar(ActionEvent event) {
        NavigationController.switchToTela("/LoginEntrar.fxml", event);
    }

    @FXML
    public void switchToLogin(ActionEvent event) {
        NavigationController.switchToTela("/Login.fxml", event);
    }

    @FXML
    public void switchToPecas(ActionEvent event) {
        NavigationController.switchToTela("/Pecas.fxml", event);
    }
}

