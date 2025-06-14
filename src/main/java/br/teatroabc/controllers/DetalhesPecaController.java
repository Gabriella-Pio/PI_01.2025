package br.teatroabc.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

import static br.teatroabc.controllers.NavigationController.screenHistory;

public class DetalhesPecaController {

    @FXML
    private Label tituloPeca;

    @FXML
    private ImageView imagemPeca;

    @FXML
    private Label descricaoPeca;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<String> choiceHorario;

    @FXML
    private ChoiceBox<String> choiceQuantidadeIngressos;

    private Stage stage;

    public void initialize() {
        // Exemplo de preenchimento inicial
        tituloPeca.setText("A Comédia da Vida Privada");
        imagemPeca.setImage(new Image(getClass().getResourceAsStream("/Img/pecaRJ.jpg")));
        descricaoPeca.setText("Uma peça emocionante que retrata os desafios e as alegrias da vida cotidiana.");
    }

    public void setDetalhes(String titulo, String descricao, String imagemPath) {
        tituloPeca.setText(titulo);
        descricaoPeca.setText(descricao);
        imagemPeca.setImage(new Image(getClass().getResourceAsStream(imagemPath)));
    }

    @FXML
    public void switchToEscolherPoltrona(ActionEvent event) {
        // Verifica se todas as informações obrigatórias foram preenchidas
        LocalDate selectedDate = datePicker.getValue();
        String selectedHorario = choiceHorario.getValue();
        String selectedIngressos = choiceQuantidadeIngressos.getValue();

        if (selectedDate == null || selectedHorario == null || selectedIngressos == null) {
            // Exibe alerta de erro se algum campo estiver vazio
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Informações Incompletas");
            alert.setHeaderText("Preenchimento Obrigatório");
            alert.setContentText("Por favor, preencha todos os campos obrigatórios antes de continuar.");
            alert.showAndWait();
        } else {
            // Navega para a próxima tela se tudo estiver preenchido
            switchToTela("/EscolherPoltrona.fxml", event, Integer.parseInt(selectedIngressos));
        }
    }

    private void switchToTela(String fxmlPath, Event event, int quantidadeIngressos) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Passar a quantidade de ingressos para o próximo controlador
            EscolherPoltronaController controller = loader.getController();
            controller.setQuantidadeIngressos(quantidadeIngressos);

            // Salva a cena atual no histórico
            Scene currentScene = ((Node) event.getSource()).getScene();
            saveCurrentScene(currentScene);

            // Configura a nova cena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveCurrentScene(Scene currentScene) {
        screenHistory.push(currentScene);
    }
}
