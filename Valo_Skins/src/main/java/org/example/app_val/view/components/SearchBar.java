package org.example.app_val.view.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import org.example.app_val.services.ConfigManager;

public class SearchBar extends HBox {

    private final TextField searchField;
    private final Button searchButton;

    public SearchBar() {
        setSpacing(20);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(25, 0, 25, 0));

        searchField = new TextField();
        searchField.setPromptText("Wpisz nazwę skina");
        searchField.setStyle(ConfigManager.get("searchBar.textField.style"));

        searchButton = new Button("Szukaj");
        searchButton.setStyle(ConfigManager.get("searchBar.button.style"));
        searchButton.setOnMouseEntered(e -> searchButton.setStyle(ConfigManager.get("searchBar.button.hoverStyle")));
        searchButton.setOnMouseExited(e -> searchButton.setStyle(ConfigManager.get("searchBar.button.style")));

        getChildren().addAll(searchButton, searchField);
    }

    public TextField getSearchField() {
        return searchField;
    }

    public Button getSearchButton() {
        return searchButton;
    }
}
