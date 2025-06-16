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

    // Componentes FXML da interface
    @FXML private VBox rootLayout;
    @FXML private VBox plateiaASection, plateiaBSection, frisaEsquerdaSection, frisaDireitaSection, camaroteSection, balcaoNobreSection;
    @FXML private Label labelTotal;

    // Variáveis de estado da seleção de poltronas
    private int quantidadeIngressos;
    private final ArrayList<String> poltronasSelecionadas = new ArrayList<>();
    private double total = 0.0;
    private final DecimalFormat formatador = new DecimalFormat("R$ #,##0.00");

    /**
     * Método de inicialização do controlador.
     * Configura as seções do teatro e marca as poltronas já vendidas.
     */
    @FXML
    public void initialize() {
        System.out.println("Initializing seat selection controller...");

        // Configurações das diferentes seções do teatro
        configurarSecao(plateiaASection, "PA", 25, 13, 40.00);
        configurarSecao(plateiaBSection, "PB", 100, 15, 60.00);
        configurarFrisa();
        configurarCamarote();
        configurarSecao(balcaoNobreSection, "BN", 50, 18, 250.00);

        // Obtém a data e turno da sessão atual do estado global
        State state = Controller.getCurrentState();
        String dataSessao = state.getSessao();
        String turnoSessao = state.getTurno();

        System.out.println("Current session: " + dataSessao + " - " + turnoSessao);

        // Obtém e marca as poltronas já vendidas para a sessão atual
        Set<String> soldSeats = getSoldSeats(dataSessao, turnoSessao);
        System.out.println("Found sold seats: " + soldSeats);
        markSoldSeats(soldSeats);
    }

    /**
     * Define a quantidade de ingressos que o usuário deseja comprar.
     * @param quantidade O número de ingressos a serem selecionados.
     */
    public void setQuantidadeIngressos(int quantidade) {
        this.quantidadeIngressos = quantidade;
    }

    /**
     * Configura uma seção genérica do teatro (Plateia A, Plateia B, Balcão Nobre).
     * Cria botões para cada poltrona e os organiza em linhas.
     * @param section O VBox que representa a seção.
     * @param prefixo O prefixo do ID da poltrona (ex: "PA", "PB").
     * @param totalPoltronas O número total de poltronas na seção.
     * @param poltronasPorLinha O número de poltronas por linha.
     * @param preco O preço de cada poltrona na seção.
     */
    private void configurarSecao(VBox section, String prefixo, int totalPoltronas, int poltronasPorLinha, double preco) {
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

    /**
     * Configura as seções de frisa (esquerda e direita).
     * As frisas têm um layout específico com letras para as fileiras.
     */
    private void configurarFrisa() {
        configurarFrisaLado(frisaEsquerdaSection, "F", 1, 6, true, 120.00);
        configurarFrisaLado(frisaDireitaSection, "F", 2, 6, false, 120.00);
    }

    /**
     * Configura um lado da frisa (esquerda ou direita).
     * @param section O VBox que representa a seção da frisa.
     * @param prefixo O prefixo do ID da poltrona (ex: "F").
     * @param inicio Índice inicial para a iteração das frisas.
     * @param fim Índice final para a iteração das frisas.
     * @param esquerda Booleano indicando se é a frisa esquerda para alinhamento.
     * @param preco O preço de cada poltrona na frisa.
     */
    private void configurarFrisaLado(VBox section, String prefixo, int inicio, int fim, boolean esquerda, double preco) {
        char[] abc = "ABCDEF".toCharArray(); // Usado para as letras das frisas
        for (int i = inicio; i <= fim; i += 2) { // Iteração a cada 2 para frisas
            VBox grupo = new VBox(5);
            grupo.setAlignment(Pos.CENTER);
            for (int linha = 1; linha <= 2; linha++) { // Duas linhas de poltronas por frisa
                HBox hboxLinha = new HBox(10);
                hboxLinha.setAlignment(esquerda ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

                int inicioBotao = (linha - 1) * 3 + 1;
                int fimBotao = linha * 3 - (linha == 2 ? 1 : 0); // Ajuste para a segunda linha

                for (int j = inicioBotao; j <= fimBotao; j++) {
                    String seatId = prefixo + abc[i-1] + " " + j;
                    criarBotao(hboxLinha, seatId, preco);
                }
                grupo.getChildren().add(hboxLinha);
            }
            section.getChildren().add(grupo);
        }
    }

    /**
     * Configura a seção de camarotes.
     * Organiza os camarotes e suas poltronas.
     */
    private void configurarCamarote() {
        HBox layoutCamarotes = new HBox(20);
        layoutCamarotes.setAlignment(Pos.CENTER);
        char[] abc = "ABCDEF".toCharArray();
        for (int i = 1; i <= 5; i++) { // 5 camarotes
            VBox camarote = new VBox(10);
            camarote.setAlignment(Pos.CENTER);
            for (int linha = 1; linha <= 3; linha++) { // 3 linhas de poltronas por camarote
                HBox hboxLinha = new HBox(10);
                hboxLinha.setAlignment(Pos.CENTER);
                int inicioBotao = (linha - 1) * 4 + 1;
                int fimBotao = linha * 4 - (linha == 3 ? 2 : 0); // Ajuste para a última linha

                for (int j = inicioBotao; j <= fimBotao; j++) {
                    String seatId = "C" + abc[i] + " " + j; // Ex: C B 1
                    criarBotao(hboxLinha, seatId, 80.00);
                }
                camarote.getChildren().add(hboxLinha);
            }
            layoutCamarotes.getChildren().add(camarote);
        }
        camaroteSection.getChildren().add(layoutCamarotes);
    }

    /**
     * Cria um botão de poltrona com seu ID e estilo padrão.
     * Adiciona um manipulador de evento para a seleção.
     * @param linha O HBox onde o botão será adicionado.
     * @param poltronaId O ID único da poltrona (ex: "PA 1").
     * @param preco O preço da poltrona.
     */
    private void criarBotao(HBox linha, String poltronaId, double preco) {
        Button botaoPoltrona = new Button(poltronaId);
        botaoPoltrona.setId("seat-" + poltronaId.replace(" ", "-"));
        botaoPoltrona.setStyle("-fx-background-color: #ede0d4; -fx-text-fill: #532920; " +
                "-fx-font-size: 14px; -fx-cursor: hand;");
        botaoPoltrona.setOnAction(event -> selecionarPoltrona(botaoPoltrona, poltronaId, preco));
        linha.getChildren().add(botaoPoltrona);
    }

    /**
     * Manipula a seleção/desseleção de uma poltrona.
     * Atualiza a lista de poltronas selecionadas e o total.
     * @param botao O botão da poltrona clicado.
     * @param poltronaId O ID da poltrona.
     * @param preco O preço da poltrona.
     */
    private void selecionarPoltrona(Button botao, String poltronaId, double preco) {
        if (poltronasSelecionadas.contains(poltronaId)) {
            // Desseleciona a poltrona
            poltronasSelecionadas.remove(poltronaId);
            total -= preco;
            botao.setStyle("-fx-background-color: #ede0d4; -fx-text-fill: #532920;");
        } else {
            // Seleciona a poltrona, se não exceder o limite
            if (poltronasSelecionadas.size() < quantidadeIngressos) {
                poltronasSelecionadas.add(poltronaId);
                total += preco;
                botao.setStyle("-fx-background-color: #532920; -fx-text-fill: #ede0d4;");
            } else {
                // Exibe alerta se o limite for atingido
                showAlert("Limite de Poltronas",
                        "Você atingiu o limite de poltronas selecionadas.",
                        "Por favor, remova uma poltrona antes de selecionar outra.");
            }
        }
        atualizarTotal(); // Atualiza o valor total exibido
    }

    /**
     * Obtém as poltronas já vendidas para uma sessão específica lendo do arquivo CSV.
     * @param dataSessao A data da sessão.
     * @param turnoSessao O turno da sessão.
     * @return Um Set de Strings contendo os IDs das poltronas vendidas.
     */
    private Set<String> getSoldSeats(String dataSessao, String turnoSessao) {
        Set<String> soldSeats = new HashSet<>();
        try {
            // Lê o arquivo CSV de itens de venda
            List<String[]> itensVenda = CSVUtils.readCSV(2); // Read ItemVenda.csv
            for (String[] item : itensVenda) {
                // Verifica se o item de venda corresponde à sessão atual
                if (item.length > 4 && item[3].equals(dataSessao) && item[4].equals(turnoSessao)) {
                    // Format: "AreaId SeatNumber" (e.g., "PA 21")
                    String seatId = item[2] + " " + item[1];
                    soldSeats.add(seatId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Imprime o stack trace em caso de erro de leitura
        }
        return soldSeats;
    }

    /**
     * Percorre todas as seções do teatro e marca as poltronas que já foram vendidas.
     * @param soldSeats Um Set de Strings com os IDs das poltronas vendidas.
     */
    private void markSoldSeats(Set<String> soldSeats) {
        // Mark seats in all sections
        markSectionSeats(plateiaASection, soldSeats);
        markSectionSeats(plateiaBSection, soldSeats);
        markSectionSeats(frisaEsquerdaSection, soldSeats);
        markSectionSeats(frisaDireitaSection, soldSeats);
        markSectionSeats(camaroteSection, soldSeats);
        markSectionSeats(balcaoNobreSection, soldSeats);
    }

    /**
     * Método auxiliar recursivo para percorrer as seções e seus filhos (HBoxes ou VBoxes aninhados)
     * e marcar as poltronas vendidas.
     * @param section O VBox atual a ser percorrido.
     * @param soldSeats Um Set de Strings com os IDs das poltronas vendidas.
     */
    private void markSectionSeats(VBox section, Set<String> soldSeats) {
        if (section == null) return; // Garante que a seção não é nula

        for (javafx.scene.Node node : section.getChildren()) {
            if (node instanceof HBox) {
                // Se for um HBox, marca as poltronas dentro dele
                markHBoxSeats((HBox) node, soldSeats);
            } else if (node instanceof VBox) {
                // Se for um VBox (seção aninhada), chama recursivamente
                markSectionSeats((VBox) node, soldSeats);
            }
        }
    }

    /**
     * Marca as poltronas vendidas dentro de um HBox.
     * Desabilita o botão e altera seu estilo para indicar que está vendido.
     * @param hbox O HBox contendo os botões das poltronas.
     * @param soldSeats Um Set de Strings com os IDs das poltronas vendidas.
     */
    private void markHBoxSeats(HBox hbox, Set<String> soldSeats) {
        for (javafx.scene.Node node : hbox.getChildren()) {
            if (node instanceof Button) {
                Button seatButton = (Button) node;
                String seatId = seatButton.getText(); // Obtém o ID da poltrona do texto do botão

                if (soldSeats.contains(seatId)) {
                    // Se a poltrona foi vendida, desabilita e muda o estilo
                    seatButton.setDisable(true);
                    seatButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
                    System.out.println("Marked as sold: " + seatId);
                }
            }
        }
    }

    /**
     * Atualiza o Label que exibe o valor total da compra.
     */
    private void atualizarTotal() {
        labelTotal.setText(formatador.format(total));
    }

    /**
     * Manipula o evento de ir para a tela de confirmação.
     * Verifica se o número correto de poltronas foi selecionado.
     * Popula o estado global com os itens de venda selecionados.
     * @param event O evento de ação que disparou este método.
     */
    @FXML
    public void switchToConfirmar(ActionEvent event) {
        // Verifica se a quantidade de poltronas selecionadas é igual à quantidade de ingressos desejada
        if (poltronasSelecionadas.size() < quantidadeIngressos) {
            showAlert("Seleção Incompleta",
                    "Número insuficiente de poltronas selecionadas",
                    "Por favor, selecione todas as poltronas antes de continuar.");
            return; // Sai do método se a seleção estiver incompleta
        }

        State selecionado = Controller.getCurrentState(); // Obtém o estado global

        // Para cada poltrona selecionada, cria um ItemVenda e o adiciona ao estado
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

    /**
     * Exibe um alerta para o usuário.
     * @param title O título do alerta.
     * @param header O cabeçalho do alerta.
     * @param content O conteúdo da mensagem do alerta.
     */
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}