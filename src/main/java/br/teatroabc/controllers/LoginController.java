package br.teatroabc.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controlador para a tela de Login.
 * Gerencia a autenticação do usuário e a navegação para a tela administrativa.
 */
public class LoginController {
    @FXML
    private TextField txtUsuario; // Campo de texto para o nome de usuário

    @FXML
    private PasswordField txtSenha; // Campo de senha para a senha

    /**
     * Tenta realizar o login quando o botão correspondente é clicado.
     * Valida os campos de usuário e senha e verifica as credenciais.
     * @param event O evento de ação (clique do botão).
     */
    @FXML
    private void realizarLogin(ActionEvent event) {
        String usuario = txtUsuario.getText(); // Obtém o texto do campo de usuário
        String senha = txtSenha.getText();     // Obtém o texto do campo de senha

        // 1. Validação: Verifica se os campos estão vazios
        if ((usuario == null || usuario.isEmpty()) || (senha == null || senha.isEmpty())) {
            // Exibe um alerta de erro se algum campo não estiver preenchido
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Login");
            alert.setHeaderText("Campos Obrigatórios Não Preenchidos");
            alert.setContentText("Por favor, preencha o campo de usuário e senha antes de continuar.");
            alert.showAndWait(); // Mostra o alerta e espera a interação do usuário
            return; // Interrompe o método
        }

        // 2. Validação: Verifica as credenciais (usuário "42" e senha "CELG")
        if (!(usuario.equals("42")) || !(senha.equals("CELG"))) {
            // Exibe um alerta de erro se as credenciais estiverem incorretas
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Login");
            alert.setHeaderText("Usuário ou Senha Incorretos");
            alert.setContentText("Acesso Negado");
            alert.showAndWait(); // Mostra o alerta e espera a interação do usuário
            return; // Interrompe o método
        }

        // Se ambos os campos estiverem preenchidos e as credenciais corretas, navega para a tela de administração
        switchToAdm(event);
    }

    /**
     * Alterna para a tela principal da área administrativa (`/Adm/Principal.fxml`).
     * @param event O evento de ação que disparou a navegação.
     */
    @FXML
    private void switchToAdm(ActionEvent event) {
        NavigationController.switchToTela("/Adm/Principal.fxml", event);
    }

    /**
     * Retorna à tela anterior no histórico de navegação.
     * @param event O evento de ação que disparou a ação de voltar.
     */
    @FXML
    public void voltar(ActionEvent event) {
        NavigationController.voltar(event);
    }
}