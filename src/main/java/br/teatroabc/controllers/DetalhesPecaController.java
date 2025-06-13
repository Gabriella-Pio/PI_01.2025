package br.teatroabc.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

import static br.teatroabc.controllers.NavigationController.screenHistory;

public class DetalhesPecaController {

    @FXML
    private Label tituloPeca;

    @FXML
    private ImageView imagemPeca;

    @FXML
    private Label descricaoPeca;

    @FXML
    private Label horariosPeca;

    @FXML
    private ChoiceBox<String> choiceQuantidadeIngressos;


    private Stage stage;

    public void initialize() {
        // Exemplo de preenchimento inicial
        tituloPeca.setText("A Comédia da Vida Privada");
        imagemPeca.setImage(new Image(getClass().getResourceAsStream("/Img/peca1.png")));
        descricaoPeca.setText("Uma peça emocionante que retrata os desafios e as alegrias da vida cotidiana.");
    }

    public void switchToEscolherPoltrona(ActionEvent event) {
        switchToTela("/EscolherPoltrona.fxml", event);
    }

    public static void saveCurrentScene(Scene currentScene) {
        screenHistory.push(currentScene);
    }

    public void switchToTela(String fxmlPath, Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Passar a quantidade de ingressos para o próximo controlador
            EscolherPoltronaController controller = loader.getController();
            String selectedIngressos = choiceQuantidadeIngressos.getValue();

            if (selectedIngressos != null) {
                controller.setQuantidadeIngressos(Integer.parseInt(selectedIngressos));
            }

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