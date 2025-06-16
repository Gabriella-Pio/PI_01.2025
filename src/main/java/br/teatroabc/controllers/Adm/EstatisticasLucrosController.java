package br.teatroabc.controllers.Adm;

import br.teatroabc.utils.CSVUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

   static class Ticket {
        String poltronaId;
        String areaId;
        String sessaoId;
        String pecaId;
        String vendaId;

        public Ticket(String poltronaId, String areaId, String sessaoId, String pecaId, String vendaId) {
            this.poltronaId = poltronaId;
            this.areaId = areaId;
            this.sessaoId = sessaoId;
            this.pecaId = pecaId;
            this.vendaId = vendaId;
        }
    }

//    public static void main(String[] args) {
//        String filePath = "path/to/your/file.csv";
//
//
//        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
//            List<String[]> rows = reader.readAll();
//            rows.stream().skip(1).forEach(row -> tickets.add(new Ticket(row[1], row[2], row[3], row[4], row[5])));
//        } catch (IOException | CsvException e) {
//            e.printStackTrace();
//            return;
//        }

    public void initialize() throws IOException {
        List<Ticket> tickets = new ArrayList<>();
        Map<String, Double> areaPrices = Map.of(
                "PA", 40.0, // Plateia A
                "PB", 60.0, // Plateia B
                "FC", 120.0, // Frisa Central
                "FB", 120.0, // Frisa Balcão
                "CD", 80.0, // Camarote
                "BN", 250.0 // Balcão Nobre
        );

//        List Linhas = CSVUtils.readCSV(0);
//        Linhas.stream().skip(1).forEach(row -> tickets.add(new Ticket(row[1], row[2], row[3], row[4], row[5])));

        // Total tickets by piece
        Map<String, Long> ticketsByPeca = tickets.stream()
                .collect(Collectors.groupingBy(ticket -> ticket.pecaId, Collectors.counting()));

        // Most and least tickets by piece
        String mostSoldPeca = ticketsByPeca.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
        String leastSoldPeca = ticketsByPeca.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();

        // Tickets by session
        Map<String, Long> ticketsBySessao = tickets.stream()
                .collect(Collectors.groupingBy(ticket -> ticket.sessaoId, Collectors.counting()));

        // Most and least occupied sessions
        String mostOccupiedSession = ticketsBySessao.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
        String leastOccupiedSession = ticketsBySessao.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();

        // Revenue by piece
        Map<String, Double> revenueByPeca = tickets.stream()
                .collect(Collectors.groupingBy(ticket -> ticket.pecaId,
                        Collectors.summingDouble(ticket -> areaPrices.getOrDefault(ticket.areaId, 0.0))));

        // Revenue by session
        Map<String, Double> revenueBySessao = tickets.stream()
                .collect(Collectors.groupingBy(ticket -> ticket.sessaoId,
                        Collectors.summingDouble(ticket -> areaPrices.getOrDefault(ticket.areaId, 0.0))));

        // Average revenue per piece
        double averageRevenuePerPiece = revenueByPeca.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        // Total and average revenue by area
        Map<String, Double> revenueByArea = tickets.stream()
                .collect(Collectors.groupingBy(ticket -> ticket.areaId,
                        Collectors.summingDouble(ticket -> areaPrices.getOrDefault(ticket.areaId, 0.0))));
        double averageRevenueByArea = revenueByArea.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        // Average ticket value per customer
        long totalTicketsSold = tickets.size();
        double totalRevenue = tickets.stream().mapToDouble(ticket -> areaPrices.getOrDefault(ticket.areaId, 0.0)).sum();
        double averageTicketValue = totalTicketsSold == 0 ? 0 : totalRevenue / totalTicketsSold;


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
