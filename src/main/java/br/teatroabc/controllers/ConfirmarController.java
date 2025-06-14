package br.teatroabc.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class ConfirmarController {
    @FXML
    private TextField txtCPF;

    @FXML
    private DatePicker dateNascimento;

    private double total;

    @FXML
    private Label labelTotal;

    public void setTotal(double total) {
        this.total = total;
        atualizarTotal();
    }

    private void atualizarTotal() {
        labelTotal.setText(String.format("R$ %.2f", total));
    }

    @FXML
    private void confirmarCompra(ActionEvent event) {
        String cpf = txtCPF.getText();
        LocalDate dataNascimento = dateNascimento.getValue();

        if (cpf == null || cpf.isEmpty() || dataNascimento == null) {
            // Exibe alerta de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro na Compra");
            alert.setHeaderText("Dados Obrigatórios Não Preenchidos");
            alert.setContentText("Por favor, informe o CPF e a Data de Nascimento para concluir a compra.");
            alert.showAndWait();
        } else {
            // Exibe confirmação de compra
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Compra Confirmada");
            alert.setHeaderText("Compra Realizada com Sucesso!");
            alert.setContentText("Obrigado por adquirir seus ingressos! Divirta-se no Teatro ABC.");
            alert.showAndWait();
        }
    }
}
