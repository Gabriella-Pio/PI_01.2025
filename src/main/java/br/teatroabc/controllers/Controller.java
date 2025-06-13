package br.teatroabc.controllers;

import br.teatroabc.controllers.NavigationController;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;
import javafx.fxml.FXML;

import java.time.LocalDate;

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

    @FXML
    private TextField txtCPF;

    @FXML
    private DatePicker dateNascimento;

    @FXML
    private void confirmarCompra(ActionEvent event) {
        String cpf = txtCPF.getText();
        LocalDate dataNascimento = dateNascimento.getValue();

        if (cpf == null || cpf.isEmpty() || dataNascimento == null) {
            // Exibe alerta de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro na Compra");
            alert.setHeaderText("Dados Obrigatórios Não Preenchidos");
            alert.setContentText("Por favor, informe o CPF e a Data de Nascimento para concluir a compra.");
            alert.showAndWait();
        } else {
            // Exibe confirmação de compra
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Compra Confirmada");
            alert.setHeaderText("Compra Realizada com Sucesso!");
            alert.setContentText("Obrigado por adquirir seus ingressos! Divirta-se no Teatro ABC.");
            alert.showAndWait();
        }
    }

}

