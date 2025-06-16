package br.teatroabc.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DetalhesIngressoController {

    @FXML
    private Label pecaLabel;

    @FXML
    private Label diaEscolhidoLabel;

    @FXML
    private Label sessaoLabel;

    @FXML
    private Label areaLabel;

    @FXML
    private Label poltronaLabel;
//todo Ler CSV e filtrar por CPF os detalhes
    public void setDetalhes(String peca, String diaEscolhido, String sessao, String area, String poltrona) {


        pecaLabel.setText("Peça: ...... " + peca);
        diaEscolhidoLabel.setText("Data: ........ " + diaEscolhido);
        sessaoLabel.setText("Sessão: .......... " + sessao);
        areaLabel.setText("Área: .......... " + area);
        poltronaLabel.setText("Poltrona: ........... " + poltrona);
    }
}
