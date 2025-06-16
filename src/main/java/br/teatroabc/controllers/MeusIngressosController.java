package br.teatroabc.controllers;

import br.teatroabc.Models.Cliente;
import br.teatroabc.Models.ItemVenda;
import br.teatroabc.Models.Venda;
import br.teatroabc.utils.CSVUtils; // Não usado diretamente, mas pode ser para EstatisticasService
import br.teatroabc.utils.EstatisticasService; // Serviço para carregar dados de CSVs
import br.teatroabc.utils.GeneralUse; // Utilitários gerais, como validação de CPF
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader; // Para carregar novas telas FXML
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*; // Componentes de UI do JavaFX
import javafx.scene.layout.VBox;
import javafx.stage.Stage; // Para criar novas janelas

import java.io.IOException;
import java.time.LocalDate; // Para trabalhar com datas
import java.time.format.DateTimeFormatter; // Para formatar datas
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors; // Para coletar elementos de streams

/**
 * Controlador da tela "Meus Ingressos".
 * Permite que um cliente busque seus ingressos comprados informando CPF e data de nascimento.
 * Também permite visualizar os detalhes de um ingresso selecionado.
 */
public class MeusIngressosController {

    // Componentes FXML da interface
    @FXML private TextField cpfField; // Campo para entrada do CPF
    @FXML private DatePicker dateNascimento; // Seletor de data de nascimento
    @FXML private Button btnBuscarIngressos; // Botão para iniciar a busca
    @FXML private VBox searchBox; // Contêiner da seção de busca (inicialmente visível)
    @FXML private VBox resultBox; // Contêiner da seção de resultados (inicialmente invisível)
    @FXML private ListView<String> ingressosList; // Lista para exibir os ingressos encontrados

    // Mapa para associar a informação de exibição de um ingresso (String) ao seu objeto ItemVenda real
    private final Map<String, ItemVenda> ticketMap = new HashMap<>();

