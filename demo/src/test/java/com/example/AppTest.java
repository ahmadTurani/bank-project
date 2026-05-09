package com.example;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AppTest extends Application{
    @Override
    public void start(Stage primarStage){
        Pane root = new Pane();
        Scene scene = new Scene(root, 400, 300);
        primarStage.setTitle("aswd");
        primarStage.setScene(scene);
        primarStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
