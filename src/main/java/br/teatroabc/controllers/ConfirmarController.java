package br.teatroabc.controllers;

import br.teatroabc.Models.Venda;
import br.teatroabc.utils.GeneralUse;
import br.teatroabc.utils.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;

public class ConfirmarController {
    @FXML
    private TextField txtCPF;

    @FXML
    private DatePicker dateNascimento;

    private double total;

    @FXML
    private Label labelTotal;
    Venda venda;
    public void setVenda(){
//        Venda venda = new Venda();
    }
    public void setTotal(double total) {
        this.total = total;
        atualizarTotal();
    }

    private void atualizarTotal() {
        labelTotal.setText(String.format("R$ %.2f", total));
    }

    @FXML
    private void confirmarCompra(ActionEvent event) throws IOException {
        String cpf = txtCPF.getText();
        LocalDate dataNascimento = dateNascimento.getValue();
        if (!GeneralUse.isCPFValid(cpf)) {
            // Exibe alerta de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro na Compra");
            alert.setHeaderText("CPF inválido");
            alert.setContentText("Por favor, informe CPF Válido");
            alert.showAndWait();
            return;
        }

        if (cpf == null || cpf.isEmpty() || dataNascimento == null) {
            // Exibe alerta de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro na Compra");
            alert.setHeaderText("Dados Obrigatórios Não Preenchidos");
            alert.setContentText("Por favor, informe o CPF e a Data de Nascimento para concluir a compra.");
            alert.showAndWait();
            return;
        }
            // Exibe confirmação de compra
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Compra Confirmada");
            alert.setHeaderText("Compra Realizada com Sucesso!");
            alert.setContentText("Obrigado por adquirir seus ingressos! Divirta-se no Teatro ABC.");
            alert.showAndWait();
            State selecionadoState = Controller.getCurrentState();
            selecionadoState.getCliente().setCPF(cpf);
            selecionadoState.getCliente().setDataNasc(dataNascimento);
            Controller.setCurrentState(selecionadoState);
            State.Finish();
            NavigationController.switchToTela("/TelaInicial.fxml", event);

    }
}
