package org.example.app_val.view.components;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import org.example.app_val.services.ConfigManager;

import java.util.List;

public class ResultsPane extends FlowPane {

    public ResultsPane() {
        setHgap(20);
        setVgap(20);
        setStyle(ConfigManager.get("resultsPane.style"));
        setVisible(false);

        // Responsywność: dostosuj szerokość SkinView do dostępnej szerokości
        widthProperty().addListener((obs, oldWidth, newWidth) -> updateCardWidths());
    }

    private void updateCardWidths() {
        double availableWidth = getWidth();
        if (availableWidth <= 0) return;

        double scrollbarWidth = 30; // przybliżona szerokość scrollbara
        double effectiveWidth = availableWidth - scrollbarWidth;

        double minCardWidth = 250;
        double cardWidth = getCardWidth(effectiveWidth, minCardWidth);

        for (Node node : getChildren()) {
            if (node instanceof SkinView view) {
                view.setPrefWidth(Math.max(cardWidth, minCardWidth));
            }
        }
    }

    private double getCardWidth(double effectiveWidth, double minCardWidth) {
        int maxColumns = 4;

        // Oblicz ile kolumn się mieści
        int columns = Math.min(maxColumns, (int) ((effectiveWidth + getHgap()) / (minCardWidth + getHgap())));
        columns = Math.max(columns, 1);

        double totalGap = getHgap() * (columns - 1);
        double cardWidth = (effectiveWidth - totalGap) / columns;

        while (cardWidth < minCardWidth && columns > 1) {
            columns--;
            totalGap = getHgap() * (columns - 1);
            cardWidth = (effectiveWidth - totalGap) / columns;
        }
        return cardWidth;
    }


    public void showLoading() {
        setVisible(true);
        getChildren().setAll(new Label("🔄 Ładowanie skinów..."));
    }

    public void showSkins(List<? extends Node> skinViews) {
        getChildren().setAll(skinViews);
        updateCardWidths();
    }

    public void showError() {
        getChildren().setAll(new Label("❌ Błąd przy ładowaniu skinów"));
    }
}
