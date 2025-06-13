package br.teatroabc.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashSet;
import java.util.Set;

public class EscolherPoltronaController {

    @FXML
    private VBox rootLayout;

    @FXML
    private VBox plateiaASection, plateiaBSection, frisaEsquerdaSection, frisaDireitaSection, camaroteSection, balcaoNobreSection;

    private int quantidadeIngressos;
    private final Set<String> poltronasSelecionadas = new HashSet<>();

    @FXML
    public void initialize() {
        configurarSecao(plateiaASection, "A", 25, 13, 40.00);
        configurarSecao(plateiaBSection, "B", 100, 15, 60.00);
        configurarFrisa();
        configurarCamarote();
        configurarSecao(balcaoNobreSection, "BN", 50, 18, 250.00);
    }

    /**
     * Define a quantidade de ingressos informada na tela anterior.
     */
    public void setQuantidadeIngressos(int quantidade) {
        this.quantidadeIngressos = quantidade;
    }

    /**
     * Configura uma seção genérica de poltronas.
     */
    private void configurarSecao(VBox section, String prefixo, int totalPoltronas, int poltronasPorLinha, double preco) {
        for (int i = 1; i <= Math.ceil((double) totalPoltronas / poltronasPorLinha); i++) {
            HBox linha = new HBox(10);
            linha.setAlignment(Pos.CENTER);
            for (int j = 1; j <= poltronasPorLinha && (i - 1) * poltronasPorLinha + j <= totalPoltronas; j++) {
                criarBotao(linha, prefixo + " " + ((i - 1) * poltronasPorLinha + j), preco);
            }
            section.getChildren().add(linha);
        }
    }

    private void configurarFrisa() {
        configurarFrisaLado(frisaEsquerdaSection, "F", 1, 6, true, 120.00);
        configurarFrisaLado(frisaDireitaSection, "F", 2, 6, false, 120.00);
    }

    private void configurarFrisaLado(VBox section, String prefixo, int inicio, int fim, boolean esquerda, double preco) {
        for (int i = inicio; i <= fim; i += 2) {
            VBox grupo = new VBox(5);
            grupo.setAlignment(Pos.CENTER);
            for (int linha = 1; linha <= 2; linha++) {
                HBox hboxLinha = new HBox(10);
                hboxLinha.setAlignment(esquerda ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

                int inicioBotao = (linha - 1) * 3 + 1;
                int fimBotao = linha * 3 - (linha == 2 ? 1 : 0);

                for (int j = inicioBotao; j <= fimBotao; j++) {
                    criarBotao(hboxLinha, prefixo + " " + i + "-" + j, preco);
                }
                grupo.getChildren().add(hboxLinha);
            }
            section.getChildren().add(grupo);
        }
    }

    private void configurarCamarote() {
        HBox layoutCamarotes = new HBox(20);
        layoutCamarotes.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 5; i++) {
            VBox camarote = new VBox(10);
            camarote.setAlignment(Pos.CENTER);
            for (int linha = 1; linha <= 3; linha++) {
                HBox hboxLinha = new HBox(10);
                hboxLinha.setAlignment(Pos.CENTER);
                int inicioBotao = (linha - 1) * 4 + 1;
                int fimBotao = linha * 4 - (linha == 3 ? 2 : 0);

                for (int j = inicioBotao; j <= fimBotao; j++) {
                    criarBotao(hboxLinha, "C " + i + "-" + j, 80.00);
                }
                camarote.getChildren().add(hboxLinha);
            }
            layoutCamarotes.getChildren().add(camarote);
        }
        camaroteSection.getChildren().add(layoutCamarotes);
    }

    /**
     * Cria um botão representando uma poltrona.
     */
    private void criarBotao(HBox linha, String poltronaId, double preco) {
        Button botaoPoltrona = new Button(poltronaId);
        botaoPoltrona.setStyle("-fx-background-color: #ede0d4; -fx-text-fill: #532920; -fx-font-size: 14px; -fx-cursor: hand;");
        botaoPoltrona.setOnAction(event -> selecionarPoltrona(botaoPoltrona, poltronaId, preco));
        linha.getChildren().add(botaoPoltrona);
    }

    /**
     * Ação ao selecionar uma poltrona.
     */
    private void selecionarPoltrona(Button botao, String poltronaId, double preco) {
        if (poltronasSelecionadas.contains(poltronaId)) {
            poltronasSelecionadas.remove(poltronaId);
            botao.setStyle("-fx-background-color: #ede0d4; -fx-text-fill: #532920; -fx-font-size: 14px; -fx-cursor: hand;");
        } else {
            if (poltronasSelecionadas.size() < quantidadeIngressos) {
                poltronasSelecionadas.add(poltronaId);
                botao.setStyle("-fx-background-color: #532920; -fx-text-fill: #ede0d4; -fx-font-size: 14px; -fx-cursor: hand;");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Limite de Poltronas");
                alert.setHeaderText("Você atingiu o limite de poltronas selecionadas.");
                alert.setContentText("Por favor, remova uma poltrona antes de selecionar outra.");
                alert.showAndWait();
            }
        }
    }
}
