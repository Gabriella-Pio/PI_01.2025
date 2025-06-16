package br.teatroabc.controllers.Adm;


import br.teatroabc.Models.Cliente;
import br.teatroabc.Models.ItemVenda;
import br.teatroabc.Models.Venda;
import br.teatroabc.utils.CSVUtils;
import br.teatroabc.utils.EstatisticasService;
import com.opencsv.exceptions.CsvException;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


// Peça mais / menos vendida
// Peça mais / menos lucrativa - total

public class EstatisticasPecasController {

    @FXML
    private BarChart<String, Number> graficoVendasPorPeca;

    @FXML
    private Label pecaMaisVendidaLabel;

    @FXML
    private Label pecaMenosVendidaLabel;

    private EstatisticasService estatisticasService;

    public void initialize() {
        carregarDados();
        carregarDadosVendasPorPeca();
    }


    private void carregarDados() {
        try {

            String clientes = Paths.get("data/BD/Cliente.csv").toAbsolutePath().toString();
            String vendas = Paths.get("data/BD/Venda.csv").toAbsolutePath().toString();
            String itensVendas = Paths.get("data/BD/ItemVenda.csv").toAbsolutePath().toString();

            // Inicializa o serviço de estatísticas
            estatisticasService = new EstatisticasService(clientes, vendas, itensVendas);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            // Tratamento de erro ao carregar arquivos
        }
    }

    private void carregarDadosVendasPorPeca() {
        if (estatisticasService == null) {
            return; // Serviço não inicializado
        }

        // Obtém as estatísticas
        Map<String, Long> ingressosPorPeca = estatisticasService.getIngressosPorPeca();
        String pecaMaisVendida = estatisticasService.getPecaMaisVendida();
        String pecaMenosVendida = estatisticasService.getPecaMenosVendida();

        // Preenche o gráfico
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ingressos Vendidos");

        for (Map.Entry<String, Long> entry : ingressosPorPeca.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        graficoVendasPorPeca.getData().clear(); // Limpa dados anteriores
        graficoVendasPorPeca.getData().add(series);

        // Atualiza os indicadores
        pecaMaisVendidaLabel.setText("Peça mais vendida: " + pecaMaisVendida);
        pecaMenosVendidaLabel.setText("Peça menos vendida: " + pecaMenosVendida);
    }
}
