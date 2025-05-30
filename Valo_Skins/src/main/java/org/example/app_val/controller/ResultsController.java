package org.example.app_val.controller;

import javafx.concurrent.Task;
import javafx.stage.Stage;
import org.example.app_val.services.Database;
import org.example.app_val.model.Skin;
import org.example.app_val.model.ResultsModel;
import org.example.app_val.view.components.ResultsPane;
import org.example.app_val.view.components.SkinView;

import java.util.List;
import java.util.stream.Collectors;

public class ResultsController {

    private final ResultsModel resultsModel;
    private final ResultsPane resultsPane;
    private final Database database;
    private List<Skin> allSkins = null;

    private final Stage owner;

    public ResultsController(ResultsModel resultsModel, ResultsPane resultsPane, Stage owner) {
        this.resultsModel = resultsModel;
        this.resultsPane = resultsPane;
        this.database = new Database();
        this.owner = owner;
    }

    public void loadSkinsAsync(String query, String weaponId) {
        resultsPane.showLoading();

        if (allSkins != null) {
            showFilteredSkins(query, weaponId);
            return;
        }

        Task<List<Skin>> task = resultsModel.fetchSkinsAsync();

        task.setOnSucceeded(e -> {
            allSkins = task.getValue();
            showFilteredSkins(query, weaponId);
        });

        task.setOnFailed(e -> {
            resultsPane.showError();
            task.getException().printStackTrace();
        });

        new Thread(task).start();
    }

    private void showFilteredSkins(String query, String weaponId) {
        List<Skin> filteredSkins = resultsModel.filterSkins(allSkins, query, weaponId);

        List<SkinView> skinViews = filteredSkins.stream()
                .map(skin -> new SkinViewController(skin, database, owner).getSkinView())
                .collect(Collectors.toList());

        resultsPane.showSkins(skinViews);
    }
}
