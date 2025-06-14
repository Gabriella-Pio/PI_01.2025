package br.teatroabc.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class NavBarController {

    @FXML
    public void switchToInicial(ActionEvent event) {
        NavigationController.switchToTela("/TelaInicial.fxml", event);
    }

    @FXML
    public void switchToPecas(ActionEvent event) {
        NavigationController.switchToTela("/Pecas.fxml", event);
    }

    @FXML
    public void switchToSobre(ActionEvent event) {
        NavigationController.switchToTela("/Sobre.fxml", event);
    }

    @FXML
    public void switchToMeusIngressos(ActionEvent event) {
        NavigationController.switchToTela("/MeusIngressos.fxml", event);
    }

    @FXML
    public void switchToCadastroFidelidade(ActionEvent event) {
        NavigationController.switchToTela("/CadastroFidelidade.fxml", event);
    }

    @FXML
    public void voltar(ActionEvent event) {
        NavigationController.voltar(event);
    }
}
