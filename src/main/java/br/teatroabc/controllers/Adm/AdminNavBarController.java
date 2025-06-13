package br.teatroabc.controllers.Adm;

import br.teatroabc.controllers.NavigationController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.Stack;

import static br.teatroabc.controllers.NavigationController.switchToTela;

public class AdminNavBarController {
    static final Stack<Scene> screenHistory = new Stack<>();

    /**
     * Volta para a cena anterior no histórico.
     */
    public  void voltar(Event event) {
        if (!screenHistory.isEmpty()) {
            Scene previousScene = screenHistory.pop();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(previousScene);
            stage.show();
        } else {
            System.out.println("Nenhuma tela anterior encontrada.");
        }
    }

    public void switchToEstatisticasPecas(ActionEvent event) {
        switchToTela("/Adm/EstatisticasPecas.fxml", event);
    }

    public void switchToEstatisticasSessoes(ActionEvent event) {
        switchToTela("/Adm/EstatisticasSessoes.fxml", event);
    }

    public void switchToEstatisticasLucros(ActionEvent event) {
        switchToTela("/Adm/EstatisticasLucros.fxml", event);
    }

    public void switchToInicial(ActionEvent event) {
        switchToTela("/TelaInicial.fxml", event);
    }
}
