package com.example.frontend.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Utility class for managing scene transitions within the JavaFX application.
 * <p>
 * The {@code SceneNavigator} acts as a centralized handler for switching between
 * FXML views while maintaining a single shared {@link Stage}. It simplifies
 * navigation logic by providing a static interface for controllers to trigger
 * scene changes without duplicating FXML loading code.
 *
 * <p><b>Typical Usage:</b>
 * <ul>
 *   <li>Called by controllers such as {@link com.example.frontend.Controller.LogInController}
 *       and {@link com.example.frontend.Controller.SignUpController} to change views.</li>
 *   <li>Used in the {@code start()} method of your main application to initialize the primary stage.</li>
 * </ul>
 *
 * <p><b>Example Usage:</b>
 * <pre>
 * // Set the global stage in your Application class
 * SceneNavigator.setStage(primaryStage);
 *
 * // Navigate to another view from any controller
 * SceneNavigator.switchScene("Dashboard-view.fxml");
 * </pre>
 *
 * <p><b>FXML Structure:</b>
 * All FXML files should be located in the project's {@code resources/Fxml/} directory.
 *
 * @version 1.0
 * @see javafx.stage.Stage
 * @see javafx.fxml.FXMLLoader
 * @see com.example.frontend.Controller.LogInController
 * @see com.example.frontend.Controller.SignUpController
 */
public class SceneNavigator {

    // ============================================================
    // Fields
    // ============================================================

    /** The global application stage shared across all controllers. */
    private static Stage stage;

    // ============================================================
    // Methods
    // ============================================================

    /**
     * Assigns the primary {@link Stage} for scene navigation.
     * <p>
     * This method should be called once in the main application’s
     * {@code start(Stage primaryStage)} method before any scenes are switched.
     *
     * @param s the JavaFX stage to assign as the global navigation container
     */
    public static void setStage(Stage s) {
        stage = s;
    }

    /**
     * Switches the currently displayed scene to the one defined by the given FXML file.
     * <p>
     * The method loads an FXML layout from the {@code /Fxml/} directory, applies it
     * to the shared {@link Stage}, and displays it.
     *
     * <p><b>Example:</b>
     * <pre>
     * SceneNavigator.switchScene("LogIn-view.fxml");
     * </pre>
     *
     * @param fxmlName the filename of the FXML file to load (e.g., "Dashboard-view.fxml")
     * @throws IOException if the FXML file cannot be found or loaded
     */
    public static void switchScene(String fxmlName) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource("/Fxml/" + fxmlName));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}
