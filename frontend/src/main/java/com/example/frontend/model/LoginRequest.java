package com.example.frontend.model;

/**
 * Represents the login request payload sent from the frontend to the backend.
 * <p>
 * The {@code LoginRequest} model encapsulates a user's credentials
 * (email and password) and is used to authenticate users through
 * the backend {@code /api/auth/login} endpoint.
 *
 * <p><b>Associated Backend DTO:</b> {@code com.example.project.Auth.LoginRequest}
 *
 * <p><b>Example JSON payload:</b>
 * <pre>
 * {
 *   "email": "john.doe@example.com",
 *   "password": "SecurePass123"
 * }
 * </pre>
 *
 * <p><b>Typical Usage:</b>
 * <ul>
 *   <li>Serialized and sent via {@link com.example.frontend.api.AuthApi#login(String, String)}.</li>
 *   <li>Used within the {@link com.example.frontend.Controller.LogInController} to pass credentials.</li>
 * </ul>
 *
 * @version 1.0
 * @see com.example.frontend.api.AuthApi
 * @see com.example.frontend.Controller.LogInController
 */
public class LoginRequest {

    // ============================================================
    // Fields
    // ============================================================

    /** The user's registered email address. */
    private String email;

    /** The user's plaintext password (to be securely sent to the backend). */
    private String password;

    // ============================================================
    // Constructors
    // ============================================================

    /** Default no-argument constructor (required for JSON serialization). */
    public LoginRequest() {}

    /**
     * Constructs a {@code LoginRequest} with both email and password fields initialized.
     *
     * @param email    the user's email address
     * @param password the user's plaintext password
     */
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // ============================================================
    // Getters and Setters
    // ============================================================

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
