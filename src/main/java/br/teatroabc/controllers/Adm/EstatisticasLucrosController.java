package br.teatroabc.controllers.Adm;

import br.teatroabc.Models.ItemVenda;
import br.teatroabc.utils.EstatisticasService;
import com.opencsv.exceptions.CsvException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class EstatisticasLucrosController {

    @FXML
    private Label labelPecaMaisLucrativa;
    @FXML
    private Label labelSessaoMaisLucrativa;
    @FXML
    private BarChart<String, Number> graficoLucros;
    @FXML
    private CategoryAxis eixoX;
    @FXML
    private NumberAxis eixoY;
    @FXML
    private TableView<LucroEstatistica> tabelaLucros;
    @FXML
    private TableColumn<LucroEstatistica, String> colunaPeca;
    @FXML
    private TableColumn<LucroEstatistica, String> colunaSessao;
    @FXML
    private TableColumn<LucroEstatistica, String> colunaLucro;

    private EstatisticasService estatisticasService;
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @FXML
    public void initialize() {
        // Configure table columns
        colunaPeca.setCellValueFactory(new PropertyValueFactory<>("peca"));
        colunaSessao.setCellValueFactory(new PropertyValueFactory<>("sessao"));
        colunaLucro.setCellValueFactory(new PropertyValueFactory<>("lucroFormatado"));

        // Configure chart axes
        eixoX.setLabel("Peças");
        eixoY.setLabel("Lucro Total (R$)");

        carregarDados();
    }

    private void carregarDados() {
        try {
            String clienteCSVPath = Paths.get("data/BD/Cliente.csv").toAbsolutePath().toString();
            String vendaCSVPath = Paths.get("data/BD/Venda.csv").toAbsolutePath().toString();
            String itemVendaCSVPath = Paths.get("data/BD/ItemVenda.csv").toAbsolutePath().toString();

            estatisticasService = new EstatisticasService(clienteCSVPath, vendaCSVPath, itemVendaCSVPath);
            atualizarEstatisticas();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            // Handle error (show alert to user)
        }
    }

    private void atualizarEstatisticas() {
        if (estatisticasService == null) return;

        // Get revenue by play
        Map<String, Double> receitaPorPeca = estatisticasService.getReceitaPorPeca();

        // Get revenue by session (date + play)
        Map<String, Double> receitaPorSessao = estatisticasService.getItensVenda().stream()
                .collect(Collectors.groupingBy(
                        item -> item.getTurnoSessao() + " - " + item.getPecaId(),
                        Collectors.summingDouble(item -> estatisticasService.PRECO_POR_AREA.getOrDefault(item.getAreaId(), 0.0))
                ));

        // Find most profitable play
        Optional<Map.Entry<String, Double>> pecaMaisLucrativa = receitaPorPeca.entrySet()
                .stream().max(Map.Entry.comparingByValue());

        // Find most profitable session
        Optional<Map.Entry<String, Double>> sessaoMaisLucrativa = receitaPorSessao.entrySet()
                .stream().max(Map.Entry.comparingByValue());

        // Update labels
        pecaMaisLucrativa.ifPresent(entry ->
                labelPecaMaisLucrativa.setText(entry.getKey() + ": " + currencyFormat.format(entry.getValue())));

        sessaoMaisLucrativa.ifPresent(entry -> {
            String[] parts = entry.getKey().split(" - ");
            labelSessaoMaisLucrativa.setText(parts[1] + " (" + parts[0] + "): " + currencyFormat.format(entry.getValue()));
        });

        // Update chart with plays revenue
        XYChart.Series<String, Number> serieLucros = new XYChart.Series<>();
        serieLucros.setName("Lucro por Peça");

        receitaPorPeca.forEach((peca, lucro) ->
                serieLucros.getData().add(new XYChart.Data<>(peca, lucro)));

        graficoLucros.getData().clear();
        graficoLucros.getData().add(serieLucros);

        // Update table with session revenue details
        ObservableList<LucroEstatistica> dadosTabela = FXCollections.observableArrayList();

        receitaPorSessao.forEach((sessaoKey, lucro) -> {
            String[] parts = sessaoKey.split(" - ");
            String data = parts.length > 0 ? parts[0] : "";
            String peca = parts.length > 1 ? parts[1] : "";

            dadosTabela.add(new LucroEstatistica(peca, data, lucro, currencyFormat));
        });

        tabelaLucros.setItems(dadosTabela);
    }

    public static class LucroEstatistica {
        private final String peca;
        private final String sessao;
        private final double lucro;
        private final String lucroFormatado;

        public LucroEstatistica(String peca, String sessao, double lucro, NumberFormat format) {
            this.peca = peca;
            this.sessao = sessao;
            this.lucro = lucro;
            this.lucroFormatado = format.format(lucro);
        }

        public String getPeca() {
            return peca;
        }

        public String getSessao() {
            return sessao;
        }

        public double getLucro() {
            return lucro;
        }

        public String getLucroFormatado() {
            return lucroFormatado;
        }
    }
}