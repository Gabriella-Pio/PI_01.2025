package br.teatroabc.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DetalhesIngressoController {
    @FXML private Label pecaLabel;
    @FXML private Label dataLabel;
    @FXML private Label turnoLabel;
    @FXML private Label areaLabel;
    @FXML private Label poltronaLabel;

    public void setTicketDetails(String peca, String dataSessao, String turnoSessao, String area, String poltrona) {
        if (pecaLabel != null) pecaLabel.setText("Peça: " + peca);
        if (dataLabel != null) dataLabel.setText("Data: " + dataSessao);
        if (turnoLabel != null) turnoLabel.setText("Turno: " + turnoSessao);
        if (areaLabel != null) areaLabel.setText("Área: " + area);
        if (poltronaLabel != null) poltronaLabel.setText("Poltrona: " + poltrona);

        // Debug output
        System.out.println("Labels initialized: " +
                "\npecaLabel: " + (pecaLabel != null) +
                "\ndataLabel: " + (dataLabel != null) +
                "\nturnoLabel: " + (turnoLabel != null) +
                "\nareaLabel: " + (areaLabel != null) +
                "\npoltronaLabel: " + (poltronaLabel != null));
    }
}