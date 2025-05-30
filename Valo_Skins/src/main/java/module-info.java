module org.example.app_val {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;
    requires com.google.common;


    opens org.example.app_val to javafx.fxml;
    exports org.example.app_val.controller to com.google.common;
    exports org.example.app_val;
    exports org.example.app_val.view.screens;
    opens org.example.app_val.view.screens to javafx.fxml;
    exports org.example.app_val.model;
    opens org.example.app_val.model to javafx.fxml;
    exports org.example.app_val.view.components;
    opens org.example.app_val.view.components to javafx.fxml;
    exports org.example.app_val.events;
    opens org.example.app_val.events to javafx.fxml;
    exports org.example.app_val.services;
    opens org.example.app_val.services to javafx.fxml;
}