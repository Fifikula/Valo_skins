package org.example.app_val.model;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.example.app_val.controller.FavoritesController;
import org.example.app_val.controller.MainController;
import org.example.app_val.controller.StartScreenController;
import org.example.app_val.view.screens.StartScreen;
import org.example.app_val.view.screens.FavoritesScreen;

public class NavigationModel {

    private final Stage primaryStage;
    private final StackPane rootContainer;

    public NavigationModel(Stage primaryStage, StackPane rootContainer) {
        this.primaryStage = primaryStage;
        this.rootContainer = rootContainer;
    }
    // Przechodzi do ekranu startowego
    public void showStartScreen() {
        StartScreenController startScreenController = new StartScreenController(primaryStage, this);
        rootContainer.getChildren().setAll(startScreenController.getStartScreen());
    }

    // Przechodzi do ekranu głównego
    public void showMainScreen() {
        MainController mainController = new MainController(primaryStage, this);
        rootContainer.getChildren().setAll(mainController.getMainScreen());
    }

    // Przechodzi do ekranu ulubionych
    public void showFavoritesScreen() {
        FavoritesController favoritesController = new FavoritesController(primaryStage, this);
        rootContainer.getChildren().setAll(favoritesController.getFavoritesScreen());
    }
}
