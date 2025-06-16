package br.teatroabc.controllers;

import br.teatroabc.Models.Cliente;
import br.teatroabc.Models.ItemVenda;
import br.teatroabc.Models.Venda;
import br.teatroabc.utils.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.ArrayList;

public class Controller {
    static State currentState;

    public static State getCurrentState() {
        return currentState;
    }

    public static void setCurrentState(State currentStateArg) {
        currentState = currentStateArg;
    }

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
        setCurrentState(new State());
        NavigationController.switchToTela("/Pecas.fxml", event);
    }
}

