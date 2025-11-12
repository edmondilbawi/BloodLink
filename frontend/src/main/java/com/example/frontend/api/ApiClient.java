package com.example.frontend.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for sending HTTP requests from the JavaFX frontend to the backend API.
 * <p>
 * {@code ApiClient} acts as a lightweight HTTP wrapper that simplifies interaction
 * with REST endpoints (e.g., authentication, user management, blood requests).
 * It supports customizable headers, request methods, and body payloads in JSON format.
 *
 * <p><b>Core responsibilities:</b>
 * <ul>
 *   <li>Establish and manage HTTP connections via {@link HttpURLConnection}.</li>
 *   <li>Handle JSON serialization and deserialization using Jackson’s {@link ObjectMapper}.</li>
 *   <li>Attach authorization headers (JWT) when required.</li>
 *   <li>Provide a reusable, centralized helper for all frontend API calls.</li>
 * </ul>
 *
 * <p><b>Example usage:</b>
 * <pre>
 * String token = AuthApi.loginAndReturnToken("john@example.com", "password123");
 * String response = ApiClient.request(
 *     "GET",
 *     "http://localhost:8080/api/Users",
 *     null,
 *     ApiClient.authHeaders(token)
 * );
 * System.out.println(response);
 * </pre>
 *
 * @version 1.0
 * @see com.example.frontend.api.AuthApi
 */
public final class ApiClient {

    /**
     * Global Jackson object mapper for converting between JSON and Java objects.
     * Configured to ignore unknown properties to prevent mapping failures.
     */
    public static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /** Private constructor to prevent instantiation (utility class pattern). */
    private ApiClient() {}

    // ============================================================
    // Header Management
    // ============================================================

    /**
     * Builds a header map containing an Authorization header (if JWT provided)
     * and JSON content type.
     *
     * <p>This method ensures that requests to secure backend endpoints
     * include a correctly formatted {@code "Bearer <token>"} header.
     *
     * @param jwt the JSON Web Token (JWT) string
     * @return a map of headers including {@code Authorization} and {@code Content-Type}
     */
    public static Map<String, String> authHeaders(String jwt) {
        if (jwt == null || jwt.isBlank()) {
            return null;
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", ensureBearer(jwt));
        headers.put("Content-Type", "application/json");
        return headers;
    }

    /**
     * Ensures the JWT token string starts with the "Bearer " prefix.
     *
     * @param token the JWT token string
     * @return the formatted token with "Bearer " prefix
     */
    public static String ensureBearer(String token) {
        return token.startsWith("Bearer ") ? token : "Bearer " + token;
    }

    // ============================================================
    // HTTP Request Handling
    // ============================================================

    /**
     * Sends an HTTP request to the specified backend endpoint and returns the response body.
     *
     * <p>This method is universal — it supports all common HTTP methods such as:
     * <ul>
     *   <li>{@code GET} — for data retrieval.</li>
     *   <li>{@code POST} — for data creation or login/registration.</li>
     *   <li>{@code PUT} — for updates.</li>
     *   <li>{@code DELETE} — for deletions.</li>
     * </ul>
     *
     * <p><b>Behavior:</b>
     * <ul>
     *   <li>Automatically attaches headers if provided.</li>
     *   <li>Writes JSON request body for POST/PUT calls.</li>
     *   <li>Reads server responses or error streams depending on HTTP status.</li>
     * </ul>
     *
     * @param method  the HTTP method to use ("GET", "POST", "PUT", "DELETE")
     * @param urlStr  the full URL of the backend endpoint (e.g., {@code http://localhost:8080/api/Users})
     * @param body    the JSON string to send as the request body (nullable for GET/DELETE)
     * @param headers optional map of request headers (may include JWT)
     * @return the response body as a {@link String}, or {@code null} if the connection fails
     * @throws IOException if there is a problem with the network connection or I/O streams
     */
    public static String request(String method, String urlStr, String body, Map<String, String> headers) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);

        // Add headers if provided
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        // Write JSON request body if applicable
        if (body != null && !body.isBlank()) {
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }
        }

        // Read server response
        int responseCode = conn.getResponseCode();
        InputStream inputStream = (responseCode >= 200 && responseCode < 300)
                ? conn.getInputStream()
                : conn.getErrorStream();

        if (inputStream == null) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
}
