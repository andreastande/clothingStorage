module clothingStorage.ui {
    requires com.fasterxml.jackson.databind;

    requires java.net.http;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;


    requires clothingStorage.core;

    opens clothingStorage.ui to javafx.graphics, javafx.fxml;
}

