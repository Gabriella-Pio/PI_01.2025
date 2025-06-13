package br.teatroabc.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FidelidadeController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCPF;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtEndereco;

    @FXML
    private DatePicker dateNascimento;

    @FXML
    private VBox mainBox;

    // Simulação de banco de dados para armazenar cadastros
    private static final Map<String, String> bancoFidelidade = new HashMap<>();

    @FXML
    private void cadastrarCliente(ActionEvent event) {
        String nome = txtNome.getText();
        String cpf = txtCPF.getText();
        String telefone = txtTelefone.getText();
        String endereco = txtEndereco.getText();
        LocalDate nascimento = dateNascimento.getValue();

        if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || endereco.isEmpty() || nascimento == null) {
            exibirMensagem("Erro de Cadastro", "Por favor, preencha todos os campos.", Alert.AlertType.WARNING);
            return;
        }

        // Verifica se o CPF já está cadastrado
        if (bancoFidelidade.containsKey(cpf)) {
            exibirMensagem("CPF já cadastrado", "O CPF informado já está registrado no sistema.", Alert.AlertType.INFORMATION);
        } else {
            // Salva o cliente no banco simulado
            bancoFidelidade.put(cpf, nome);
            exibirMensagem("Sucesso", "Cadastro realizado com sucesso!", Alert.AlertType.INFORMATION);

            // Limpa os campos após o cadastro
            limparCampos();
        }
    }

    private void exibirMensagem(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void limparCampos() {
        txtNome.clear();
        txtCPF.clear();
        txtTelefone.clear();
        txtEndereco.clear();
        dateNascimento.setValue(null);
    }
}
