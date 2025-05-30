package org.example.app_val.view.components;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import org.example.app_val.services.ConfigManager;

import java.util.Objects;

public class SkinDetailsPopupView {
    private final Stage stage;
    private final Label nameLabel;
    private final ImageView imageView;
    private final ProgressIndicator loadingIndicator;
    private final Button favoriteButton;
    private final Button exitButton;

    public SkinDetailsPopupView(Window owner) {
        this.stage = new Stage();
        this.nameLabel = new Label();
        this.imageView = new ImageView();
        this.loadingIndicator = new ProgressIndicator();
        this.favoriteButton = new Button();
        this.exitButton = new Button("Zamknij");

        initializeView(owner);
    }

    private void initializeView(Window owner) {
        stage.setTitle("Szczegóły skina");
        stage.setResizable(false);
        stage.initOwner(owner);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Valorant_logo.jpeg"))));

        nameLabel.setStyle(ConfigManager.get("popup.label.name.style"));
        nameLabel.setAlignment(Pos.CENTER);

        ImageView favoriteIcon = new ImageView();
        favoriteIcon.setFitWidth(24);
        favoriteIcon.setFitHeight(24);
        favoriteButton.setGraphic(favoriteIcon);
        favoriteButton.setStyle(ConfigManager.get("popup.favorite.button.style"));
        favoriteButton.setPrefSize(40, 40);

        addHoverEffect(favoriteButton, favoriteIcon);

        BorderPane topBar = new BorderPane();
        topBar.setPadding(new Insets(5));
        topBar.setCenter(nameLabel);
        topBar.setRight(favoriteButton);

        StackPane imageContainer = new StackPane(imageView, loadingIndicator);
        imageContainer.setPrefSize(600, 400);
        imageContainer.setStyle(ConfigManager.get("popup.image.container.style"));

        exitButton.setStyle(ConfigManager.get("popup.exit.button.style"));
        addHoverEffect(exitButton, null);

        VBox content = new VBox(10, topBar, imageContainer, exitButton);
        content.setStyle(ConfigManager.get("popup.content.style"));
        content.setAlignment(Pos.CENTER);

        Scene scene = new Scene(content, 900, 770);
        stage.setScene(scene);
    }
    private void addHoverEffect(Button button, ImageView icon) {
        DropShadow glow = new DropShadow(20, Color.WHITE);
        glow.setSpread(0.3);

        button.setOnMouseEntered(e -> {
            if (icon != null) animateScale(icon, 1.0, 1.3);
            else animateScale(button, 1.0, 1.1);
            button.setEffect(glow);
        });

        button.setOnMouseExited(e -> {
            if (icon != null) animateScale(icon, 1.3, 1.0);
            else animateScale(button, 1.1, 1.0);
            button.setEffect(null);
        });
    }

    private void animateScale(javafx.scene.Node node, double from, double to) {
        ScaleTransition st = new ScaleTransition(Duration.millis(150), node);
        st.setFromX(from);
        st.setFromY(from);
        st.setToX(to);
        st.setToY(to);
        st.play();
    }

    public void setSkinName(String name) {
        nameLabel.setText(name);
    }

    public void setSkinImage(Image image) {
        imageView.setImage(image);
        imageView.maxHeight(200);
    }
    public void showLoading() {
        loadingIndicator.setVisible(true);
    }

    public void hideLoading() {
        loadingIndicator.setVisible(false);
    }

    public void setFavoriteIcon(boolean isFavorite) {
        String iconPath = isFavorite ? "/images/star_filled.png" : "/images/star_empty.png";
        ImageView icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath))));
        icon.setFitWidth(24);
        icon.setFitHeight(24);
        favoriteButton.setGraphic(icon);
        addHoverEffect(favoriteButton, icon);
    }
    public Button getFavoriteButton() {
        return favoriteButton;
    }
    public Button getExitButton() {
        return exitButton;
    }
    public Stage getStage() {
        return stage;
    }
    public void close() {
        stage.close();
    }
}
