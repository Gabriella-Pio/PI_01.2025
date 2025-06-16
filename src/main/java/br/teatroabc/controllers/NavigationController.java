package br.teatroabc.controllers;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack; // Importa a classe Stack para gerenciar o histórico de telas
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * Controlador de navegação para gerenciar a transição entre telas (cenas) no aplicativo JavaFX.
 * Permite avançar e retroceder no histórico de telas.
 */
public class NavigationController {
    // Pilha estática para armazenar o histórico de cenas visitadas. LIFO (Last-In, First-Out).
    private static final Stack<Scene> screenHistory = new Stack<>();
    // Limite máximo de cenas a serem armazenadas no histórico para evitar consumo excessivo de memória.
    private static final int HISTORY_LIMIT = 10;

    /**
     * Salva a cena atual no histórico de telas. Se o histórico atingir o limite,
     * a cena mais antiga é removida.
     * @param currentScene A cena atual a ser salva.
     */
    public static void saveCurrentScene(Scene currentScene) {
        if (screenHistory.size() >= HISTORY_LIMIT) {
            // Remove a cena mais antiga (primeira da pilha) se o limite for atingido
            screenHistory.remove(0);
        }
        screenHistory.push(currentScene); // Adiciona a cena atual ao topo da pilha
    }

    /**
     * Retorna para a cena anterior no histórico, se houver.
     * @param event O evento que disparou a ação de voltar (usado para obter a Stage).
     */
    public static void voltar(Event event) {
        if (!screenHistory.isEmpty()) { // Verifica se há cenas no histórico
            Scene previousScene = screenHistory.pop(); // Remove e obtém a cena anterior do topo da pilha
            // Obtém a Stage (janela) a partir do evento
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(previousScene); // Define a cena anterior como a cena atual da Stage
            stage.show(); // Exibe a Stage com a nova cena
        } else {
            return; // Não faz nada se o histórico estiver vazio
        }
    }

    /**
     * Carrega e exibe uma nova tela (cena) a partir de um arquivo FXML.
     * Não executa nenhuma ação no controlador da nova tela e não define tela cheia.
     * @param fxmlPath O caminho para o arquivo FXML da nova tela.
     * @param event O evento que disparou a transição (usado para obter a Stage e a cena atual).
     */
    public static void switchToTela(String fxmlPath, Event event) {
        // Chama a versão mais completa do método com valores padrão para os parâmetros opcionais
        switchToTela(fxmlPath, event, null, false);
    }

    /**
     * Carrega e exibe uma nova tela (cena) a partir de um arquivo FXML.
     * Permite executar uma ação no controlador da nova tela após o carregamento.
     * Não define tela cheia.
     * @param <T> O tipo do controlador da nova tela.
     * @param fxmlPath O caminho para o arquivo FXML da nova tela.
     * @param event O evento que disparou a transição.
     * @param controllerConsumer Um Consumer que recebe o controlador da nova tela, permitindo configurá-lo.
     */
    public static <T> void switchToTela(String fxmlPath, Event event, Consumer<T> controllerConsumer) {
        // Chama a versão mais completa do método com valor padrão para o parâmetro fullScreen
        switchToTela(fxmlPath, event, controllerConsumer, false);
    }

    /**
     * Carrega e exibe uma nova tela (cena) a partir de um arquivo FXML, com opções de configuração.
     * Esta é a versão mais completa do método de transição de tela.
     * @param <T> O tipo do controlador da nova tela.
     * @param fxmlPath O caminho para o arquivo FXML da nova tela.
     * @param event O evento que disparou a transição.
     * @param controllerConsumer Um Consumer que recebe o controlador da nova tela, permitindo configurá-lo.
     * @param fullScreen Um booleano que indica se a nova tela deve ser exibida em modo tela cheia.
     */
    public static <T> void switchToTela(String fxmlPath, Event event, Consumer<T> controllerConsumer, boolean fullScreen) {
        try {
            // Cria um FXMLLoader para carregar o arquivo FXML especificado
            FXMLLoader loader = new FXMLLoader(NavigationController.class.getResource(fxmlPath));
            Parent root = loader.load(); // Carrega o FXML, criando a árvore de nós da interface

            // Obtém o controlador associado ao FXML carregado
            T controller = loader.getController();
            if (controllerConsumer != null) {
                // Se um Consumer foi fornecido, ele é executado para configurar o controlador (ex: passar dados)
                controllerConsumer.accept(controller);
            }

            // Salva a cena atual no histórico antes de mudar para a nova cena
            Scene currentScene = ((Node) event.getSource()).getScene();
            saveCurrentScene(currentScene);

            // Obtém a Stage (janela principal) a partir do evento
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root)); // Define a nova cena na Stage
            stage.setFullScreen(fullScreen); // Define se a Stage deve estar em tela cheia
            stage.show(); // Exibe a nova cena

        } catch (IOException e) {
            // Em caso de erro ao carregar o FXML, imprime o stack trace
            e.printStackTrace();
            // Logger.getLogger(NavigationController.class.getName()).log(Level.SEVERE, "Erro ao carregar FXML: " + fxmlPath, e);
        }
    }
}