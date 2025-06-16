package br.teatroabc.controllers;

import javafx.fxml.FXML; // Importa FXML para anotar campos que correspondem a elementos FXML
import javafx.scene.control.Label; // Importa Label para exibir texto na interface

/**
 * Controlador para a tela de "Detalhes do Ingresso".
 * Esta classe é responsável por exibir as informações detalhadas de um único ingresso
 * em uma interface gráfica.
 */
public class DetalhesIngressoController {
    // Anotações FXML para injetar os rótulos (Labels) definidos no arquivo FXML
    @FXML private Label pecaLabel; // Rótulo para exibir o nome da peça
    @FXML private Label dataLabel; // Rótulo para exibir a data da sessão
    @FXML private Label turnoLabel; // Rótulo para exibir o turno (horário) da sessão
    @FXML private Label areaLabel; // Rótulo para exibir a área do teatro (ex: "Plateia A", "Frisa")
    @FXML private Label poltronaLabel; // Rótulo para exibir o número ou identificação da poltrona

    /**
     * Define os detalhes específicos do ingresso nos rótulos da interface.
     * Este método é chamado de outra tela (ex: MeusIngressosController) para popular os dados
     * quando os detalhes de um ingresso são solicitados.
     *
     * @param peca O nome da peça teatral.
     * @param dataSessao A data da sessão.
     * @param turnoSessao O turno (horário) da sessão.
     * @param area A área do teatro onde a poltrona está localizada.
     * @param poltrona A identificação da poltrona.
     */
    public void setTicketDetails(String peca, String dataSessao, String turnoSessao, String area, String poltrona) {
        // Verifica se o rótulo não é nulo antes de tentar definir o texto, evitando NullPointerException
        if (pecaLabel != null) pecaLabel.setText("Peça: " + peca);
        if (dataLabel != null) dataLabel.setText("Data: " + dataSessao);
        if (turnoLabel != null) turnoLabel.setText("Turno: " + turnoSessao);
        if (areaLabel != null) areaLabel.setText("Área: " + area);
        if (poltronaLabel != null) poltronaLabel.setText("Poltrona: " + poltrona);

        // Saída de depuração para verificar se os rótulos foram inicializados corretamente
        System.out.println("Labels initialized: " +
                "\npecaLabel: " + (pecaLabel != null) +
                "\ndataLabel: " + (dataLabel != null) +
                "\nturnoLabel: " + (turnoLabel != null) +
                "\nareaLabel: " + (areaLabel != null) +
                "\npoltronaLabel: " + (poltronaLabel != null));
    }
}