    /**
     * Método de inicialização do controlador, chamado automaticamente pelo JavaFX.
     * Configura o formato do DatePicker e adiciona um listener para cliques na lista de ingressos.
     */
    @FXML
    private void initialize() {
        // Configura um conversor para o DatePicker para lidar com o formato de data "yyyy-MM-dd"
        dateNascimento.setConverter(new javafx.util.StringConverter<>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                return date != null ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string, dateFormatter) : null;
            }
        });

        // Adiciona um listener para quando um item na lista de ingressos é selecionado
        ingressosList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Se um novo item for selecionado, abre os detalhes desse ingresso
                openTicketDetails(newVal);
            }
        });
    }

    /**
     * Abre uma nova janela (Stage) para exibir os detalhes de um ingresso selecionado.
     * @param ticketInfo A string formatada do ingresso selecionado na lista.
     */
    private void openTicketDetails(String ticketInfo) {
        try {
            // Obtém o objeto ItemVenda correspondente à string de exibição do mapa
            ItemVenda ingresso = ticketMap.get(ticketInfo);
            if (ingresso == null) return; // Se não encontrar, retorna

            // Carrega o FXML da tela de detalhes do ingresso
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetalhesIngresso.fxml"));
            Parent root = loader.load();

            // Obtém o controlador da tela de detalhes e passa as informações do ingresso
            DetalhesIngressoController controller = loader.getController();
            controller.setTicketDetails(
                    ingresso.getPecaId(),
                    ingresso.getDataSessao(),
                    ingresso.getTurnoSessao(),
                    ingresso.getAreaId(),
                    ingresso.getPoltronaId()
            );

            // Cria uma nova Stage (janela) para exibir os detalhes
            Stage stage = new Stage();
            stage.setTitle("Detalhes do Ingresso");
            stage.setScene(new Scene(root));
            stage.setWidth(400);  // Define largura
            stage.setHeight(500); // Define altura

            stage.show(); // Exibe a nova janela
        } catch (IOException e) {
            e.printStackTrace(); // Imprime o erro no console
            showAlert("Erro", "Não foi possível abrir os detalhes do ingresso."); // Exibe alerta ao usuário
        }
    }

    /**
     * Método acionado pelo botão "Buscar Ingressos".
     * Realiza a validação dos dados de entrada (CPF e Data de Nascimento),
     * busca o cliente e suas vendas nos arquivos CSV, e exibe os ingressos encontrados.
     */
    @FXML
    private void buscarIngressos() {
        String cpf = cpfField.getText().trim(); // Obtém e limpa o CPF
        LocalDate dataNasc = dateNascimento.getValue(); // Obtém a data de nascimento

        // 1. Validação de Entradas
        if (cpf.isEmpty() || dataNasc == null) {
            showAlert("Erro", "Por favor, preencha todos os campos.");
            return;
        }

        if (!GeneralUse.isCPFValid(cpf)) {
            showAlert("Erro", "CPF inválido.");
            return;
        }

        try {
            // Limpa os resultados de buscas anteriores
            ticketMap.clear();

            // 2. Carrega Dados dos CSVs usando EstatisticasService
            EstatisticasService estatisticas = new EstatisticasService(
                    "data/BD/Cliente.csv",
                    "data/BD/Venda.csv",
                    "data/BD/ItemVenda.csv"
            );

            // 3. Encontra o cliente pelo CPF
            List<Cliente> clientes = estatisticas.getClientes();
            Cliente cliente = clientes.stream()
                    .filter(c -> c.getCPF().equals(cpf))
                    .findFirst()
                    .orElse(null); // Retorna null se não encontrar

            if (cliente == null) {
                showAlert("Erro", "Nenhum cliente encontrado com este CPF.");
                return;
            }

            // 4. Verifica a data de nascimento
            try {
                Object storedBirthDate = cliente.getDataNasc(); // Pode vir como String ou LocalDate
                LocalDate storedDate;

                if (storedBirthDate instanceof String) {
                    storedDate = LocalDate.parse((String) storedBirthDate,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } else if (storedBirthDate instanceof LocalDate) {
                    storedDate = (LocalDate) storedBirthDate;
                } else {
                    throw new IllegalArgumentException("Tipo de data inválido no cadastro");
                }

                if (!storedDate.isEqual(dataNasc)) { // Compara as datas
                    showAlert("Erro", "Data de nascimento não corresponde ao CPF informado.");
                    return;
                }
            } catch (Exception e) {
                showAlert("Erro", "Erro ao verificar data de nascimento: " + e.getMessage());
                return;
            }

            // 5. Encontra todas as vendas associadas a este cliente
            List<Venda> vendas = estatisticas.getVendas().stream()
                    .filter(v -> v.getIdCliente() == cliente.getId())
                    .collect(Collectors.toList());

            if (vendas.isEmpty()) {
                showAlert("Informação", "Nenhum ingresso encontrado para este cliente.");
                return;
            }

            // 6. Obtém todos os itens de venda (ingressos) relacionados a essas vendas
            List<ItemVenda> ingressos = estatisticas.getItensVenda().stream()
                    .filter(item -> vendas.stream().anyMatch(v -> v.getId() == item.getVendaId()))
                    .collect(Collectors.toList());

            // 7. Formata os ingressos para exibição e popula o mapa
            ObservableList<String> ingressosFormatados = FXCollections.observableArrayList();
            for (ItemVenda ingresso : ingressos) {
                String displayInfo = String.format("%s - %s", // Formato de exibição
                        ingresso.getPecaId(),
                        ingresso.getDataSessao());

                ingressosFormatados.add(displayInfo);
                ticketMap.put(displayInfo, ingresso); // Associa string formatada ao objeto ItemVenda
            }

            // 8. Exibe os resultados na ListView e torna a seção de resultados visível
            ingressosList.setItems(ingressosFormatados);
            resultBox.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace(); // Imprime o erro no console
            showAlert("Erro", "Ocorreu um erro ao buscar os ingressos: " + e.getMessage()); // Exibe alerta
        }
    }

    /**
     * Exibe uma janela de alerta (Alert).
     * @param title O título da janela de alerta.
     * @param message A mensagem a ser exibida no alerta.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // Sem cabeçalho
        alert.setContentText(message);
        alert.showAndWait(); // Exibe o alerta e aguarda a interação do usuário
    }
}