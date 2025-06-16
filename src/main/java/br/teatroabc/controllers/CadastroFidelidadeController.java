package br.teatroabc.controllers;

import br.teatroabc.Models.Cliente;
import br.teatroabc.utils.CSVUtils;
import br.teatroabc.utils.GeneralUse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CadastroFidelidadeController {

    @FXML private TextField txtNome;
    @FXML private TextField txtCPF;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEndereco;
    @FXML private DatePicker dateNascimento;
    @FXML private VBox mainBox;

    @FXML
    private void initialize() {
        // Configure date format for the DatePicker
        dateNascimento.setConverter(new javafx.util.StringConverter<>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                return date != null ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string, dateFormatter) : null;
            }
        });
    }

    @FXML
    private void cadastrarCliente(ActionEvent event) {
        String nome = txtNome.getText().trim();
        String cpf = txtCPF.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String endereco = txtEndereco.getText().trim();
        LocalDate nascimento = dateNascimento.getValue();

        // Validate inputs
        if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || endereco.isEmpty() || nascimento == null) {
            showAlert("Erro", "Por favor, preencha todos os campos.", Alert.AlertType.ERROR);
            return;
        }

        if (!GeneralUse.isCPFValid(cpf)) {
            showAlert("CPF Inválido", "O CPF informado não é válido.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Check if CPF already exists
            if (isCPFAlreadyRegistered(cpf)) {
                showAlert("CPF já cadastrado", "Este CPF já está registrado em nosso sistema.", Alert.AlertType.WARNING);
                return;
            }

            // Create new client
            Cliente cliente = new Cliente(nome, cpf, nascimento, telefone, endereco);

            // Save to CSV
            saveClientToCSV(cliente);

            // Show success message
            showAlert("Sucesso", "Cadastro realizado com sucesso!", Alert.AlertType.INFORMATION);

            // Clear fields
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erro", "Ocorreu um erro ao salvar o cadastro: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean isCPFAlreadyRegistered(String cpf) throws IOException {
        List<String[]> clientes = CSVUtils.readCSV(0); // Read Cliente.csv
        for (String[] cliente : clientes) {
            if (cliente[2].equals(cpf)) { // CPF is at index 2
                return true;
            }
        }
        return false;
    }

    private void saveClientToCSV(Cliente cliente) throws IOException {
        Path csvPath = Paths.get("data/BD/Cliente.csv");
        CSVUtils.appendCSV(csvPath.toFile(), cliente.parseToCsv(), 0);
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        txtNome.clear();
        txtCPF.clear();
        txtTelefone.clear();
        txtEndereco.clear();
        dateNascimento.setValue(null);
    }
}