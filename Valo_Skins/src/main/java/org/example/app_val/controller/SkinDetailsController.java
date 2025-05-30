package org.example.app_val.controller;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.app_val.events.SkinFavoriteChangedEvent;
import org.example.app_val.services.Database;
import org.example.app_val.model.Skin;
import org.example.app_val.model.SkinDetailsModel;
import org.example.app_val.view.components.SkinDetailsPopupView;
import org.example.app_val.events.GlobalEventBus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SkinDetailsController {
    private final Skin skin;
    private final SkinDetailsModel model;
    private final SkinDetailsPopupView view;
    private static final Map<String, Stage> openWindows = new ConcurrentHashMap<>();


    public SkinDetailsController(Skin skin, Database db, Stage owner) {
        this.skin = skin;
        this.model = new SkinDetailsModel(skin, db);
        this.view = new SkinDetailsPopupView(owner);

        initialize();
    }

    private void initialize() {
        view.setSkinName(model.getSkin().name());
        view.setFavoriteIcon(model.isFavorite());
        view.getFavoriteButton().setOnAction(e -> toggleFavorite());
        view.getExitButton().setOnAction(e -> view.close());

        loadSkinImage();
    }

    private void loadSkinImage() {
        if (skin.imageUrl() != null && !skin.imageUrl().isEmpty()){
            view.showLoading();
            Task<Image> loadImageTask = new Task<>() {
                @Override
                protected Image call() {
                    return model.getSkin().loadImage();
                }
            };
            loadImageTask.setOnSucceeded(event -> {
                Image image = loadImageTask.getValue();
                view.setSkinImage(image);

                image.progressProperty().addListener((obs, oldProgress, newProgress) -> {
                    if (newProgress.doubleValue() >= 1.0) {
                        view.hideLoading();
                    }
                });

                image.errorProperty().addListener((obs, wasError, isError) -> {
                    if (isError) {
                        view.hideLoading();
                    }
                });
            });
            loadImageTask.setOnFailed(event -> view.hideLoading());
            new Thread(loadImageTask).start();
        }
    }

    private void toggleFavorite() {
        model.toggleFavorite();
        view.setFavoriteIcon(model.isFavorite());
        GlobalEventBus.getBus().post(new SkinFavoriteChangedEvent(model.getSkin()));
    }

    public void show() {
        String skinId = model.getSkin().name();

        if (openWindows.containsKey(skinId)) {
            openWindows.get(skinId).toFront();
            return;
        }

        Stage popup = view.getStage();
        openWindows.put(skinId, popup);

        popup.setOnHidden(e -> openWindows.remove(skinId));
        popup.show();
    }
}
