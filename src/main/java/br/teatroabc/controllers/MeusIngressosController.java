package br.teatroabc.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class MeusIngressosController {
    @FXML
    private VBox resultBox;

    @FXML
    private ListView<String> ingressosList;

    @FXML
    private TextField cpfField;

    @FXML
    private DatePicker dateNascimento;

    @FXML
    private void initialize() {
        ingressosList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Clique duplo
                abrirDetalhesIngresso(); // Chama o método que abre os detalhes do ingresso
            }
        });
    }

    @FXML
    private void buscarIngressos() {
        String cpf = cpfField.getText();
        LocalDate data = dateNascimento.getValue();

        if (cpf.isEmpty() || data == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos obrigatórios");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, preencha todos os campos.");
            alert.showAndWait();
            return;
        }

        // Simulação de busca
        List<String> ingressos = buscarIngressosPorCPFData(cpf, data);

        if (ingressos.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nenhum ingresso encontrado");
            alert.setHeaderText(null);
            alert.setContentText("Não encontramos ingressos para os dados fornecidos.");
            alert.showAndWait();
        } else {
            resultBox.setVisible(true);
            ingressosList.getItems().setAll(ingressos);
        }
    }

    private List<String> buscarIngressosPorCPFData(String cpf, LocalDate data) {
        // Substitua pelo método de busca real no banco de dados
        return List.of("Peça 1 - 20/06/2025", "Peça 2 - 25/06/2025");
    }

    @FXML
    private void abrirDetalhesIngresso() {
        String selectedIngresso = ingressosList.getSelectionModel().getSelectedItem();
        if (selectedIngresso != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetalhesIngresso.fxml"));
                VBox root = loader.load();

                DetalhesIngressoController controller = loader.getController();
                // Simulação de dados - substitua com os dados reais do ingresso
                controller.setDetalhes(
                        "Romeu e Julieta",
                        "20/06/2025",
                        "Noite",
                        "Plateia",
                        "A12"
                );

                Stage stage = new Stage();
                stage.setTitle("Detalhes do Ingresso");
                stage.setScene(new Scene(root, 400, 600));
                stage.initModality(Modality.APPLICATION_MODAL); // Janela modal
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum ingresso selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um ingresso para ver os detalhes.");
            alert.showAndWait();
        }
    }

}
