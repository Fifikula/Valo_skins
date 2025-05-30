package org.example.app_val.controller;

import javafx.stage.Stage;
import org.example.app_val.model.NavigationModel;
import org.example.app_val.view.screens.StartScreen;

public class StartScreenController {

    private final StartScreen startScreen;
    private final NavigationModel navigationModel;

    public StartScreenController(Stage primaryStage, NavigationModel navigationModel) {
        this.startScreen = new StartScreen();
        this.navigationModel = navigationModel;

        initializeActions(primaryStage);
    }

    private void initializeActions(Stage primaryStage) {
        startScreen.getSearchButton().setOnAction(e -> onSearchButtonClicked());
        startScreen.getFavoritesButton().setOnAction(e -> onFavoritesButtonClicked());
        startScreen.getExitButton().setOnAction(e -> primaryStage.close());
    }

    private void onSearchButtonClicked() {
        navigationModel.showMainScreen();
    }

    private void onFavoritesButtonClicked() {
        navigationModel.showFavoritesScreen();
    }

    public StartScreen getStartScreen() {
        return startScreen;
    }
}
