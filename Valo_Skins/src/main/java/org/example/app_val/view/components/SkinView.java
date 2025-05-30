package org.example.app_val.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.example.app_val.services.ConfigManager;

public class SkinView extends VBox {

    private final Label nameLabel;
    private final ImageView imageView;
    private final ProgressIndicator loadingIndicator;
    private final StackPane imageContainer;

    public SkinView() {
        setSpacing(10);
        setMinWidth(250);
        setMinHeight(400);
        setAlignment(Pos.CENTER);
        setStyle(ConfigManager.get("skinview.card.style"));
        setPadding(new Insets(10));
        setEffect(new DropShadow(10, Color.gray(0.3)));

        setFillWidth(true);
        setMaxWidth(Double.MAX_VALUE);

        nameLabel = new Label();
        nameLabel.setStyle(ConfigManager.get("skinview.label.name.style"));
        nameLabel.setWrapText(true);
        nameLabel.setAlignment(Pos.CENTER);

        imageContainer = new StackPane();
        imageContainer.setMaxWidth(Double.MAX_VALUE);
        imageContainer.prefWidthProperty().bind(widthProperty());

        VBox.setVgrow(imageContainer, javafx.scene.layout.Priority.ALWAYS);
        VBox.setMargin(nameLabel, new Insets(0, 0, 20, 0));

        loadingIndicator = new ProgressIndicator();
        loadingIndicator.setMaxSize(50, 50);
        StackPane.setAlignment(loadingIndicator, Pos.CENTER);

        imageView = new ImageView();
        imageView.fitWidthProperty().bind(widthProperty().subtract(20)); // Automatycznie dopasowuje do szerokości
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setVisible(false);

        setOnMouseEntered(e -> {
            setStyle(ConfigManager.get("skinview.card.hover.style"));
            animateScale(this, 1.0, 1.03);
        });

        setOnMouseExited(e -> {
            setStyle(ConfigManager.get("skinview.card.style"));
            animateScale(this, 1.03, 1.0);
        });

        imageContainer.getChildren().addAll(imageView, loadingIndicator);
        getChildren().addAll(imageContainer, nameLabel);
    }

    public void setSkinName(String name) {
        nameLabel.setText(name);
    }

    public void setImage(Image image) {
        imageView.setImage(image);
    }
    public ImageView getImageView() {
        return imageView;
    }

    private void animateScale(VBox node, double from, double to) {
        javafx.animation.ScaleTransition st = new javafx.animation.ScaleTransition(Duration.millis(150), node);
        st.setFromX(from);
        st.setFromY(from);
        st.setToX(to);
        st.setToY(to);
        st.play();
    }

    public void showLoading() {
        loadingIndicator.setVisible(true);
    }

    public void showError() {
        loadingIndicator.setVisible(false);
    }

    public void showLoadingIndicator(boolean show) {
        loadingIndicator.setVisible(show);
    }

    public StackPane getImageContainer() {
        return imageContainer;
    }
}
