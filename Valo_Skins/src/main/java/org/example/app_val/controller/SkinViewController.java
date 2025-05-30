package org.example.app_val.controller;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.app_val.services.Database;
import org.example.app_val.model.Skin;
import org.example.app_val.view.components.SkinView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SkinViewController {
    private final Skin skin;
    private final SkinView skinView;
    private static final ExecutorService imageLoaderExecutor = Executors.newFixedThreadPool(10);
    private final Database database;
    private final Stage owner;

    public SkinViewController(Skin skin, Database database, Stage owner) {
        this.skin = skin;
        this.skinView = new SkinView();
        this.database = database;
        this.owner = owner;

        initialize();
    }

    private void initialize() {
        skinView.setSkinName(skin.name());
        skinView.showLoading();

        loadImageAsync();

        skinView.getImageContainer().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> openSkinDetails());
    }

    private void loadImageAsync() {
        if (skin.imageUrl() != null && !skin.imageUrl().isEmpty()) {
            skinView.showLoading();

            Task<Image> loadImageTask = new Task<>() {
                @Override
                protected Image call() {
                    return skin.loadImage();
                }
            };

            loadImageTask.setOnSucceeded(event -> {
                Image image = loadImageTask.getValue();
                skinView.setImage(image);

                image.progressProperty().addListener((obs, oldProgress, newProgress) -> {
                    if (newProgress.doubleValue() >= 1.0) {
                        skinView.getImageView().setVisible(true);
                        skinView.showLoadingIndicator(false);
                    }
                });

                image.errorProperty().addListener((obs, wasError, isError) -> {
                    if (isError) {
                        skinView.showError();
                    }
                });
            });

            loadImageTask.setOnFailed(event -> skinView.showError());

            imageLoaderExecutor.submit(loadImageTask);
        }
    }

    private void openSkinDetails() {
        SkinDetailsController detailsController = new SkinDetailsController(skin, database, owner);
        detailsController.show();
    }
    public SkinView getSkinView() {
        return skinView;
    }

    public static void shutdownExecutor() {
        if (!imageLoaderExecutor.isShutdown()) {
            imageLoaderExecutor.shutdownNow();
        }
    }
}
