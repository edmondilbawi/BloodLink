package com.example.frontend.api;

import com.example.frontend.model.UserModel;
import java.util.Arrays;
import java.util.List;

/**
 * Provides frontend-level access to user-related API endpoints.
 * <p>
 * The {@code UsersApi} class enables JavaFX components to interact with
 * the backend’s {@code /api/users} endpoints for retrieving and deleting user records.
 * This class is designed for public or admin-level functionality and does not
 * require authentication headers (JWT).
 *
 * <p><b>Base URL:</b> {@code http://localhost:3002/api/users}
 *
 * <p><b>Supported Operations:</b>
 * <ul>
 *   <li><b>GET /api/users</b> — Retrieve a list of all registered users.</li>
 *   <li><b>DELETE /api/users/{userId}</b> — Delete a specific user by ID.</li>
 * </ul>
 *
 * <p><b>Usage example:</b>
 * <pre>
 * List&lt;UserModel&gt; users = UsersApi.getAllUsers();
 * for (UserModel u : users) {
 *     System.out.println(u.getFullName());
 * }
 *
 * boolean deleted = UsersApi.deleteUser(3L);
 * if (deleted) System.out.println("User deleted successfully");
 * </pre>
 *
 * @version 1.0
 * @see ApiClient
 * @see UserModel
 */
public class UsersApi {

    /** Base URL for user-related API endpoints. */
    private static final String BASE_URL = "http://localhost:3002/api/users";

    // ============================================================
    // GET: Fetch All Users
    // ============================================================

    /**
     * Fetches all users from the backend API.
     * <p>
     * This method performs an unauthenticated {@code GET} request to
     * {@code /api/users}. It deserializes the response into a list of
     * {@link UserModel} objects.
     *
     * <p><b>Example JSON response:</b>
     * <pre>
     * [
     *   {
     *     "userId": 1,
     *     "fullName": "John Doe",
     *     "email": "john@example.com",
     *     "role": "Donor",
     *     "bloodType": "A",
     *     "rhesus": "+"
     *   },
     *   {
     *     "userId": 2,
     *     "fullName": "Sarah Ahmed",
     *     "email": "sarah@example.com",
     *     "role": "Recipient",
     *     "bloodType": "O",
     *     "rhesus": "-"
     *   }
     * ]
     * </pre>
     *
     * @return a list of {@link UserModel} objects; returns an empty list if none are found or if an error occurs
     */
    public static List<UserModel> getAllUsers() {
        try {
            // No headers, no JWT — simple unauthenticated GET request
            String response = ApiClient.request("GET", BASE_URL, null, null);

            if (response != null && !response.isEmpty()) {
                UserModel[] users = ApiClient.MAPPER.readValue(response, UserModel[].class);
                return Arrays.asList(users);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    // ============================================================
    // DELETE: Remove User by ID
    // ============================================================

    /**
     * Deletes a user record from the backend API based on the provided ID.
     * <p>
     * Performs an unauthenticated {@code DELETE} request to
     * {@code /api/users/{userId}}.
     *
     * <p><b>Example request:</b>
     * <pre>
     * DELETE /api/users/5
     * </pre>
     *
     * <p><b>Expected response:</b> HTTP 204 (No Content)
     *
     * @param userId the unique identifier of the user to delete
     * @return {@code true} if the deletion succeeded, {@code false} otherwise
     */
    public static boolean deleteUser(Long userId) {
        try {
            String response = ApiClient.request("DELETE", BASE_URL + "/" + userId, null, null);
            System.out.println("Deleted user " + userId + ": " + response);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Convenience lookup to retrieve a user by email using the existing GET endpoint.
     *
     * @param email email address to search for
     * @return the matching {@link UserModel}, or {@code null} if not found
     */
    public static UserModel findByEmail(String email) {
        return getAllUsers().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}
