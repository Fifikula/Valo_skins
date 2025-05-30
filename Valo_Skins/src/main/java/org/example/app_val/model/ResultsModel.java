package org.example.app_val.model;

import javafx.concurrent.Task;
import org.example.app_val.services.ValorantApiService;

import java.util.List;
import java.util.stream.Collectors;

public class ResultsModel {

    private final ValorantApiService apiService;

    public ResultsModel(ValorantApiService apiService) {
        this.apiService = apiService;
    }

    // Metoda do pobierania skórek z API
    public Task<List<Skin>> fetchSkinsAsync() {
        return new Task<>() {
            @Override
            protected List<Skin> call() {
                return apiService.fetchSkins();
            }
        };
    }

    // Filtrowanie skórek na podstawie zapytania i id broni
    public List<Skin> filterSkins(List<Skin> skins, String query, String weaponId) {
        return skins.stream()
                .filter(skin -> {
                    boolean matchesWeapon = (weaponId == null || skin.weaponId().equalsIgnoreCase(weaponId));
                    boolean matchesQuery = (query == null || query.isEmpty()
                            || skin.name().toLowerCase().contains(query.toLowerCase()));
                    return matchesWeapon && matchesQuery;
                })
                .collect(Collectors.toList());
    }
}
