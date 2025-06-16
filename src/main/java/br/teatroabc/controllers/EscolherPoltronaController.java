package br.teatroabc.controllers;

import br.teatroabc.Models.ItemVenda;
import br.teatroabc.utils.CSVUtils;
import br.teatroabc.utils.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import static br.teatroabc.controllers.NavigationController.switchToTela;

public class EscolherPoltronaController {

    @FXML
    private VBox rootLayout;

    @FXML
    private VBox plateiaASection, plateiaBSection, frisaEsquerdaSection,
            frisaDireitaSection, camaroteSection, balcaoNobreSection;

    @FXML
    private Label labelTotal;

    private int quantidadeIngressos;
    private final ArrayList<String> poltronasSelecionadas = new ArrayList<>();
    private double total = 0.0;
    private final DecimalFormat formatador = new DecimalFormat("R$ #,##0.00");

    @FXML
    public void initialize() {
        System.out.println("Initializing seat selection controller...");

        // Configure all seating sections
        configurarSecao(plateiaASection, "PA", 25, 13, 40.00);
        configurarSecao(plateiaBSection, "PB", 100, 15, 60.00);
        configurarFrisa();
        configurarCamarote();
        configurarSecao(balcaoNobreSection, "BN", 50, 18, 250.00);

        // Get current session info
        State state = Controller.getCurrentState();
        String dataSessao = state.getSessao();
        String turnoSessao = state.getTurno();

        System.out.println("Current session: " + dataSessao + " - " + turnoSessao);

        // Get and mark sold seats
        Set<String> soldSeats = getSoldSeats(dataSessao, turnoSessao);
        System.out.println("Found sold seats: " + soldSeats);
        markSoldSeats(soldSeats);
    }

    public void setQuantidadeIngressos(int quantidade) {
        this.quantidadeIngressos = quantidade;
    }

    private void configurarSecao(VBox section, String prefixo, int totalPoltronas,
                                 int poltronasPorLinha, double preco) {
        for (int i = 1; i <= Math.ceil((double) totalPoltronas / poltronasPorLinha); i++) {
            HBox linha = new HBox(10);
            linha.setAlignment(Pos.CENTER);
            for (int j = 1; j <= poltronasPorLinha && (i - 1) * poltronasPorLinha + j <= totalPoltronas; j++) {
                String seatId = prefixo + " " + ((i - 1) * poltronasPorLinha + j);
                criarBotao(linha, seatId, preco);
            }
            section.getChildren().add(linha);
        }
    }

    private void configurarFrisa() {
        configurarFrisaLado(frisaEsquerdaSection, "F", 1, 6, true, 120.00);
        configurarFrisaLado(frisaDireitaSection, "F", 2, 6, false, 120.00);
    }

