package br.teatroabc.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EscolherPoltronaController {

    @FXML
    private VBox rootLayout;

    @FXML
    private VBox plateiaASection, plateiaBSection, frisaEsquerdaSection, frisaDireitaSection, camaroteSection, balcaoNobreSection;

    @FXML
    public void initialize() {
        configurarPlateiaA();
        configurarPlateiaB();
        configurarFrisa();
        configurarCamarote();
        configurarBalcaoNobre();
    }

    private void configurarPlateiaA() {
        int totalPoltronas = 25;
        int poltronasPorLinha = 13;

        for (int i = 1; i <= Math.ceil((double) totalPoltronas / poltronasPorLinha); i++) {
            HBox linha = new HBox(10);
            linha.setAlignment(Pos.CENTER); // Centraliza os botões na linha
            for (int j = 1; j <= poltronasPorLinha && (i - 1) * poltronasPorLinha + j <= totalPoltronas; j++) {
                criarBotao(linha, "A " + ((i - 1) * poltronasPorLinha + j), 40.00);
            }
            plateiaASection.getChildren().add(linha);
        }
    }

    private void configurarPlateiaB() {
        int totalPoltronas = 100;
        int poltronasPorLinha = 15; // Máximo por linha

        for (int i = 1; i <= Math.ceil((double) totalPoltronas / poltronasPorLinha); i++) {
            HBox linha = new HBox(10);
            linha.setAlignment(Pos.CENTER); // Centraliza os botões na linha
            for (int j = 1; j <= poltronasPorLinha && (i - 1) * poltronasPorLinha + j <= totalPoltronas; j++) {
                criarBotao(linha, "B " + ((i - 1) * poltronasPorLinha + j), 60.00);
            }
            plateiaBSection.getChildren().add(linha);
        }
    }

    private void configurarFrisa() {

        // Configurar frisas 1, 3 e 5 na esquerda
        for (int i = 1; i <= 6; i += 2) {
            VBox grupoEsquerda = new VBox(5);
            grupoEsquerda.setAlignment(Pos.CENTER);
            HBox linha1 = new HBox(10);
            linha1.setAlignment(Pos.CENTER_RIGHT);
            HBox linha2 = new HBox(10);
            linha2.setAlignment(Pos.CENTER_RIGHT);

            for (int j = 1; j <= 3; j++) {
                criarBotao(linha1, "F " + i + "-" + j, 120.00);
            }
            for (int j = 4; j <= 5; j++) {
                criarBotao(linha2, "F " + i + "-" + j, 120.00);
            }
            grupoEsquerda.getChildren().addAll(linha1, linha2);
            frisaEsquerdaSection.getChildren().add(grupoEsquerda);
        }

        // Configurar frisas 2, 4 e 6 na direita
        for (int i = 2; i <= 6; i += 2) {
            VBox grupoDireita = new VBox(5);
            grupoDireita.setAlignment(Pos.CENTER);
            HBox linha1 = new HBox(10);
            linha1.setAlignment(Pos.CENTER_LEFT);
            HBox linha2 = new HBox(10);
            linha2.setAlignment(Pos.CENTER_LEFT);

            for (int j = 1; j <= 3; j++) {
                criarBotao(linha1, "F " + i + "-" + j, 120.00);
            }
            for (int j = 4; j <= 5; j++) {
                criarBotao(linha2, "F " + i + "-" + j, 120.00);
            }
            grupoDireita.getChildren().addAll(linha1, linha2);
            frisaDireitaSection.getChildren().add(grupoDireita);
        }
    }


    private void configurarCamarote() {
        // HBox para alinhar os camarotes lado a lado
        HBox layoutCamarotes = new HBox(20); // Espaçamento entre os camarotes
        layoutCamarotes.setAlignment(Pos.CENTER); // Centraliza os camarotes no eixo horizontal

        int grupos = 5; // Número de camarotes

        for (int i = 1; i <= grupos; i++) {
            VBox grupo = new VBox(10); // Configuração vertical para cada camarote
            grupo.setAlignment(Pos.CENTER); // Centraliza os botões no camarote

            HBox linha1 = new HBox(10);
            linha1.setAlignment(Pos.CENTER); // Centraliza os botões na linha
            HBox linha2 = new HBox(10);
            linha2.setAlignment(Pos.CENTER); // Centraliza os botões na linha
            HBox linha3 = new HBox(10);
            linha3.setAlignment(Pos.CENTER); // Centraliza os botões na linha

            // Adiciona 5 botões na primeira linha
            for (int j = 1; j <= 4; j++) {
                criarBotao(linha1, "C " + i + "-" + j, 80.00);
            }

            // Adiciona 5 botões na segunda linha
            for (int j = 5; j <= 8; j++) {
                criarBotao(linha2, "C " + i + "-" + j, 80.00);
            }

            // Adiciona 5 botões na primeira linha
            for (int j = 9; j <= 10; j++) {
                criarBotao(linha3, "C " + i + "-" + j, 80.00);
            }

            // Adiciona as linhas ao camarote
            grupo.getChildren().addAll(linha1, linha2, linha3);

            // Adiciona o camarote ao layout horizontal principal
            layoutCamarotes.getChildren().add(grupo);
        }

        // Adiciona o layout horizontal à seção dos camarotes
        camaroteSection.getChildren().add(layoutCamarotes);
    }

    private void configurarBalcaoNobre() {
        int totalPoltronas = 50;
        int poltronasPorLinha = 18;

        for (int i = 1; i <= Math.ceil((double) totalPoltronas / poltronasPorLinha); i++) {
            HBox linha = new HBox(10);
            linha.setAlignment(Pos.CENTER); // Centraliza os botões na linha
            for (int j = 1; j <= poltronasPorLinha && (i - 1) * poltronasPorLinha + j <= totalPoltronas; j++) {
                criarBotao(linha, "BN " + ((i - 1) * poltronasPorLinha + j), 250.00);
            }
            balcaoNobreSection.getChildren().add(linha);
        }
    }

    private void criarBotao(HBox linha, String poltronaId, double preco) {
        Button botaoPoltrona = new Button(poltronaId);
        botaoPoltrona.setStyle("-fx-background-color: #ede0d4; -fx-text-fill: #0f1b13; -fx-font-size: 14px; -fx-cursor: hand;\n");
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