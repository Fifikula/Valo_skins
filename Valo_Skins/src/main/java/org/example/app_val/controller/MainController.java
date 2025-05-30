package org.example.app_val.controller;


import javafx.stage.Stage;
import org.example.app_val.model.*;
import org.example.app_val.services.ValorantApiService;
import org.example.app_val.view.screens.MainScreen;


public class MainController {

    private final MainScreen mainView;
    private final ResultsController resultsController;
    private final NavigationModel navigationModel;

    public MainController(Stage primaryStage,  NavigationModel navigationModel) {
        this.mainView = new MainScreen();
        ValorantApiService apiService = new ValorantApiService();
        ResultsModel resultsModel = new ResultsModel(apiService);
        this.navigationModel = navigationModel;
        this.resultsController = new ResultsController(resultsModel, mainView.getResultsPane(), primaryStage);
        new SearchBarController(mainView.getSearchBar(),
                query -> resultsController.loadSkinsAsync(query, null));
        new WeaponBarController(apiService,
                weaponId -> resultsController.loadSkinsAsync(null, weaponId),
                mainView.getWeaponBar());

        initialize(primaryStage);
    }

    private void initialize(Stage primaryStage) {
        topBarSetup(primaryStage);
    }

    private void topBarSetup(Stage primaryStage) {
        mainView.getTopBar().addButton("home", "/images/home.png", btn -> navigationModel.showStartScreen());
        mainView.getTopBar().addButton("favorites", "/images/star_filled.png", btn -> navigationModel.showFavoritesScreen());
        mainView.getTopBar().addButton("exit", "/images/exit.png", btn -> primaryStage.close());
    }


    public MainScreen getMainScreen() {
        return mainView;
    }
}
