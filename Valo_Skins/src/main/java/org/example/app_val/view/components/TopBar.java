package org.example.app_val.view.components;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.example.app_val.services.ConfigManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class TopBar extends HBox {

    private final Map<String, Button> buttonMap = new HashMap<>();

    public TopBar() {
        setPadding(new Insets(20, 20, 0, 20));
        setSpacing(20);
    }

    public void addButton(String id, String imagePath, Consumer<Button> onClick) {
        ImageView icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm()));
        icon.setFitWidth(26);
        icon.setFitHeight(26);

        Button button = new Button("", icon);
        button.setStyle(ConfigManager.get("button.style.default"));

        button.setOnMouseEntered(e -> {
            button.setStyle(ConfigManager.get("button.style.hover"));
            animateScale(icon, 1.0, 1.2);
        });

        button.setOnMouseExited(e -> {
            button.setStyle(ConfigManager.get("button.style.default"));
            animateScale(icon, 1.2, 1.0);
        });

        button.setOnAction(e -> onClick.accept(button));

        buttonMap.put(id, button);
        getChildren().add(button);
    }

    private void animateScale(ImageView imageView, double from, double to) {
        ScaleTransition st = new ScaleTransition(Duration.millis(150), imageView);
        st.setFromX(from);
        st.setFromY(from);
        st.setToX(to);
        st.setToY(to);
        st.play();
    }
}
