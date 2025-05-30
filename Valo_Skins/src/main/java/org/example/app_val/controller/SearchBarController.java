package org.example.app_val.controller;

import javafx.scene.input.KeyCode;
import org.example.app_val.view.components.SearchBar;
import java.util.function.Consumer;

public class SearchBarController {

    private final SearchBar searchBar;
    private final Consumer<String> onSearch;

    public SearchBarController(SearchBar searchBar, Consumer<String> onSearch) {
        this.searchBar = searchBar;
        this.onSearch = onSearch;
        initialize();
    }

    private void initialize() {
        Runnable searchAction = () -> {
            String query = searchBar.getSearchField().getText().trim();
            if (!query.isEmpty()) {
                onSearch.accept(query);
            } else {
                System.out.println("Zapytanie jest puste.");
            }
        };

        searchBar.getSearchButton().setOnAction(event -> searchAction.run());

        searchBar.getSearchField().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                searchAction.run();
            }
        });
    }
}
