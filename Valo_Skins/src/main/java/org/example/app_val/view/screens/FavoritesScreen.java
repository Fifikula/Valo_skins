package org.example.app_val.view.screens;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import org.example.app_val.services.ConfigManager;
import org.example.app_val.view.components.TopBar;

public class FavoritesScreen extends VBox {

    private final FlowPane resultsBox;
    private final TopBar topBar;

    public FavoritesScreen() {
        setStyle("-fx-background-color: #B0203D;");
        resultsBox = new FlowPane(20, 20);
        resultsBox.setStyle("-fx-padding: 20;");

        ScrollPane scroll = new ScrollPane(resultsBox);
        scroll.setFitToWidth(true);
        scroll.setStyle(ConfigManager.get("layout.scrollpane.style"));

        topBar = new TopBar();

        getChildren().addAll(topBar, scroll);
    }

    public FlowPane getResultsBox() {
        return resultsBox;
    }

    public TopBar getTopBar() {
        return topBar;
    }

}
