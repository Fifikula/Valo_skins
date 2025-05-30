package org.example.app_val.controller;

import javafx.stage.Stage;
import org.example.app_val.events.SkinFavoriteChangedEvent;
import org.example.app_val.model.*;
import org.example.app_val.services.Database;
import org.example.app_val.view.components.SkinView;
import org.example.app_val.view.screens.FavoritesScreen;
import com.google.common.eventbus.Subscribe;
import org.example.app_val.events.GlobalEventBus;

import java.util.List;

public class FavoritesController {

    private final Stage primaryStage;
    private final FavoritesScreen favoritesView;
    private final Database db;
    private final NavigationModel navigationModel;

    public FavoritesController(Stage primaryStage, NavigationModel navigationModel) {
        this.primaryStage = primaryStage;
        this.favoritesView = new FavoritesScreen();
        this.db = new Database();
        this.navigationModel = navigationModel;
        GlobalEventBus.getBus().register(this);

        initialize();
    }

    private void initialize() {
        topBarSetup();
        loadFavorites();
    }

    @Subscribe
    public void onSkinFavoriteChanged(SkinFavoriteChangedEvent event) {
        reloadFavorites();
    }

    public void reloadFavorites() {
        favoritesView.getResultsBox().getChildren().clear();
        loadFavorites();
    }

    private void topBarSetup() {
        favoritesView.getTopBar().addButton("home", "/images/home.png", btn -> navigationModel.showStartScreen());
        favoritesView.getTopBar().addButton("search", "/images/search.png", btn -> navigationModel.showMainScreen());
        favoritesView.getTopBar().addButton("exit", "/images/exit.png", btn -> primaryStage.close());
    }


    private void loadFavorites() {
        List<Skin> favoriteSkins = db.getFavorites();
        for (Skin skin : favoriteSkins) {
            SkinView skinView = new SkinViewController(skin, db, primaryStage).getSkinView();
            skinView.setPrefWidth(600);
            favoritesView.getResultsBox().getChildren().add(skinView);
        }
    }

    public FavoritesScreen getFavoritesScreen() {
        return favoritesView;
    }
}
