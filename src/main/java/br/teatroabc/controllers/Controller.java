package br.teatroabc.controllers;

import br.teatroabc.controllers.NavigationController;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;
import javafx.fxml.FXML;

public class Controller {

    @FXML
    public void switchToInicial(ActionEvent event) {
        NavigationController.switchToTela("/TelaInicial.fxml", event);
    }

    @FXML
    public void switchToCadastroLogin(ActionEvent event) {
        NavigationController.switchToTela("/LoginCadastro.fxml", event);
    }

    public void switchToLogin(ActionEvent event) {
        NavigationController.switchToTela("/Login.fxml", event);
    }

    public void switchToCadastro(ActionEvent event) {
        NavigationController.switchToTela("/Cadastro.fxml", event);
    }

    public void switchToCadastroFidelidade(ActionEvent event) {
        NavigationController.switchToTela("/CadastroFidelidade.fxml", event);
    }

    @FXML
    public void switchToPecas(ActionEvent event) {
        NavigationController.switchToTela("/Pecas.fxml", event);
    }

    public void switchToSobre(ActionEvent event) {
        NavigationController.switchToTela("/Sobre.fxml", event);
    }

    public void switchToMeusIngressos(ActionEvent event) {
        NavigationController.switchToTela("/MeusIngressos.fxml", event);
    }

    public void switchToDetalhes(MouseEvent event) {
        NavigationController.switchToTela("/DetalhesPeca.fxml", event);
    }

    public void switchToEscolherPoltrona(ActionEvent event) {
        NavigationController.switchToTela("/EscolherPoltrona.fxml", event);
    }

    @FXML
    public void voltar(ActionEvent event) {
        NavigationController.voltar(event);
    }
}

