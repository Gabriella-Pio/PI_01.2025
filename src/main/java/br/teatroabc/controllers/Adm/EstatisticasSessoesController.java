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

public class EstatisticasSessoesController {

    @FXML
    private Label labelMaiorOcupacao;

    @FXML
    private Label labelMenorOcupacao;

    @FXML
    private BarChart<String, Number> graficoOcupacao;

    @FXML
    private TableView<Sessao> tabelaResumo;

    @FXML
    private TableColumn<Sessao, String> colunaSessao;

    @FXML
    private TableColumn<Sessao, String> colunaHorario;

    @FXML
    private TableColumn<Sessao, Integer> colunaOcupacao;

    public void initialize() {
        // Dados de exemplo
        ObservableList<Sessao> sessoes = FXCollections.observableArrayList(
                new Sessao("Sessão 1", "10h", 90),
                new Sessao("Sessão 2", "14h", 50),
                new Sessao("Sessão 3", "19h", 20)
        );

        // Preencher tabela
        colunaSessao.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        colunaOcupacao.setCellValueFactory(new PropertyValueFactory<>("ocupacao"));
        tabelaResumo.setItems(sessoes);

        // Preencher gráfico
        XYChart.Series<String, Number> serieOcupacao = new XYChart.Series<>();
        for (Sessao sessao : sessoes) {
            serieOcupacao.getData().add(new XYChart.Data<>(sessao.getNome(), sessao.getOcupacao()));
        }
        graficoOcupacao.getData().add(serieOcupacao);

        // Definir maior e menor ocupação
        Sessao maior = sessoes.stream().max((s1, s2) -> Integer.compare(s1.getOcupacao(), s2.getOcupacao())).orElse(null);
        Sessao menor = sessoes.stream().min((s1, s2) -> Integer.compare(s1.getOcupacao(), s2.getOcupacao())).orElse(null);

        if (maior != null) {
            labelMaiorOcupacao.setText(maior.getNome() + ": " + maior.getOcupacao() + "%");
        }
        if (menor != null) {
            labelMenorOcupacao.setText(menor.getNome() + ": " + menor.getOcupacao() + "%");
        }
    }

    // Classe auxiliar para representar os dados das sessões
    public static class Sessao {
        private final String nome;
        private final String horario;
        private final int ocupacao;

        public Sessao(String nome, String horario, int ocupacao) {
            this.nome = nome;
            this.horario = horario;
            this.ocupacao = ocupacao;
        }

        public String getNome() {
            return nome;
        }

        public String getHorario() {
            return horario;
        }

        public int getOcupacao() {
            return ocupacao;
        }
    }
}
