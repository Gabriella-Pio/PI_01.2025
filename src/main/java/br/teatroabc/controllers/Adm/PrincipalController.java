package br.teatroabc.controllers.Adm;

import br.teatroabc.utils.EstatisticasService;
import com.opencsv.exceptions.CsvException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public class PrincipalController {

    @FXML
    private TableView<ResumoEstatistica> tabelaResumoArea;

    @FXML
    private TableColumn<ResumoEstatistica, String> nomeArea;

    @FXML
    private TableColumn<ResumoEstatistica, Double> receita;

    @FXML
    private TableView<ResumoEstatistica> tabelaResumoPeca;

    @FXML
    private TableColumn<ResumoEstatistica, String> nomePeca;

    @FXML
    private TableColumn<ResumoEstatistica, Double> lucroMedio;

    @FXML
    private TableView<ResumoEstatistica> tabelaResumoCliente;

    @FXML
    private TableColumn<ResumoEstatistica, String> cpfCliente;

    @FXML
    private TableColumn<ResumoEstatistica, Double> ticketMedio;

    private EstatisticasService estatisticasService;

    @FXML
    public void initialize() {
        configurarTabelas();
        carregarDados();
    }

    private void configurarTabelas() {
        // Configurando as colunas de Área
        nomeArea.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        receita.setCellValueFactory(new PropertyValueFactory<>("valor"));
        receita.setCellFactory(column -> new TableCell<ResumoEstatistica, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item)); // Formata para duas casas decimais
                }
            }
        });

        // Configurando as colunas de Peça
        nomePeca.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        lucroMedio.setCellValueFactory(new PropertyValueFactory<>("valor"));
        lucroMedio.setCellFactory(column -> new TableCell<ResumoEstatistica, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item)); // Formata para duas casas decimais
                }
            }
        });

        // Configurando as colunas de Cliente
        cpfCliente.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        ticketMedio.setCellValueFactory(new PropertyValueFactory<>("valor"));
        ticketMedio.setCellFactory(column -> new TableCell<ResumoEstatistica, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item)); // Formata para duas casas decimais
                }
            }
        });
    }

    private void carregarDados() {
        try {
            String clienteCSVPath = Paths.get("data/BD/Cliente.csv").toAbsolutePath().toString();
            String vendaCSVPath = Paths.get("data/BD/Venda.csv").toAbsolutePath().toString();
            String itemVendaCSVPath = Paths.get("data/BD/ItemVenda.csv").toAbsolutePath().toString();

            estatisticasService = new EstatisticasService(clienteCSVPath, vendaCSVPath, itemVendaCSVPath);

            atualizarDados();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            // Handle exceptions (e.g., display error to the user)
        }
    }

    private void atualizarDados() {
        if (estatisticasService == null) return;

        // Receita média por área
        Map<String, Double> receitaPorArea = estatisticasService.getReceitaMediaPorArea();
        ObservableList<ResumoEstatistica> dadosArea = receitaPorArea.entrySet().stream()
                .map(entry -> new ResumoEstatistica(entry.getKey(), entry.getValue()))
                .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
        tabelaResumoArea.setItems(dadosArea);

        // Lucro médio por peça
        Map<String, Double> lucroPorPeca = estatisticasService.getLucroMedioPorPeca();
        ObservableList<ResumoEstatistica> dadosPeca = lucroPorPeca.entrySet().stream()
                .map(entry -> new ResumoEstatistica(entry.getKey(), entry.getValue()))
                .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
        tabelaResumoPeca.setItems(dadosPeca);

        // Ticket médio por cliente
        Map<String, Double> ticketMedio = estatisticasService.getTicketMedioPorCliente();
        ObservableList<ResumoEstatistica> dadosCliente = ticketMedio.entrySet().stream()
                .map(entry -> new ResumoEstatistica(entry.getKey(), entry.getValue()))
                .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
        tabelaResumoCliente.setItems(dadosCliente);
    }


    public static class ResumoEstatistica {
        private final String descricao;
        private final Double valor;

        public ResumoEstatistica(String descricao, Double valor) {
            this.descricao = descricao;
            this.valor = valor;
        }

        public String getDescricao() {
            return descricao;
        }

        public Double getValor() {
            return valor;
        }
    }
}
