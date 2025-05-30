package org.example.app_val.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.app_val.services.ConfigManager;
import org.example.app_val.model.Skin;
import javafx.geometry.Insets;

public class WeaponCard extends VBox {

    private final Skin skin;

    public WeaponCard(Skin skin) {
        this.skin = skin;
        setAlignment(Pos.CENTER);
        setSpacing(6);
        setPrefSize(85, 90);
        setStyle(ConfigManager.get("weaponcard.normal.style"));

        // Ustawiamy obrazek
        ImageView iconView = new ImageView();
        if (skin.imageUrl() != null && !skin.imageUrl().isEmpty()) {
            Image img = new Image(skin.imageUrl(), true);
            iconView.setImage(img);
        }
        iconView.setPreserveRatio(true);
        iconView.setFitWidth(60);
        iconView.setSmooth(true);

        // Ustawiamy nazwę broni
        Label nameLabel = new Label(extractWeaponNameFromSkinName(skin.name()));
        nameLabel.setStyle(ConfigManager.get("weaponcard.label.name.style"));
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(70);
        nameLabel.setAlignment(Pos.CENTER);
        VBox.setMargin(nameLabel, new Insets(5, 0, 0, 0));

        getChildren().addAll(iconView, nameLabel);

        // Obsługa efektów wizualnych
        setOnMouseEntered(e -> {
            setStyle(ConfigManager.get("weaponcard.hover.style"));
            setScaleX(1.05);
            setScaleY(1.05);
        });
        setOnMouseExited(e -> {
            setStyle(ConfigManager.get("weaponcard.normal.style"));
            setScaleX(1.0);
            setScaleY(1.0);
        });
    }

    private String extractWeaponNameFromSkinName(String skinName) {
        if (skinName == null || skinName.isEmpty()) return "Nieznana";
        String[] parts = skinName.trim().split(" ");
        return parts.length > 1 ? parts[parts.length - 1] : skinName;
    }

    public Skin getSkin() {
        return skin;
    }

    public void setOnClick(Runnable onClick) {
        setOnMouseClicked(e -> onClick.run());
    }
}
