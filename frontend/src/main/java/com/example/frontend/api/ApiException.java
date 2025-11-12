package com.example.frontend.api;

/**
 * Custom runtime exception used to represent API-related errors
 * during communication between the frontend and the backend.
 * <p>
 * {@code ApiException} provides a simple mechanism for propagating
 * HTTP error details (status code + message) throughout the JavaFX frontend,
 * making it easier to handle failed network calls or backend rejections gracefully.
 *
 * <p><b>Typical scenarios:</b>
 * <ul>
 *   <li>Authentication failures (e.g., HTTP 401 Unauthorized).</li>
 *   <li>Validation errors (e.g., HTTP 400 Bad Request).</li>
 *   <li>Server-side issues (e.g., HTTP 500 Internal Server Error).</li>
 * </ul>
 *
 * <p><b>Example usage:</b>
 * <pre>
 * try {
 *     String response = ApiClient.request("GET", "http://localhost:8080/api/Users", null, headers);
 * } catch (IOException e) {
 *     throw new ApiException("Network error: " + e.getMessage(), 500);
 * }
 * </pre>
 *
 * @version 1.0
 */
public class ApiException extends RuntimeException {

    /**
     * The HTTP response status code returned by the backend.
     * <p>
     * Examples: 400 (Bad Request), 401 (Unauthorized), 404 (Not Found), 500 (Server Error)
     */
    private final int responseCode;

    /**
     * Constructs a new {@code ApiException} with a detailed error message and the backend's HTTP response code.
     *
     * @param message       the descriptive error message returned by the backend or generated locally
     * @param responseCode  the HTTP status code associated with the error
     */
    public ApiException(String message, int responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    /**
     * Retrieves the HTTP status code associated with this exception.
     *
     * @return the backend response code (e.g., 400, 401, 500)
     */
    public int getResponseCode() {
        return responseCode;
    }
}
