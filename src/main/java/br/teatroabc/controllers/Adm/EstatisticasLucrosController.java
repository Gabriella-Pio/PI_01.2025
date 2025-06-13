package br.teatroabc.controllers.Adm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EstatisticasLucrosController {

    @FXML
    private Label labelPecaMaisLucrativa;

    @FXML
    private Label labelSessaoMaisLucrativa;

    @FXML
    private BarChart<String, Number> graficoLucros;

    @FXML
    private TableView<Lucro> tabelaLucros;

    @FXML
    private TableColumn<Lucro, String> colunaPeca;

    @FXML
    private TableColumn<Lucro, String> colunaSessao;

    @FXML
    private TableColumn<Lucro, Double> colunaLucro;

    public void initialize() {
        // Dados de exemplo
        ObservableList<Lucro> lucros = FXCollections.observableArrayList(
                new Lucro("Peça 1", "Sessão 1", 5000.00),
                new Lucro("Peça 2", "Sessão 2", 10000.00),
                new Lucro("Peça 3", "Sessão 3", 7500.00)
        );

        // Preencher tabela
        colunaPeca.setCellValueFactory(new PropertyValueFactory<>("peca"));
        colunaSessao.setCellValueFactory(new PropertyValueFactory<>("sessao"));
        colunaLucro.setCellValueFactory(new PropertyValueFactory<>("lucro"));
        tabelaLucros.setItems(lucros);

        // Preencher gráfico
        XYChart.Series<String, Number> serieLucros = new XYChart.Series<>();
        for (Lucro lucro : lucros) {
            serieLucros.getData().add(new XYChart.Data<>(lucro.getPeca(), lucro.getLucro()));
        }
        graficoLucros.getData().add(serieLucros);

        // Definir peça e sessão mais lucrativa
        Lucro maisLucrativa = lucros.stream().max((l1, l2) -> Double.compare(l1.getLucro(), l2.getLucro())).orElse(null);

        if (maisLucrativa != null) {
            labelPecaMaisLucrativa.setText(maisLucrativa.getPeca() + ": R$ " + maisLucrativa.getLucro());
            labelSessaoMaisLucrativa.setText(maisLucrativa.getSessao() + ": R$ " + maisLucrativa.getLucro());
        }
    }

    // Classe auxiliar para representar os dados de lucro
    public static class Lucro {
        private final String peca;
        private final String sessao;
        private final double lucro;

        public Lucro(String peca, String sessao, double lucro) {
            this.peca = peca;
            this.sessao = sessao;
            this.lucro = lucro;
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
    }
}
