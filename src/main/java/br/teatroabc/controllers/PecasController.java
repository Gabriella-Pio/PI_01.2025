package br.teatroabc.controllers;

import br.teatroabc.utils.State;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;

import java.util.HashMap;
import java.util.Map;

public class PecasController {
    // Mapa estático para armazenar detalhes de cada peça, usando o ID da peça como chave
    private static final Map<String, PecaDetalhes> pecasDetalhesMap = new HashMap<>();

    // Bloco estático para inicializar o mapa com os detalhes das peças ao carregar a classe
    static {
        pecasDetalhesMap.put("peca1", new PecaDetalhes(
                "Romeu e Julieta",
                "\"A história atemporal de amor proibido entre dois jovens apaixonados de famílias rivais. Uma peça repleta de romance, conflitos, segredos e sacrifícios que atravessa gerações, trazendo à tona as emoções mais profundas do amor e do ódio. Uma obra clássica de William Shakespeare que continua encantando públicos ao redor do mundo.\"",
                "/Img/pecaRJ.png" // Caminho da imagem da peça
        ));
        pecasDetalhesMap.put("peca2", new PecaDetalhes(
                "Hamlet",
                "\"Um mergulho profundo nos dilemas da vingança, loucura e moralidade. 'Hamlet', uma das maiores tragédias de William Shakespeare, narra a jornada do Príncipe da Dinamarca em busca de justiça pela morte de seu pai. Repleta de intrigas, traições e monólogos inesquecíveis, esta peça provoca reflexões sobre a essência humana e a luta entre dever e desejo.\"",
                "/Img/pecaHamlet.png"
        ));
        pecasDetalhesMap.put("peca3", new PecaDetalhes(
                "Alice no País das Maravilhas",
                "\"Uma jornada mágica e surreal pelo mundo do imaginário. 'Alice no País das Maravilhas', baseado na obra icônica de Lewis Carroll, nos apresenta uma jovem curiosa que descobre um universo cheio de personagens fascinantes, como o Coelho Branco, o Chapeleiro Maluco e a Rainha de Copas. Uma aventura encantadora que celebra a criatividade e a coragem de sonhar.\"",
                "/Img/pecaAlice.png"
        ));
    }

    /**
     * Alterna para a tela de detalhes de uma peça específica.
     * @param event O evento que disparou a transição.
     * @param pecaId O ID da peça cujos detalhes serão exibidos.
     */
    public void switchToDetalhes(Event event, String pecaId) {
        // Obtém os detalhes da peça do mapa usando o ID
        PecaDetalhes detalhes = pecasDetalhesMap.get(pecaId);

        if (detalhes != null) {
            // Se os detalhes existirem, navega para a tela DetalhesPeca.fxml
            // e injeta os detalhes (título, descrição, imagem) no controlador de destino.
            NavigationController.switchToTela(
                    "/DetalhesPeca.fxml",
                    event,
                    (DetalhesPecaController controller) -> controller.setDetalhes(
                            detalhes.getTitulo(),
                            detalhes.getDescricao(),
                            detalhes.getImagemPath()
                    )
            );
        } else {
            // Exibe um erro se o ID da peça não for encontrado no mapa
            System.err.println("Detalhes da peça não encontrados para o ID: " + pecaId);
        }
    }

    /**
     * Manipula o clique em um "card" (botão/elemento clicável) de peça na interface.
     * Recupera o ID do card clicado, atualiza o estado global com a peça selecionada
     * e chama `switchToDetalhes` para exibir os detalhes da peça correspondente.
     * @param event O evento de clique.
     */
    @FXML
    private void handleCardClick(Event event) {
        // Obtém o nó (componente) que disparou o evento
        Node source = (Node) event.getSource();
        // Recupera o fx:id do componente clicado, que corresponde ao ID da peça (ex: "peca1")
        String cardId = source.getId();

        // Atualiza o estado global da aplicação com o título da peça selecionada
        State selecionar = Controller.getCurrentState();
        selecionar.setPeca(String.valueOf(pecasDetalhesMap.get(cardId).getTitulo()));
        Controller.setCurrentState(selecionar);

        // Usa um switch para chamar o método switchToDetalhes com o ID correto da peça
        // Isso poderia ser simplificado chamando diretamente switchToDetalhes(event, cardId);
        switch (cardId) {
            case "peca1":
                switchToDetalhes(event, "peca1");
                break;
            case "peca2":
                switchToDetalhes(event, "peca2");
                break;
            case "peca3":
                switchToDetalhes(event, "peca3");
                break;
            default:
                System.err.println("Card desconhecido: " + cardId);
        }
    }
}

/**
 * Classe auxiliar para encapsular os detalhes de uma peça teatral.
 * Contém o título, descrição e o caminho da imagem da peça.
 */
class PecaDetalhes {
    private final String titulo;
    private final String descricao;
    private final String imagemPath;

    /**
     * Construtor para criar uma nova instância de PecaDetalhes.
     * @param titulo O título da peça.
     * @param descricao A descrição da peça.
     * @param imagemPath O caminho para o arquivo de imagem da peça.
     */
    public PecaDetalhes(String titulo, String descricao, String imagemPath) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagemPath = imagemPath;
    }

    // Métodos getters para acessar os atributos da peça
    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getImagemPath() {
        return imagemPath;
    }
}