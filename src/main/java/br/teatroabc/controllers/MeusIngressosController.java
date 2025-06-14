package br.teatroabc.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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
}
