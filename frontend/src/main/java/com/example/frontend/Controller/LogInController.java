package com.example.frontend.Controller;

import com.example.frontend.api.AuthApi;
import com.example.frontend.api.UsersApi;
import com.example.frontend.model.UserModel;
import com.example.frontend.utils.SceneNavigator;
import com.example.frontend.utils.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller for managing the login functionality of the application.
 * <p>
 * The {@code LogInController} handles user input on the login screen,
 * validates credentials, and communicates with the backend authentication API
 * through {@link AuthApi}. Upon successful authentication, it navigates the
 * user to the main dashboard view.
 *
 * <p><b>Associated FXML:</b> {@code LogIn-view.fxml}
 *
 * <p><b>Responsibilities:</b>
 * <ul>
 *   <li>Validate user input (email and password).</li>
 *   <li>Authenticate user using {@link AuthApi#login(String, String)}.</li>
 *   <li>Navigate to {@code Dashboard-view.fxml} upon success.</li>
 *   <li>Display alert dialogs for feedback and errors.</li>
 *   <li>Provide navigation to the registration (Sign-Up) screen.</li>
 * </ul>
 *
 * @version 1.0
 * @see AuthApi
 * @see SceneNavigator
 */
public class LogInController {

    // ============================================================
    // UI Components (linked via FXML)
    // ============================================================

    /** Field for entering the user's email address. */
    @FXML private TextField emailField;

    /** Field for entering the user's password (hidden input). */
    @FXML private PasswordField passwordField;

    /** Optional token field (currently unused). */
    @FXML private TextField tokenField;

    // ============================================================
    // Event Handlers
    // ============================================================

    /**
     * Handles the login button action.
     * <p>
     * Reads input from email and password fields, validates them,
     * and calls {@link AuthApi#login(String, String)} to authenticate the user.
     * If successful, the scene switches to {@code Dashboard-view.fxml}.
     * Otherwise, an error alert is displayed.
     *
     * <p><b>Behavior:</b>
     * <ul>
     *   <li>If email or password is empty → shows an error alert.</li>
     *   <li>If authentication succeeds → navigates to dashboard.</li>
     *   <li>If authentication fails → shows an error alert.</li>
     * </ul>
     */
    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter email and password.");
            return;
        }

        String token = AuthApi.login(email, password);

        if (token != null) {
            Session.setToken(token);
            UserModel currentUser = UsersApi.findByEmail(email);
            if (currentUser != null) {
                Session.setCurrentUser(currentUser);
            } else {
                System.err.println("Warning: logged in but user profile not found for " + email);
            }
            showAlert("Success", "Login successful!");
            try {
                SceneNavigator.switchScene("Dashboard-view.fxml");
                System.out.println("User logged in and redirected to dashboard.");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to navigate to dashboard.");
            }
        } else {
            showAlert("Error", "Invalid credentials or login failed.");
        }
    }

    /**
     * Navigates the user to the Sign-Up (registration) view.
     * <p>
     * Triggered when the "Create Account" or "Sign Up" button is pressed.
     * The method switches the active scene to {@code SignUp-view.fxml}.
     */
    @FXML
    private void goToSignUp() {
        try {
            SceneNavigator.switchScene("SignUp-view.fxml");
            System.out.println("➡Navigated to Sign-Up screen.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load Sign-Up view.");
        }
    }

    // ============================================================
    // Utility Methods
    // ============================================================

    /**
     * Displays an informational alert dialog to the user.
     *
     * @param title the title of the alert window
     * @param msg   the message content of the alert
     */
    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
