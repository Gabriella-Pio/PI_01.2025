package br.teatroabc.controllers;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class NavigationController {
    private static final Stack<Scene> screenHistory = new Stack<>();
    private static final int HISTORY_LIMIT = 10;

    public static void saveCurrentScene(Scene currentScene) {
        if (screenHistory.size() >= HISTORY_LIMIT) {
            screenHistory.remove(0);
        }
        screenHistory.push(currentScene);
    }

    public static void voltar(Event event) {
        if (!screenHistory.isEmpty()) {
            Scene previousScene = screenHistory.pop();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(previousScene);
            stage.show();
        } else {
            return;
        }
    }

    public static void switchToTela(String fxmlPath, Event event) {
        switchToTela(fxmlPath, event, null, false);
    }

    public static <T> void switchToTela(String fxmlPath, Event event, Consumer<T> controllerConsumer) {
        switchToTela(fxmlPath, event, controllerConsumer, false);
    }

    public static <T> void switchToTela(String fxmlPath, Event event, Consumer<T> controllerConsumer, boolean fullScreen) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationController.class.getResource(fxmlPath));
            Parent root = loader.load();

            T controller = loader.getController();
            if (controllerConsumer != null) {
                controllerConsumer.accept(controller);
            }

            Scene currentScene = ((Node) event.getSource()).getScene();
            saveCurrentScene(currentScene);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setFullScreen(fullScreen);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
