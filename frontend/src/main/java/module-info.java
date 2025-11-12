module com.example.frontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.net.http;               // if your Api uses HttpClient
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    opens com.example.frontend.Controller to javafx.fxml;
    opens com.example.frontend.model to com.fasterxml.jackson.databind;

    exports com.example.frontend;
}
