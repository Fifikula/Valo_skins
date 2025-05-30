package org.example.app_val.view.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.app_val.services.ConfigManager;

public class StartScreen extends VBox {

    private final Button searchButton = createButton("SZUKAJ", "/images/search.png");
    private final Button favoritesButton = createButton("ULUBIONE", "/images/star_filled.png");
    private final Button exitButton = createButton("WYJDŹ", "/images/exit.png");

    public StartScreen() {
        setStyle("-fx-background-color: #B0203D; -fx-padding: 20 50 50 50;");
        buildUI();
    }

    private void buildUI() {
        // Stworzenie logo i tytułu
        ImageView logoView = createLogo();
        Label titleLabel = createTitleLabel();
        VBox titleBox = createTitleBox(logoView, titleLabel);

        // Kontener na przyciski
        VBox buttonsBox = new VBox(15, searchButton, favoritesButton, exitButton);

        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setSpacing(20);

        // Spacer na górze i dole, aby wyśrodkować przyciski w pionie
        Region spacerTop = new Region();
        Region spacerBottom = new Region();
        VBox.setVgrow(spacerTop, Priority.ALWAYS);
        VBox.setVgrow(spacerBottom, Priority.ALWAYS);

        getChildren().addAll(titleBox, spacerTop, buttonsBox, spacerBottom);
    }

    private ImageView createLogo() {
        ImageView logoView = new ImageView(new Image(getClass().getResourceAsStream("/images/Valorant_logo.jpeg")));
        logoView.setFitHeight(120);
        logoView.setFitWidth(120);
        logoView.setPreserveRatio(true);

        DropShadow glow = new DropShadow();
        glow.setColor(Color.GOLD);
        glow.setRadius(30);
        glow.setSpread(0.5);
        logoView.setEffect(glow);

        return logoView;
    }

    private Label createTitleLabel() {
        Label titleLabel = new Label("VALO SKINS");
        titleLabel.setStyle("-fx-text-fill: gold; -fx-font-size: 80px; -fx-font-weight: bold;");
        titleLabel.setAlignment(Pos.CENTER);
        return titleLabel;
    }

    private VBox createTitleBox(ImageView logoView, Label titleLabel) {
        HBox.setMargin(logoView, new Insets(0, 30, 0, 0));

        HBox titleContainer = new HBox(logoView, titleLabel);
        titleContainer.setAlignment(Pos.CENTER);
        titleContainer.setPadding(new Insets(30, 0, 0, 0));

        return new VBox(titleContainer);
    }

    private Button createButton(String text, String iconPath) {
        Button button = new Button(text);
        button.setStyle(ConfigManager.get("button.start.style"));

        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
        icon.setFitHeight(100);
        icon.setFitWidth(100);
        button.setGraphic(icon);
        button.setContentDisplay(javafx.scene.control.ContentDisplay.RIGHT);

        // Animacja hover
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            button.setScaleX(1.1);
            button.setScaleY(1.1);
            button.setEffect(new DropShadow(10, Color.GOLD));
        });

        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            button.setScaleX(1.0);
            button.setScaleY(1.0);
            button.setStyle(ConfigManager.get("button.start.style"));
            button.setEffect(null);
        });return button;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public Button getFavoritesButton() {
        return favoritesButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}
