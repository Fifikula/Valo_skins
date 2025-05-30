package org.example.app_val;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.app_val.controller.SkinViewController;
import org.example.app_val.model.NavigationModel;
import javafx.scene.image.Image;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        StackPane rootContainer = new StackPane();
        Scene scene = new Scene(rootContainer, 1280, 720);

        NavigationModel navigationModel = new NavigationModel(primaryStage, rootContainer);
        navigationModel.showStartScreen();

        primaryStage.setTitle("Valorant Skin Viewer");
        primaryStage.getIcons().add(
                new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream("/images/Valorant_logo.jpeg"))
                )
        );
        primaryStage.setScene(scene);

        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");

        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        SkinViewController.shutdownExecutor();
    }

    public static void main(String[] args) {
        launch(args);
    }
}