package com.example.project.Auth;

/**
 * Data Transfer Object (DTO) representing a login request payload.
 * <p>
 * This class is used to receive user credentials from incoming HTTP requests
 * to the <b>/api/auth/login</b> endpoint.
 *
 * <p>Typical usage:
 * <pre>
 * {
 *   "email": "john@example.com",
 *   "password": "Password123!"
 * }
 * </pre>
 *
 * <p>Validated and processed by:
 * <ul>
 *   <li>{@link AuthController#login(LoginRequest)}</li>
 *   <li>{@link AuthService#login(LoginRequest)}</li>
 * </ul>
 *
 * @version 1.0
 */
public class LoginRequest {

    /**
     * The user's registered email address.
     */
    private String email;

    /**
     * The user's plaintext password to be verified during login.
     */
    private String password;

    /**
     * Returns the email provided by the user during login.
     *
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the email to assign
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the plaintext password entered by the user.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the password to assign
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
