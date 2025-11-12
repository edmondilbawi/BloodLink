package com.example.frontend;

import com.example.frontend.utils.SceneNavigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LogInApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SceneNavigator.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/LogIn-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("BloodLink");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
