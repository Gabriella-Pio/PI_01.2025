package br.teatroabc.controllers;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PecasController {


    public void switchToDetalhes(Event event, String titulo, String descricao, String imagemPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetalhesPeca.fxml"));
            Parent root = loader.load();

            // Obtém o controlador da próxima tela
            DetalhesPecaController controller = loader.getController();

            // Passa os dados para o controlador
            controller.setDetalhes(titulo, descricao, imagemPath);

            // Configura a nova cena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToDetalhesPeca1(Event event) {
        switchToDetalhes(event, "Romeu e Julieta", "\"A história atemporal de amor proibido entre dois jovens apaixonados de famílias rivais. Uma peça repleta de romance, conflitos, segredos e sacrifícios que atravessa gerações, trazendo à tona as emoções mais profundas do amor e do ódio. Uma obra clássica de William Shakespeare que continua encantando públicos ao redor do mundo.\"", "/Img/pecaRJ.jpg");
    }

    public void switchToDetalhesPeca2(Event event) {
        switchToDetalhes(event, "Hamlet", "\"Um mergulho profundo nos dilemas da vingança, loucura e moralidade. 'Hamlet', uma das maiores tragédias de William Shakespeare, narra a jornada do Príncipe da Dinamarca em busca de justiça pela morte de seu pai. Repleta de intrigas, traições e monólogos inesquecíveis, esta peça provoca reflexões sobre a essência humana e a luta entre dever e desejo.\"", "/Img/pecaHamlet.jpeg");
    }

    public void switchToDetalhesPeca3(Event event) {
        switchToDetalhes(event, "Alice no País das Maravilhas", "\"Uma jornada mágica e surreal pelo mundo do imaginário. 'Alice no País das Maravilhas', baseado na obra icônica de Lewis Carroll, nos apresenta uma jovem curiosa que descobre um universo cheio de personagens fascinantes, como o Coelho Branco, o Chapeleiro Maluco e a Rainha de Copas. Uma aventura encantadora que celebra a criatividade e a coragem de sonhar.\"", "/Img/pecaAlice.jpg");
    }
}
