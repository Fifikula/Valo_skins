package org.example.app_val.events;

import org.example.app_val.model.Skin;

public class SkinFavoriteChangedEvent {
    private final Skin skin;

    public SkinFavoriteChangedEvent(Skin skin) {
        this.skin = skin;
    }

    public Skin getSkin() {
        return skin;
    }
}
