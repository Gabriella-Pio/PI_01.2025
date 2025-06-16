package br.teatroabc.controllers;

import br.teatroabc.Models.Cliente;
import br.teatroabc.Models.ItemVenda;
import br.teatroabc.Models.Venda;
import br.teatroabc.utils.CSVUtils;
import br.teatroabc.utils.EstatisticasService;
import br.teatroabc.utils.GeneralUse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MeusIngressosController {

    @FXML private TextField cpfField;
    @FXML private DatePicker dateNascimento;
    @FXML private Button btnBuscarIngressos;
    @FXML private VBox searchBox;
    @FXML private VBox resultBox;
    @FXML private ListView<String> ingressosList;

    private final Map<String, ItemVenda> ticketMap = new HashMap<>();

    @FXML
    private void initialize() {
        // Initialize date format
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

        // Add click listener to the list view
        ingressosList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                openTicketDetails(newVal);
            }
        });
    }

    private void openTicketDetails(String ticketInfo) {
        try {
            ItemVenda ingresso = ticketMap.get(ticketInfo);
            if (ingresso == null) return;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetalhesIngresso.fxml"));
            Parent root = loader.load();

            DetalhesIngressoController controller = loader.getController();
            controller.setTicketDetails(
                    ingresso.getPecaId(),
                    ingresso.getDataSessao(),
                    ingresso.getTurnoSessao(),
                    ingresso.getAreaId(),
                    ingresso.getPoltronaId()
            );

            Stage stage = new Stage();
            stage.setTitle("Detalhes do Ingresso");
            stage.setScene(new Scene(root));
            stage.setWidth(400);  // Width in pixels
            stage.setHeight(500); // Height in pixels

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erro", "Não foi possível abrir os detalhes do ingresso.");
        }
    }

    @FXML
    private void buscarIngressos() {
        String cpf = cpfField.getText().trim();
        LocalDate dataNasc = dateNascimento.getValue();

        // Validate inputs
        if (cpf.isEmpty() || dataNasc == null) {
            showAlert("Erro", "Por favor, preencha todos os campos.");
            return;
        }

        if (!GeneralUse.isCPFValid(cpf)) {
            showAlert("Erro", "CPF inválido.");
            return;
        }

        try {
            // Clear previous results
            ticketMap.clear();

            // Load data from CSVs
            EstatisticasService estatisticas = new EstatisticasService(
                    "data/BD/Cliente.csv",
                    "data/BD/Venda.csv",
                    "data/BD/ItemVenda.csv"
            );

            // Find client by CPF
            List<Cliente> clientes = estatisticas.getClientes();
            Cliente cliente = clientes.stream()
                    .filter(c -> c.getCPF().equals(cpf))
                    .findFirst()
                    .orElse(null);

            if (cliente == null) {
                showAlert("Erro", "Nenhum cliente encontrado com este CPF.");
                return;
            }

            // Verify birth date
            try {
                Object storedBirthDate = cliente.getDataNasc();
                LocalDate storedDate;

                if (storedBirthDate instanceof String) {
                    storedDate = LocalDate.parse((String) storedBirthDate,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } else if (storedBirthDate instanceof LocalDate) {
                    storedDate = (LocalDate) storedBirthDate;
                } else {
                    throw new IllegalArgumentException("Tipo de data inválido no cadastro");
                }

                if (!storedDate.isEqual(dataNasc)) {
                    showAlert("Erro", "Data de nascimento não corresponde ao CPF informado.");
                    return;
                }
            } catch (Exception e) {
                showAlert("Erro", "Erro ao verificar data de nascimento: " + e.getMessage());
                return;
            }

            // Find all sales for this client
            List<Venda> vendas = estatisticas.getVendas().stream()
                    .filter(v -> v.getIdCliente() == cliente.getId())
                    .collect(Collectors.toList());

            if (vendas.isEmpty()) {
                showAlert("Informação", "Nenhum ingresso encontrado para este cliente.");
                return;
            }

            // Get all ticket items for these sales
            List<ItemVenda> ingressos = estatisticas.getItensVenda().stream()
                    .filter(item -> vendas.stream().anyMatch(v -> v.getId() == item.getVendaId()))
                    .collect(Collectors.toList());

            // Format the tickets for display
            ObservableList<String> ingressosFormatados = FXCollections.observableArrayList();
            for (ItemVenda ingresso : ingressos) {
                String displayInfo = String.format("%s - %s",
                        ingresso.getPecaId(),
                        ingresso.getDataSessao());

                ingressosFormatados.add(displayInfo);
                ticketMap.put(displayInfo, ingresso);
            }

            // Display the results
            ingressosList.setItems(ingressosFormatados);
            resultBox.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erro", "Ocorreu um erro ao buscar os ingressos: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}