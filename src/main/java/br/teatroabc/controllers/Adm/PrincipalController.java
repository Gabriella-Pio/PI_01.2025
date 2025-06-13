package br.teatroabc.controllers.Adm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PrincipalController {

    private Stage stage;
    private Scene scene;

    @FXML
    public void switchToEstatisticasPecas(ActionEvent event) {
        switchToTela("/Adm/EstatisticasPecas.fxml", event);
    }

    @FXML
    public void switchToEstatisticasSessoes(ActionEvent event) {
        switchToTela("/Adm/EstatisticasSessoes.fxml", event);
    }

    @FXML
    public void switchToEstatisticasLucros(ActionEvent event) {
        switchToTela("/Adm/EstatisticasLucros.fxml", event);
    }


    private void switchToTela(String fxmlPath, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
