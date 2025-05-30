package org.example.app_val.model;

import org.example.app_val.services.Database;

public class SkinDetailsModel {
    private final Skin skin;
    private final Database db;

    public SkinDetailsModel(Skin skin, Database db) {
        this.skin = skin;
        this.db = db;
    }

    public Skin getSkin() {
        return skin;
    }

    public boolean isFavorite() {
        return db.isFavorite(skin);
    }

    public void toggleFavorite() {
        if (isFavorite()) {
            db.removeFromFavorites(skin);
        } else {
            db.addToFavorites(skin);
        }
    }
}
