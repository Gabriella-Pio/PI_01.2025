package br.teatroabc.controllers;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class NavigationController {
    static final Stack<Scene> screenHistory = new Stack<>();

    /**
     * Salva a cena atual no histórico.
     */
    public static void saveCurrentScene(Scene currentScene) {
        screenHistory.push(currentScene);
    }

    /**
     * Volta para a cena anterior no histórico.
     */
    public static void voltar(Event event) {
        if (!screenHistory.isEmpty()) {
            Scene previousScene = screenHistory.pop();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(previousScene);
            stage.show();
        } else {
            System.out.println("Nenhuma tela anterior encontrada.");
        }
    }

    /**
     * Alterna para uma nova tela, salvando a cena atual no histórico.
     */
    public static void switchToTela(String fxmlPath, Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationController.class.getResource(fxmlPath));
            Parent root = loader.load();

            // Salva a cena atual no histórico
            Scene currentScene = ((Node) event.getSource()).getScene();
            saveCurrentScene(currentScene);

            // Configura a nova cena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
//            stage.setFullScreen(true); // Força tela cheia
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public static void switchToTela(String fxmlPath, Event event, String[] args) {
public static void switchToTela(String fxmlPath, Event event, String[] args) {
    try {
            FXMLLoader loader = new FXMLLoader(NavigationController.class.getResource(fxmlPath));
            Parent root = loader.load();

            // Salva a cena atual no histórico
            Scene currentScene = ((Node) event.getSource()).getScene();
            saveCurrentScene(currentScene);

            // Configura a nova cena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
//            stage.setFullScreen(true); // Força tela cheia
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
