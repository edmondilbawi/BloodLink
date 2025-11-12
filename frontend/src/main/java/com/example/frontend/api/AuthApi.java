package com.example.frontend.api;

import com.example.frontend.model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Handles all authentication-related HTTP operations for the frontend,
 * including user registration and login.
 * <p>
 * The {@code AuthApi} class provides static methods for interacting with
 * the backend’s authentication endpoints. It manages JSON payload creation,
 * server communication, and response parsing — returning tokens or raising
 * {@link ApiException} when authentication fails.
 *
 * <p><b>Base URL:</b> {@code http://localhost:3002/api/auth}
 *
 * <p><b>Endpoints:</b>
 * <ul>
 *   <li><b>POST /api/auth/register</b> — Registers a new user account.</li>
 *   <li><b>POST /api/auth/login</b> — Authenticates user and returns JWT token.</li>
 * </ul>
 *
 * <p><b>Typical usage:</b>
 * <pre>
 * UserModel user = new UserModel("John Doe", "john@example.com", "0501234567", "pass123",
 *                                "Donor", "Dubai Marina", "A", "+");
 *
 * try {
 *     boolean success = AuthApi.register(user);
 *     if (success) {
 *         String token = AuthApi.login(user.getEmail(), user.getPasswordHash());
 *         System.out.println("Token: " + token);
 *     }
 * } catch (ApiException e) {
 *     System.err.println("Auth failed: " + e.getMessage() + " (code " + e.getResponseCode() + ")");
 * }
 * </pre>
 *
 * @version 1.0
 * @see ApiClient
 * @see ApiException
 */
public class AuthApi {

    /** Base URL for authentication endpoints. */
    private static final String BASE_URL = "http://localhost:3002/api/auth";

    /** Shared JSON mapper for serializing/deserializing responses. */
    private static final ObjectMapper mapper = ApiClient.MAPPER;

    // ============================================================
    // Registration
    // ============================================================

    /**
     * Registers a new user account with the backend API.
     * <p>
     * Constructs a JSON payload from the provided {@link UserModel} and sends a
     * POST request to {@code /api/auth/register}.
     * <p>
     * Returns {@code true} if the server responds with HTTP 200 or 201,
     * otherwise throws an {@link ApiException}.
     *
     * <p><b>Example request JSON:</b>
     * <pre>
     * {
     *   "fullName": "Sarah Ali",
     *   "email": "sarah@example.com",
     *   "phone": "0509876543",
     *   "password": "securePass456",
     *   "role": "Recipient",
     *   "homeAddress": "Abu Dhabi Corniche",
     *   "bloodType": "O",
     *   "rhesus": "-"
     * }
     * </pre>
     *
     * @param user the {@link UserModel} containing registration details
     * @return {@code true} if registration succeeded
     * @throws ApiException if the server returns an error response or the connection fails
     */
    public static boolean register(UserModel user) throws ApiException {
        try {
            URL url = new URL(BASE_URL + "/register");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Construct JSON manually for simplicity
            String jsonInput = String.format(
                    "{ \"fullName\":\"%s\", \"email\":\"%s\", \"phone\":\"%s\", " +
                            "\"password\":\"%s\", \"role\":\"%s\", \"homeAddress\":\"%s\", " +
                            "\"bloodType\":\"%s\", \"rhesus\":\"%s\" }",
                    user.getFullName(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getPasswordHash(),
                    user.getRole(),
                    user.getHomeAddress(),
                    user.getBloodType(),
                    user.getRhesus()
            );

            // Send JSON request
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();

            // Successful registration
            if (responseCode == 201 || responseCode == 200) {
                System.out.println("✅ Registration successful for: " + user.getEmail());
                return true;
            } else {
                System.out.println("❌ Registration failed. Code: " + responseCode);
                printError(conn);
                throw new ApiException("Registration failed", responseCode);
            }
        } catch (ApiException e) {
            throw e; // Re-throw for external handling
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("Unexpected error: " + e.getMessage(), -1);
        }
    }

    // ============================================================
    // Login
    // ============================================================

    /**
     * Logs in an existing user by sending their credentials to the backend API.
     * <p>
     * Sends a JSON payload with {@code email} and {@code password} to
     * {@code /api/auth/login}. If successful, the method extracts the JWT token
     * from the response and returns it as a string.
     *
     * <p><b>Example request JSON:</b>
     * <pre>
     * {
     *   "email": "sarah@example.com",
     *   "password": "securePass456"
     * }
     * </pre>
     *
     * <p><b>Example response:</b>
     * <pre>
     * {
     *   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
     * }
     * </pre>
     *
     * @param email    the user’s registered email
     * @param password the user’s plain-text password
     * @return a JWT token string if login succeeded; {@code null} otherwise
     */
    public static String login(String email, String password) {
        try {
            URL url = new URL(BASE_URL + "/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Construct and send JSON request body
            String body = String.format("{\"email\":\"%s\", \"password\":\"%s\"}", email, password);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                // Parse JSON response
                try (InputStream in = conn.getInputStream()) {
                    Map<?, ?> json = mapper.readValue(in, Map.class);
                    Object tokenObj = json.get("token");
                    if (tokenObj != null) {
                        System.out.println("✅ Login successful! Token: " + tokenObj);
                        return tokenObj.toString();
                    }
                }
            } else {
                System.out.println("❌ Login failed. Code: " + responseCode);
                printError(conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ============================================================
    // Error Handling
    // ============================================================

    /**
     * Reads and prints error messages returned by the backend.
     * <p>
     * This method is typically called when the HTTP response code is 4xx or 5xx.
     *
     * @param conn the {@link HttpURLConnection} instance used for the request
     */
    private static void printError(HttpURLConnection conn) {
        try (InputStream err = conn.getErrorStream()) {
            if (err != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(err, StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Server error: " + line);
                }
            }
        } catch (IOException ignored) {}
    }
}
