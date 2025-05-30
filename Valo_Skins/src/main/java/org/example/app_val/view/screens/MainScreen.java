package org.example.app_val.view.screens;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import org.example.app_val.services.ConfigManager;
import org.example.app_val.view.components.ResultsPane;
import org.example.app_val.view.components.SearchBar;
import org.example.app_val.view.components.TopBar;
import org.example.app_val.view.components.WeaponBar;

public class MainScreen extends VBox {

    private final TopBar topBar;
    private final HBox weaponBarContainer;
    private final ScrollPane scrollPane;
    private final ResultsPane resultsPane;
    private final SearchBar searchBar;
    private final WeaponBar weaponBar;

    public MainScreen() {
        setStyle("-fx-background-color: #B0203D;");
        topBar = new TopBar();

        weaponBar = new WeaponBar();
        weaponBarContainer = new HBox(weaponBar);
        weaponBarContainer.setPadding(new Insets(40, 0, 0, 40));
        HBox.setHgrow(weaponBar, Priority.ALWAYS);
        weaponBar.setMaxWidth(Double.MAX_VALUE);

        searchBar = new SearchBar();
        VBox.setMargin(searchBar, new Insets(10, 0, 0, 0));
        resultsPane = new ResultsPane();

        scrollPane = new ScrollPane(resultsPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(ConfigManager.get("layout.scrollpane.style"));

        getChildren().addAll(topBar, weaponBarContainer, searchBar, scrollPane);
    }

    public TopBar getTopBar() {
        return topBar;
    }

    public WeaponBar getWeaponBar() {
        return weaponBar;
    }

    public ResultsPane getResultsPane() {
        return resultsPane;
    }

    public SearchBar getSearchBar() {
        return searchBar;
    }

}
