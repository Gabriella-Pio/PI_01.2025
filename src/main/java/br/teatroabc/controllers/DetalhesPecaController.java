package br.teatroabc.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDate;

public class DetalhesPecaController {

    @FXML
    private Label tituloPeca;

    @FXML
    private ImageView imagemPeca;

    @FXML
    private Label descricaoPeca;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<String> choiceHorario;

    @FXML
    private ChoiceBox<String> choiceQuantidadeIngressos;

    public void initialize() {
        // Exemplo de preenchimento inicial
        tituloPeca.setText("A Comédia da Vida Privada");
        imagemPeca.setImage(new Image(getClass().getResourceAsStream("/Img/others.png")));
        descricaoPeca.setText("Uma peça emocionante que retrata os desafios e as alegrias da vida cotidiana.");
    }

    public void setDetalhes(String titulo, String descricao, String imagemPath) {
        tituloPeca.setText(titulo);
        descricaoPeca.setText(descricao);
        imagemPeca.setImage(new Image(getClass().getResourceAsStream(imagemPath)));
    }

    @FXML
    public void switchToEscolherPoltrona(ActionEvent event) {
        LocalDate selectedDate = datePicker.getValue();
        String selectedHorario = choiceHorario.getValue();
        String selectedIngressos = choiceQuantidadeIngressos.getValue();

        if (selectedDate == null || selectedHorario == null || selectedIngressos == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Informações Incompletas");
            alert.setHeaderText("Preenchimento Obrigatório");
            alert.setContentText("Por favor, preencha todos os campos obrigatórios antes de continuar.");
            alert.showAndWait();
        } else {
            int quantidadeIngressos = Integer.parseInt(selectedIngressos);
            NavigationController.switchToTela("/EscolherPoltrona.fxml", event,
                    (EscolherPoltronaController controller) -> controller.setQuantidadeIngressos(quantidadeIngressos));
        }
    }

}
