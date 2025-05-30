package org.example.app_val.model;

import javafx.scene.image.Image;

public record Skin(String name, String imageUrl, String uuid, String weaponId) {

    public Image loadImage() {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            return new Image(imageUrl, true);
        }
        return null;
    }
}
