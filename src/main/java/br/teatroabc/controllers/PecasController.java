package br.teatroabc.controllers;

import br.teatroabc.utils.State;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;

import java.util.HashMap;
import java.util.Map;

public class PecasController {
    private static final Map<String, PecaDetalhes> pecasDetalhesMap = new HashMap<>();

    static {
        pecasDetalhesMap.put("peca1", new PecaDetalhes(
                "Romeu e Julieta",
                "\"A história atemporal de amor proibido entre dois jovens apaixonados de famílias rivais. Uma peça repleta de romance, conflitos, segredos e sacrifícios que atravessa gerações, trazendo à tona as emoções mais profundas do amor e do ódio. Uma obra clássica de William Shakespeare que continua encantando públicos ao redor do mundo.\"",
                "/Img/pecaRJ.png"
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

    public void switchToDetalhes(Event event, String pecaId) {
        PecaDetalhes detalhes = pecasDetalhesMap.get(pecaId);

        if (detalhes != null) {
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
            System.err.println("Detalhes da peça não encontrados para o ID: " + pecaId);
        }
    }

    @FXML
    private void handleCardClick(Event event) {
        Node source = (Node) event.getSource();
        String cardId = source.getId(); // Recupera o fx:id do card clicado

        State selecionar = Controller.getCurrentState();
        selecionar.setPeca(String.valueOf(pecasDetalhesMap.get(cardId).getTitulo()));
        Controller.setCurrentState(selecionar);

        switch (cardId) {
            case "peca1":
                switchToDetalhes(event, "peca1"); // Chama o método no PecasController
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

class PecaDetalhes {
    private final String titulo;
    private final String descricao;
    private final String imagemPath;

    public PecaDetalhes(String titulo, String descricao, String imagemPath) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagemPath = imagemPath;
    }

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


