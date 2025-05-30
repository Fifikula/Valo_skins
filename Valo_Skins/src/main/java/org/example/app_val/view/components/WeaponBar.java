package org.example.app_val.view.components;

import javafx.scene.layout.FlowPane;

public class WeaponBar extends FlowPane {

    public WeaponBar() {
        setHgap(10);
        setVgap(10);
        prefWrapLengthProperty().bind(widthProperty());
    }
}
