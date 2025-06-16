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
import java.util.*;
import java.util.stream.Collectors;

public class EstatisticasSessoesController {

    @FXML
    private Label labelMaiorOcupacao;

    @FXML
    private Label labelMenorOcupacao;

    @FXML
    private BarChart<String, Number> graficoOcupacao;

    @FXML
    private CategoryAxis eixoX;

    @FXML
    private NumberAxis eixoY;

    @FXML
    private TableView<SessaoEstatistica> tabelaResumo;

    @FXML
    private TableColumn<SessaoEstatistica, String> colunaSessao;

    @FXML
    private TableColumn<SessaoEstatistica, String> colunaHorario;

    @FXML
    private TableColumn<SessaoEstatistica, Integer> colunaOcupacao;

    private EstatisticasService estatisticasService;
    private static final int TOTAL_POLTRONAS = 250;

    @FXML
    public void initialize() {
        // Configure table columns
        colunaSessao.setCellValueFactory(new PropertyValueFactory<>("peca"));
        colunaHorario.setCellValueFactory(new PropertyValueFactory<>("data"));
        colunaOcupacao.setCellValueFactory(new PropertyValueFactory<>("ocupacao"));

        // Configure chart axes
        eixoX.setLabel("Sessões");
        eixoY.setLabel("Porcentagem de Ocupação (%)");

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

        // Group items by session (date + play)
        Map<String, List<ItemVenda>> sessoesMap = estatisticasService.getItensVenda().stream()
                .collect(Collectors.groupingBy(item -> item.getTurnoSessao() + " - " + item.getPecaId()));

        // Calculate statistics for each session
        ObservableList<SessaoEstatistica> sessoes = FXCollections.observableArrayList();
        Map<String, Integer> ocupacaoMap = new HashMap<>();

        for (Map.Entry<String, List<ItemVenda>> entry : sessoesMap.entrySet()) {
            String sessaoKey = entry.getKey();
            int vendidos = entry.getValue().size();
            int porcentagemOcupacao = (int) Math.round((vendidos * 100.0) / TOTAL_POLTRONAS);

            String[] parts = sessaoKey.split(" - ");
            String data = parts.length > 0 ? parts[0] : "";
            String peca = parts.length > 1 ? parts[1] : "";

            sessoes.add(new SessaoEstatistica(peca, data, porcentagemOcupacao));
            ocupacaoMap.put(sessaoKey, porcentagemOcupacao);
        }

        // Update table
        tabelaResumo.setItems(sessoes);

        // Update chart
        XYChart.Series<String, Number> serieOcupacao = new XYChart.Series<>();
        serieOcupacao.setName("Ocupação");

        for (SessaoEstatistica sessao : sessoes) {
            serieOcupacao.getData().add(new XYChart.Data<>(
                    sessao.getPeca() + "\n" + sessao.getData(),
                    sessao.getOcupacao()
            ));
        }

        graficoOcupacao.getData().clear();
        graficoOcupacao.getData().add(serieOcupacao);

        // Update highest/lowest occupancy labels
        Optional<Map.Entry<String, Integer>> maxEntry = ocupacaoMap.entrySet()
                .stream().max(Map.Entry.comparingByValue());
        Optional<Map.Entry<String, Integer>> minEntry = ocupacaoMap.entrySet()
                .stream().min(Map.Entry.comparingByValue());

        maxEntry.ifPresent(entry -> {
            String[] parts = entry.getKey().split(" - ");
            labelMaiorOcupacao.setText(parts[1] + " (" + parts[0] + "): " + entry.getValue() + "%");
        });

        minEntry.ifPresent(entry -> {
            String[] parts = entry.getKey().split(" - ");
            labelMenorOcupacao.setText(parts[1] + " (" + parts[0] + "): " + entry.getValue() + "%");
        });
    }

    public static class SessaoEstatistica {
        private final String peca;
        private final String data;
        private final int ocupacao;

        public SessaoEstatistica(String peca, String data, int ocupacao) {
            this.peca = peca;
            this.data = data;
            this.ocupacao = ocupacao;
        }

        public String getPeca() {
            return peca;
        }

        public String getData() {
            return data;
        }

        public int getOcupacao() {
            return ocupacao;
        }
    }
}