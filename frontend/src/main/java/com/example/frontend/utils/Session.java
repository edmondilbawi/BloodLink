package com.example.frontend.utils;

import com.example.frontend.model.UserModel;

/**
 * Manages the user's active session within the JavaFX application.
 * <p>
 * The {@code Session} class provides static methods to store and retrieve
 * authentication-related data such as the currently logged-in {@link UserModel}
 * and the associated JWT token. It acts as a simple in-memory session cache,
 * allowing any controller or API helper to access the authenticated user context.
 *
 * <p><b>Typical Responsibilities:</b>
 * <ul>
 *   <li>Store the authenticated {@link UserModel} after successful login or registration.</li>
 *   <li>Store and provide access to the user's JWT token for API authorization headers.</li>
 *   <li>Clear all session data on logout.</li>
 * </ul>
 *
 * <p><b>Typical Usage:</b>
 * <pre>
 * // After successful login
 * Session.setCurrentUser(user);
 * Session.setToken(jwtToken);
 *
 * // Access from anywhere in the app
 * UserModel loggedInUser = Session.getCurrentUser();
 * String jwt = Session.getToken();
 *
 * // Clear session on logout
 * Session.clear();
 * </pre>
 *
 * @version 1.0
 * @see com.example.frontend.api.AuthApi
 * @see com.example.frontend.Controller.LogInController
 * @see com.example.frontend.Controller.SignUpController
 */
public class Session {

    // ============================================================
    // Fields
    // ============================================================

    /** The currently authenticated user, or {@code null} if not logged in. */
    private static UserModel currentUser;

    /** The JSON Web Token (JWT) for authenticating API requests. */
    private static String token;

    // ============================================================
    // Accessors
    // ============================================================

    /**
     * Sets the current authenticated user for this session.
     *
     * @param user the {@link UserModel} representing the logged-in user
     */
    public static void setCurrentUser(UserModel user) {
        currentUser = user;
    }

    /**
     * Retrieves the currently authenticated user.
     *
     * @return the {@link UserModel} for the active session, or {@code null} if none exists
     */
    public static UserModel getCurrentUser() {
        return currentUser;
    }

    /**
     * Stores the JSON Web Token (JWT) for the current user session.
     *
     * @param jwt the JWT string returned by the backend
     */
    public static void setToken(String jwt) {
        token = jwt;
    }

    /**
     * Retrieves the stored JSON Web Token (JWT) for API authentication.
     *
     * @return the JWT string, or {@code null} if the user is not logged in
     */
    public static String getToken() {
        return token;
    }

    /**
     * Clears all session data, including the user object and token.
     * <p>
     * This method should be called on logout or when the session expires.
     */
    public static void clear() {
        currentUser = null;
        token = null;
    }
}
