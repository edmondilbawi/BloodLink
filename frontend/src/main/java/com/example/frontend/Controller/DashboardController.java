package com.example.frontend.Controller;

import com.example.frontend.utils.SceneNavigator;
import com.example.frontend.utils.Session;
import javafx.fxml.FXML;

/**
 * Controller for the application's main dashboard view.
 * <p>
 * The {@code DashboardController} handles UI interactions that occur
 * on the dashboard screen, such as selecting a role (Donor or Recipient)
 * and logging out of the application.
 *
 * <p>This controller is linked to the {@code Dashboard-view.fxml} file
 * and manages navigation through the {@link SceneNavigator} utility class.
 *
 * <p><b>Associated FXML:</b> {@code Dashboard-view.fxml}
 *
 * <p><b>Primary Responsibilities:</b>
 * <ul>
 *   <li>Handle user role selection (Donor or Recipient).</li>
 *   <li>Manage logout and navigation back to the login screen.</li>
 *   <li>Provide entry point for branching to donor or recipient dashboards (future expansion).</li>
 * </ul>
 *
 * @version 1.0
 * @see SceneNavigator
 */
public class DashboardController {

    // ============================================================
    // Event Handlers
    // ============================================================

    /**
     * Handles the event when the "Donor" role is selected.
     * <p>
     * Currently, this method only prints a console message,
     * but in the future, it can be expanded to:
     * <ul>
     *   <li>Load a donor-specific dashboard scene.</li>
     *   <li>Initialize donor data or session state.</li>
     *   <li>Trigger API calls to fetch donor information.</li>
     * </ul>
     */
    @FXML
    private void selectDonor() {
        try {
            SceneNavigator.switchScene("DonorProfile-view.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load donor profile view.");
        }
    }

    /**
     * Handles the event when the "Recipient" role is selected.
     * <p>
     * Currently prints to console, but may later:
     * <ul>
     *   <li>Navigate to a recipient dashboard.</li>
     *   <li>Initialize data specific to blood requests or pledges.</li>
     * </ul>
     */
    @FXML
    private void selectRecipient() {
        System.out.println("Role selected: RECIPIENT");
        // TODO: Navigate to recipient-specific dashboard if applicable
    }

    /**
     * Handles the logout event.
     * <p>
     * When the user clicks the "Logout" button, this method triggers
     * a scene change to the login screen using {@link SceneNavigator}.
     * <p>
     * <b>Associated FXML target:</b> {@code LogIn-view.fxml}
     *
     * <p><b>Behavior:</b>
     * <ul>
     *   <li>Ends the current session context (if implemented).</li>
     *   <li>Redirects the user back to the login scene.</li>
     * </ul>
     */
    @FXML
    private void handleLogout() {
        try {
            Session.clear();
            SceneNavigator.switchScene("LogIn-view.fxml");
            System.out.println("User logged out and redirected to login screen.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load login screen.");
        }
    }
}