    private void configurarFrisaLado(VBox section, String prefixo, int inicio,
                                     int fim, boolean esquerda, double preco) {
        char[] abc = "ABCDEF".toCharArray();
        for (int i = inicio; i <= fim; i += 2) {
            VBox grupo = new VBox(5);
            grupo.setAlignment(Pos.CENTER);
            for (int linha = 1; linha <= 2; linha++) {
                HBox hboxLinha = new HBox(10);
                hboxLinha.setAlignment(esquerda ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

                int inicioBotao = (linha - 1) * 3 + 1;
                int fimBotao = linha * 3 - (linha == 2 ? 1 : 0);

                for (int j = inicioBotao; j <= fimBotao; j++) {
                    String seatId = prefixo + abc[i-1] + " " + j;
                    criarBotao(hboxLinha, seatId, preco);
                }
                grupo.getChildren().add(hboxLinha);
            }
            section.getChildren().add(grupo);
        }
    }

    private void configurarCamarote() {
        HBox layoutCamarotes = new HBox(20);
        layoutCamarotes.setAlignment(Pos.CENTER);
        char[] abc = "ABCDEF".toCharArray();
        for (int i = 1; i <= 5; i++) {
            VBox camarote = new VBox(10);
            camarote.setAlignment(Pos.CENTER);
            for (int linha = 1; linha <= 3; linha++) {
                HBox hboxLinha = new HBox(10);
                hboxLinha.setAlignment(Pos.CENTER);
                int inicioBotao = (linha - 1) * 4 + 1;
                int fimBotao = linha * 4 - (linha == 3 ? 2 : 0);

                for (int j = inicioBotao; j <= fimBotao; j++) {
                    String seatId = "C" + abc[i] + " " + j;
                    criarBotao(hboxLinha, seatId, 80.00);
                }
                camarote.getChildren().add(hboxLinha);
            }
            layoutCamarotes.getChildren().add(camarote);
        }
        camaroteSection.getChildren().add(layoutCamarotes);
    }

    private void criarBotao(HBox linha, String poltronaId, double preco) {
        Button botaoPoltrona = new Button(poltronaId);
        botaoPoltrona.setId("seat-" + poltronaId.replace(" ", "-"));
        botaoPoltrona.setStyle("-fx-background-color: #ede0d4; -fx-text-fill: #532920; " +
                "-fx-font-size: 14px; -fx-cursor: hand;");
        botaoPoltrona.setOnAction(event -> selecionarPoltrona(botaoPoltrona, poltronaId, preco));
        linha.getChildren().add(botaoPoltrona);
    }

    private void selecionarPoltrona(Button botao, String poltronaId, double preco) {
        if (poltronasSelecionadas.contains(poltronaId)) {
            poltronasSelecionadas.remove(poltronaId);
            total -= preco;
            botao.setStyle("-fx-background-color: #ede0d4; -fx-text-fill: #532920;");
        } else {
            if (poltronasSelecionadas.size() < quantidadeIngressos) {
                poltronasSelecionadas.add(poltronaId);
                total += preco;
                botao.setStyle("-fx-background-color: #532920; -fx-text-fill: #ede0d4;");
            } else {
                showAlert("Limite de Poltronas",
                        "Você atingiu o limite de poltronas selecionadas.",
                        "Por favor, remova uma poltrona antes de selecionar outra.");
            }
        }
        atualizarTotal();
    }

    private Set<String> getSoldSeats(String dataSessao, String turnoSessao) {
        Set<String> soldSeats = new HashSet<>();
        try {
            List<String[]> itensVenda = CSVUtils.readCSV(2); // Read ItemVenda.csv
            for (String[] item : itensVenda) {
                if (item.length > 4 && item[3].equals(dataSessao) && item[4].equals(turnoSessao)) {
                    // Format: "AreaId SeatNumber" (e.g., "PA 21")
                    String seatId = item[2] + " " + item[1];
                    soldSeats.add(seatId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return soldSeats;
    }

    private void markSoldSeats(Set<String> soldSeats) {
        // Mark seats in all sections
        markSectionSeats(plateiaASection, soldSeats);
        markSectionSeats(plateiaBSection, soldSeats);
        markSectionSeats(frisaEsquerdaSection, soldSeats);
        markSectionSeats(frisaDireitaSection, soldSeats);
        markSectionSeats(camaroteSection, soldSeats);
        markSectionSeats(balcaoNobreSection, soldSeats);
    }

    private void markSectionSeats(VBox section, Set<String> soldSeats) {
        if (section == null) return;

        for (javafx.scene.Node node : section.getChildren()) {
            if (node instanceof HBox) {
                markHBoxSeats((HBox) node, soldSeats);
            } else if (node instanceof VBox) {
                markSectionSeats((VBox) node, soldSeats);
            }
        }
    }

    private void markHBoxSeats(HBox hbox, Set<String> soldSeats) {
        for (javafx.scene.Node node : hbox.getChildren()) {
            if (node instanceof Button) {
                Button seatButton = (Button) node;
                String seatId = seatButton.getText();

                if (soldSeats.contains(seatId)) {
                    seatButton.setDisable(true);
                    seatButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
                    System.out.println("Marked as sold: " + seatId);
                }
            }
        }
    }

    private void atualizarTotal() {
        labelTotal.setText(formatador.format(total));
    }

    @FXML
    public void switchToConfirmar(ActionEvent event) {
        if (poltronasSelecionadas.size() < quantidadeIngressos) {
            showAlert("Seleção Incompleta",
                    "Número insuficiente de poltronas selecionadas",
                    "Por favor, selecione todas as poltronas antes de continuar.");
            return;
        }

        State selecionado = Controller.getCurrentState();
        poltronasSelecionadas.forEach(poltronaId -> {
            String num = poltronaId.substring(poltronaId.indexOf(" ") + 1);
            String prefix = poltronaId.substring(0, poltronaId.indexOf(" "));
            selecionado.setIvCounter(selecionado.getIvCounter() + 1);
            selecionado.getVendasAtuais().add(new ItemVenda(
                    num,
                    prefix,
                    selecionado.getSessao(),
                    selecionado.getTurno(),
                    selecionado.getPeca(),
                    selecionado.getVenda().getId()
            ));
        });

        Controller.setCurrentState(selecionado);
        switchToTela("/Confirmar.fxml", event,
                (ConfirmarController controller) -> controller.setTotal(total));
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}