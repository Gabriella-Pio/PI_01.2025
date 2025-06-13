package br.teatroabc.controllers.Adm;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public class EstatisticasPecasController {

    @FXML
    private BarChart<String, Number> graficoVendasPorPeca;

    @FXML
    private Label pecaMaisVendidaLabel;

    @FXML
    private Label pecaMenosVendidaLabel;

    public void initialize() {
        carregarDadosVendasPorPeca();
    }

    private void carregarDadosVendasPorPeca() {
        // Dados simulados
        String[] pecas = {"Peça A", "Peça B", "Peça C"};
        int[] ingressosVendidos = {120, 80, 200};

        // Adiciona dados ao gráfico
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < pecas.length; i++) {
            series.getData().add(new XYChart.Data<>(pecas[i], ingressosVendidos[i]));
        }
        graficoVendasPorPeca.getData().add(series);

        // Calcula e exibe indicadores
        int maxIndex = 0, minIndex = 0;
        for (int i = 1; i < ingressosVendidos.length; i++) {
            if (ingressosVendidos[i] > ingressosVendidos[maxIndex]) maxIndex = i;
            if (ingressosVendidos[i] < ingressosVendidos[minIndex]) minIndex = i;
        }
        pecaMaisVendidaLabel.setText("Peça mais vendida: " + pecas[maxIndex]);
        pecaMenosVendidaLabel.setText("Peça menos vendida: " + pecas[minIndex]);
    }
}
