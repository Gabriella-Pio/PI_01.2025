package br.teatroabc.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private void realizarLogin(ActionEvent event) {
        String usuario = txtUsuario.getText();
        String senha = txtSenha.getText();

        if ((usuario == null || usuario.isEmpty()) || (senha == null || senha.isEmpty())) {
            // Exibe alerta de erro se os campos não estiverem preenchidos
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Login");
            alert.setHeaderText("Campos Obrigatórios Não Preenchidos");
            alert.setContentText("Por favor, preencha o campo de usuário e senha antes de continuar.");
            alert.showAndWait();
        } else {
            // Se os campos estiverem preenchidos, faz a navegação
            switchToPecas(event);
        }
    }

    @FXML
    private void switchToPecas(ActionEvent event) {
        // Sua lógica de navegação para a página de Peças
        NavigationController.switchToTela("/Pecas.fxml", event);
    }

}
