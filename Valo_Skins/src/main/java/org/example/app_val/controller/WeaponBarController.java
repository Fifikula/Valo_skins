package org.example.app_val.controller;

import javafx.concurrent.Task;
import org.example.app_val.model.Skin;
import org.example.app_val.services.ValorantApiService;
import org.example.app_val.view.components.WeaponBar;
import org.example.app_val.view.components.WeaponCard;

import java.util.List;
import java.util.function.Consumer;

public class WeaponBarController {
    private final ValorantApiService apiService;
    private final WeaponBar weaponBar;
    private final Consumer<String> onWeaponSelected;

    public WeaponBarController(ValorantApiService apiService, Consumer<String> onWeaponSelected, WeaponBar weaponBar) {
        this.apiService = apiService;
        this.weaponBar = weaponBar;
        this.onWeaponSelected = onWeaponSelected;

        loadWeaponSkinsAsync();
    }

    private void loadWeaponSkinsAsync() {
        Task<List<Skin>> loadTask = new Task<>() {
            @Override
            protected List<Skin> call() {
                return apiService.fetchWeaponSkins();  // Pobieramy dane z API
            }
        };

        loadTask.setOnSucceeded(e -> {
            // Po załadowaniu danych, tworzymy karty broni
            for (Skin skin : loadTask.getValue()) {
                WeaponCard card = new WeaponCard(skin);
                card.setOnClick(() -> onWeaponSelected.accept(skin.weaponId()));
                weaponBar.getChildren().add(card);
            }
        });

        loadTask.setOnFailed(e -> {
            loadTask.getException().printStackTrace();
        });

        new Thread(loadTask).start();
    }
}
