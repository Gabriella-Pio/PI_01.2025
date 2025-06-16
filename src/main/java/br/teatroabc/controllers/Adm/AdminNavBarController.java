package br.teatroabc.controllers.Adm;

import br.teatroabc.controllers.NavigationController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.Stack;

import static br.teatroabc.controllers.NavigationController.switchToTela;

public class AdminNavBarController {
    @FXML
    public void switchToInicial(ActionEvent event) {
        NavigationController.switchToTela("/TelaInicial.fxml", event);
    }

    @FXML
    public void switchToEstatisticasPecas(ActionEvent event) {
        NavigationController.switchToTela("/Adm/EstatisticasPecas.fxml", event);
    }

    @FXML
    public void switchToEstatisticasSessoes(ActionEvent event) {
        NavigationController.switchToTela("/Adm/EstatisticasSessoes.fxml", event);
    }

    @FXML
    public void switchToEstatisticasLucros(ActionEvent event) {
        NavigationController.switchToTela("/Adm/EstatisticasLucros.fxml", event);
    }

    @FXML
    public void switchToPrincipal(ActionEvent event) {
        NavigationController.switchToTela("/Adm/Principal.fxml", event);
    }

    @FXML
    public void voltar(ActionEvent event) {
        NavigationController.voltar(event);
    }
}
