package br.teatroabc.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Stack; //Estrutura de Dados

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToInicial(ActionEvent event) {
        switchToTela("/TelaInicial.fxml", event);
    }

    public void switchToCadastroLogin(ActionEvent event) {
        switchToTela("/LoginCadastro.fxml", event);
    }

    public void switchToLogin(ActionEvent event) {
        switchToTela("/Login.fxml", event);
    }

    public void switchToCadastro(ActionEvent event) {
        switchToTela("/Cadastro.fxml", event);
    }

    public void switchToCadastroFidelidade(ActionEvent event) {
        switchToTela("/CadastroFidelidade.fxml", event);
    }

    public void switchToPecas(ActionEvent event) {
        switchToTela("/Pecas.fxml", event);
    }

    public void switchToSobre(ActionEvent event) {
        switchToTela("/Sobre.fxml", event);
    }

    public void switchToMeusIngressos(ActionEvent event) {
        switchToTela("/MeusIngressos.fxml", event);
    }

    public void switchToDetalhes(MouseEvent event) {
        switchToTela("/DetalhesPeca.fxml", event);
    }

    public void switchToEscolherPoltrona(ActionEvent event) {
        switchToTela("/EscolherPoltrona.fxml", event);
    }


     static final Stack<Scene> screenHistory = new Stack<>();  //Estrutura de Dados

    public static void saveCurrentScene(Scene currentScene) {
        screenHistory.push(currentScene);
    }

    @FXML
    public void voltar(ActionEvent event) {
        if (!screenHistory.isEmpty()) {
            Scene previousScene = screenHistory.pop();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(previousScene);
            stage.show();
        } else {
            System.out.println("Nenhuma tela anterior encontrada.");
        }
    }

    public void switchToTela(String fxmlPath, Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Salva a cena atual no histórico
            Scene currentScene = ((Node) event.getSource()).getScene();
            saveCurrentScene(currentScene);

            // Configura a nova cena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); //Estrutura de Dados
        }
    }
}

