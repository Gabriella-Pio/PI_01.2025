package br.teatroabc;

import br.teatroabc.utils.CSVUtils;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {

            System.setProperty("javafx.verbose", "true");

            Parent root = FXMLLoader.load(getClass().getResource("/TelaInicial.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Teatro ABC");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method
    private static void createFileIfNotExists(Path path, String header) {
        if (!Files.exists(path)) {
            try {
                // Ensure parent directory exists
                Path parent = path.getParent();
                if (parent != null && !Files.exists(parent)) {
                    Files.createDirectories(parent);
                }
                // Write CSV header
                String[] headerArray = header.split(",\\s*");
                CSVUtils.writeCSV(path, headerArray);
            } catch (IOException e) {
                System.err.println("Failed to create " + path.getFileName());
                e.printStackTrace();
            }
        }
    }

    private static void Prepare() {
//        //Cria o CSV de base caso ele não exista
//        //baseado no cmainho relativo da pasta para compatibilidade entre sistemas
        String[] caminho = {"data", "BD"};

        Path caminhoCSVVenda = Paths.get(caminho[0], caminho[1], "Venda.csv");
        String headerVenda = "Id,IdCliente,IdItemVenda";

        Path caminhoCSVItemVenda = Paths.get(caminho[0], caminho[1], "ItemVenda.csv");
        String headerItemVenda = "id,poltronaId,areaId,sessaoId,pecaId,vendaId";

        Path caminhoCSVCliente = Paths.get(caminho[0], caminho[1], "Cliente.csv");
        String headerCliente = "id,nome,dataNascimento,telefone,endereco";

        createFileIfNotExists(caminhoCSVVenda, headerVenda);
        createFileIfNotExists(caminhoCSVItemVenda, headerItemVenda);
        createFileIfNotExists(caminhoCSVCliente, headerCliente);

    }

    public static void main(String[] args) {
        new Thread(Main::Prepare).start();
        launch(args);

    }
}
