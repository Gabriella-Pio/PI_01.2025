package br.teatroabc.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EscolherPoltronaController {

    @FXML
    private VBox rootLayout;

    @FXML
    private VBox plateiaASection, plateiaBSection, frisaSection, camaroteSection, balcaoNobreSection;

    @FXML
    public void initialize() {
        configurarPlateiaA();
        configurarPlateiaB();
        configurarFrisa();
        configurarCamarote();
        configurarBalcaoNobre();
    }

    private void configurarPlateiaA() {
        plateiaASection.getChildren().add(new Label("Plateia A - R$ 40.00"));
        int totalPoltronas = 25;
        int poltronasPorLinha = 10;

        for (int i = 1; i <= Math.ceil((double) totalPoltronas / poltronasPorLinha); i++) {
            HBox linha = new HBox(10);
            for (int j = 1; j <= poltronasPorLinha && (i - 1) * poltronasPorLinha + j <= totalPoltronas; j++) {
                criarBotao(linha, "A " + ((i - 1) * poltronasPorLinha + j), 40.00);
            }
            plateiaASection.getChildren().add(linha);
        }
    }

    private void configurarPlateiaB() {
        plateiaBSection.getChildren().add(new Label("Plateia B - R$ 60.00"));
        int totalPoltronas = 100;
        int poltronasPorLinha = 16; // Máximo por linha
        int linhas = 8; // Total de linhas

        for (int i = 1; i <= linhas; i++) {
            HBox linha = new HBox(10);
            int poltronasNestaLinha = (i == linhas) ? 10 : poltronasPorLinha; // Última linha tem no máximo 10 poltronas
            for (int j = 1; j <= poltronasNestaLinha; j++) {
                criarBotao(linha, "B " + ((i - 1) * poltronasPorLinha + j), 60.00);
            }
            plateiaBSection.getChildren().add(linha);
        }
    }

    private void configurarFrisa() {
        frisaSection.getChildren().add(new Label("Frisa - R$ 120.00"));
        int grupos = 6;

        for (int i = 1; i <= grupos; i++) {
            VBox grupo = new VBox(5);
            HBox linha1 = new HBox(10);
            HBox linha2 = new HBox(10);

            for (int j = 1; j <= 3; j++) {
                criarBotao(linha1, "Frisa " + i + "-" + j, 120.00);
            }
            for (int j = 4; j <= 5; j++) {
                criarBotao(linha2, "Frisa " + i + "-" + j, 120.00);
            }
            grupo.getChildren().addAll(linha1, linha2);
            frisaSection.getChildren().add(grupo);
        }
    }

    private void configurarCamarote() {
        camaroteSection.getChildren().add(new Label("Camarote - R$ 80.00"));
        int grupos = 4;

        for (int i = 1; i <= grupos; i++) {
            VBox grupo = new VBox(5);
            HBox linha1 = new HBox(10);
            HBox linha2 = new HBox(10);

            for (int j = 1; j <= 5; j++) {
                criarBotao(linha1, "Camarote " + i + "-" + j, 80.00);
            }
            for (int j = 6; j <= 10; j++) {
                criarBotao(linha2, "Camarote " + i + "-" + j, 80.00);
            }
            grupo.getChildren().addAll(linha1, linha2);
            camaroteSection.getChildren().add(grupo);
        }
    }

    private void configurarBalcaoNobre() {
        balcaoNobreSection.getChildren().add(new Label("Balcão Nobre - R$ 250.00"));
        int totalPoltronas = 50;
        int poltronasPorLinha = 10;

        for (int i = 1; i <= Math.ceil((double) totalPoltronas / poltronasPorLinha); i++) {
            HBox linha = new HBox(10);
            for (int j = 1; j <= poltronasPorLinha && (i - 1) * poltronasPorLinha + j <= totalPoltronas; j++) {
                criarBotao(linha, "Balcão " + ((i - 1) * poltronasPorLinha + j), 250.00);
            }
            balcaoNobreSection.getChildren().add(linha);
        }
    }

    private void criarBotao(HBox linha, String poltronaId, double preco) {
        Button botaoPoltrona = new Button(poltronaId);
        botaoPoltrona.setStyle("-fx-background-color: #ede0d4; -fx-text-fill: #0f1b13; -fx-font-size: 14px;");
        botaoPoltrona.setOnAction(event -> selecionarPoltrona(poltronaId, preco));
        linha.getChildren().add(botaoPoltrona);
    }

    private void selecionarPoltrona(String poltronaId, double preco) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Poltrona Selecionada");
        alert.setHeaderText(null);
        alert.setContentText("Você selecionou a poltrona: " + poltronaId + "\nPreço: R$ " + preco);
        alert.showAndWait();
    }
}
