package com.example.frontend.Controller;

import com.example.frontend.api.AuthApi;
import com.example.frontend.api.ApiException;
import com.example.frontend.api.UsersApi;
import com.example.frontend.model.UserModel;
import com.example.frontend.utils.SceneNavigator;
import com.example.frontend.utils.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller for managing the Sign-Up (user registration) process.
 * <p>
 * The {@code SignUpController} handles all UI logic and validation for
 * creating a new user account in the application. It interacts with the
 * backend via {@link AuthApi} to register users and automatically log them in.
 *
 * <p><b>Associated FXML:</b> {@code SignUp-view.fxml}
 *
 * <p><b>Responsibilities:</b>
 * <ul>
 *   <li>Validate user input for all registration fields.</li>
 *   <li>Send registration data to the backend using {@link AuthApi#register(UserModel)}.</li>
 *   <li>Automatically log in the user after successful registration.</li>
 *   <li>Store the session token and user info in {@link Session}.</li>
 *   <li>Navigate between the Sign-Up and Login screens using {@link SceneNavigator}.</li>
 * </ul>
 *
 * @version 1.0
 * @see AuthApi
 * @see SceneNavigator
 * @see Session
 * @see UserModel
 */
public class SignUpController {

    // ============================================================
    // UI Components (linked via FXML)
    // ============================================================

    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private PasswordField passwordField;
    @FXML private TextField homeAddressField;
    @FXML private ComboBox<String> bloodTypeCombo;
    @FXML private ComboBox<String> rhesusCombo;

    // ============================================================
    // Initialization
    // ============================================================

    /**
     * Initializes the ComboBoxes for blood type and Rhesus factor when the scene is loaded.
     * <p>
     * Called automatically by the JavaFX framework after FXML elements are injected.
     */
    @FXML
    public void initialize() {
        bloodTypeCombo.setItems(FXCollections.observableArrayList("A", "B", "AB", "O"));
        rhesusCombo.setItems(FXCollections.observableArrayList("+", "-"));
    }

    // ============================================================
    // Event Handlers
    // ============================================================

    /**
     * Handles user registration when the "Sign Up" button is clicked.
     * <p>
     * Performs field-level validation, sends a registration request to the backend,
     * and logs the user in automatically if registration succeeds.
     * <p><b>Validation Rules:</b>
     * <ul>
     *   <li>All fields are mandatory.</li>
     *   <li>Email must be in a valid format.</li>
     *   <li>Phone must contain 7–15 digits (optional +country code).</li>
     *   <li>Password must be at least 6 characters long.</li>
     * </ul>
     *
     * <p><b>Process Flow:</b>
     * <ol>
     *   <li>Validate user input.</li>
     *   <li>Create a {@link UserModel} from the field data.</li>
     *   <li>Call {@link AuthApi#register(UserModel)} to register the user.</li>
     *   <li>If successful, call {@link AuthApi#login(String, String)} to retrieve a token.</li>
     *   <li>Store token and user in {@link Session}, then navigate to the dashboard.</li>
     *   <li>Display alerts for success or errors.</li>
     * </ol>
     *
     * <p><b>Example behavior:</b>
     * <pre>
     * Input:
     *   fullName = "Sarah Ahmed"
     *   email = "sarah@example.com"
     *   phone = "+971501234567"
     *   password = "securePass123"
     *   bloodType = "O"
     *   rhesus = "+"
     *
     * Output:
     *   ✅ Registration successful
     *   ✅ Auto-login successful
     *   Redirects to Dashboard-view.fxml
     * </pre>
     */
    @FXML
    private void handleSignUp() {
        try {
            // --- Field Validation ---
            if (fullNameField.getText().trim().isEmpty()) {
                showAlert("Error", "Full name is required.");
                return;
            }

            String email = emailField.getText().trim();
            if (email.isEmpty()) {
                showAlert("Error", "Email is required.");
                return;
            } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                showAlert("Error", "Please enter a valid email address.");
                return;
            }

            String phone = phoneField.getText().trim();
            if (phone.isEmpty()) {
                showAlert("Error", "Phone number is required.");
                return;
            } else if (!phone.matches("^\\+?[0-9]{7,15}$")) {
                showAlert("Error", "Please enter a valid phone number (7–15 digits, optional +country code).");
                return;
            }

            String password = passwordField.getText();
            if (password.isEmpty()) {
                showAlert("Error", "Password is required.");
                return;
            } else if (password.length() < 6) {
                showAlert("Error", "Password must be at least 6 characters long.");
                return;
            }

            if (homeAddressField.getText().trim().isEmpty()) {
                showAlert("Error", "Home address is required.");
                return;
            }

            if (bloodTypeCombo.getValue() == null) {
                showAlert("Error", "Please select a blood type.");
                return;
            }

            if (rhesusCombo.getValue() == null) {
                showAlert("Error", "Please select a Rhesus factor (+ or -).");
                return;
            }

            // --- Registration Logic ---
            UserModel user = new UserModel();
            user.setFullName(fullNameField.getText());
            user.setEmail(email);
            user.setPhone(phone);
            user.setPasswordHash(password);
            user.setRole("UNASSIGNED");
            user.setHomeAddress(homeAddressField.getText());
            user.setBloodType(bloodTypeCombo.getValue());
            user.setRhesus(rhesusCombo.getValue());

            boolean success = AuthApi.register(user);

            if (success) {
                String token = AuthApi.login(user.getEmail(), user.getPasswordHash());

                if (token != null) {
                    System.out.println("Auto-login successful. Token: " + token);
                    Session.setToken(token);
                    UserModel refreshed = UsersApi.findByEmail(user.getEmail());
                    Session.setCurrentUser(refreshed != null ? refreshed : user);
                    SceneNavigator.switchScene("Dashboard-view.fxml");
                } else {
                    showAlert("Error", "Account created, but auto-login failed. Please log in manually.");
                    SceneNavigator.switchScene("LogIn-view.fxml");
                }
            } else {
                showAlert("Error", "Registration failed. Please try again.");
            }

        } catch (ApiException e) {
            if (e.getResponseCode() == 400) {
                System.out.println("API Error: " + e.getMessage() + " | Code: " + e.getResponseCode());
                showAlert("Error", "Registration failed. Email already registered.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Navigates the user to the login screen.
     * <p>
     * Triggered when the "Already have an account?" or "Log In" button is clicked.
     */
    @FXML
    private void goToLogin() {
        try {

            SceneNavigator.switchScene("LogIn-view.fxml");
            System.out.println("Navigated to Login screen.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load Login view.");
        }
    }

    // ============================================================
    // Utility Methods
    // ============================================================

    /**
     * Displays an alert dialog to the user with a given title and message.
     *
     * @param title the title of the alert window
     * @param msg   the message text to display
     */
    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
