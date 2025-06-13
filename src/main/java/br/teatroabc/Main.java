package br.teatroabc;

import javafx.application.Application;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCombination;


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

    public static void main(String[] args) {
        launch(args);


    }

}

//public class Main extends Application {
//    @Override
//    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("TelaInicial.fxml"));
//        Scene scene = new Scene(root);
//        stage.setTitle("Tela Inicial");
//        stage.setScene(scene);
//        stage.show();
//    }

//    public void main(String[] args) {
//        launch(args);
//        System.out.println(Main.class.getResource("/logo.png"));
//
//    }
//}

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        Stage stage = new Stage();
//        Image icon = new Image("logo.png");
//        stage.getIcons().add(icon);
//        stage.setTitle("Teatro ABC");
////        stage.setFullScreen(true);
////        stage.setFullScreenExitHint("YOU CANNOT SCAPE UNLESS YOU PRESS Q");
////        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));
////        stage.setWidth(1440);
////        stage.setHeight(1024);
////        stage.setResizable(false);
////        stage.setX(50);
////        stage.setY(50);
//
//        Group root = new Group();
//        Scene scene = new Scene(root, 1440, 1024, Color.BEIGE);
//
//        Text text = new Text();
//        text.setText("Teatro ABC");
//        text.setX(50);
//        text.setY(50);
//        text.setFont(Font.font("Poppins", FontWeight.LIGHT, 70));
//        text.setFill(Color.FIREBRICK);
////        text.setTextAlignment(TextAlignment.CENTER);
//
//        Line line = new Line();
//        line.setStartX(200);
//        line.setStartY(200);
//        line.setEndX(500);
//        line.setEndY(200);
//        line.setStrokeWidth(5);
//        line.setStroke(Color.ROSYBROWN);
//        line.setOpacity(0.8);
//        line.setRotate(45);
//        line.
//
//        root.getChildren().add(text);
//        root.getChildren().add(line);
//
//        stage.setScene(scene);
//        stage.show();
//    }
//}
