package br.teatroabc.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controlador para a barra de navegação (Navbar) da aplicação.
 * Gerencia as ações de clique nos botões da barra, delegando a navegação
 * entre as diferentes telas ao `NavigationController`.
 */
public class NavBarController {

    /**
     * Alterna para a tela inicial (`/TelaInicial.fxml`).
     * Chamado quando o botão "Inicial" (ou equivalente) é clicado na Navbar.
     * @param event O evento de ação (clique do botão).
     */
    @FXML
    public void switchToInicial(ActionEvent event) {
        NavigationController.switchToTela("/TelaInicial.fxml", event);
    }

    /**
     * Alterna para a tela de listagem de peças (`/Pecas.fxml`).
     * Chamado quando o botão "Peças" (ou equivalente) é clicado na Navbar.
     * @param event O evento de ação.
     */
    @FXML
    public void switchToPecas(ActionEvent event) {
        NavigationController.switchToTela("/Pecas.fxml", event);
    }

    /**
     * Alterna para a tela "Sobre" (`/Sobre.fxml`).
     * Chamado quando o botão "Sobre" (ou equivalente) é clicado na Navbar.
     * @param event O evento de ação.
     */
    @FXML
    public void switchToSobre(ActionEvent event) {
        NavigationController.switchToTela("/Sobre.fxml", event);
    }

    /**
     * Alterna para a tela "Meus Ingressos" (`/MeusIngressos.fxml`).
     * Chamado quando o botão "Meus Ingressos" (ou equivalente) é clicado na Navbar.
     * @param event O evento de ação.
     */
    @FXML
    public void switchToMeusIngressos(ActionEvent event) {
        NavigationController.switchToTela("/MeusIngressos.fxml", event);
    }

    /**
     * Alterna para a tela de cadastro de fidelidade (`/CadastroFidelidade.fxml`).
     * Chamado quando o botão "Cadastro Fidelidade" (ou equivalente) é clicado na Navbar.
     * @param event O evento de ação.
     */
    @FXML
    public void switchToCadastroFidelidade(ActionEvent event) {
        NavigationController.switchToTela("/CadastroFidelidade.fxml", event);
    }

    /**
     * Retorna à tela anterior no histórico de navegação.
     * Chamado quando o botão "Voltar" (ou equivalente) é clicado na Navbar.
     * @param event O evento de ação.
     */
    @FXML
    public void voltar(ActionEvent event) {
        NavigationController.voltar(event);
    }
}