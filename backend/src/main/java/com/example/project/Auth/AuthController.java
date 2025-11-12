package com.example.project.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * AuthController handles user authentication operations such as registration and login.
 * <p>
 * This REST controller exposes two endpoints under the base URL path <b>/api/auth</b>:
 * <ul>
 *     <li><b>POST /register</b> — Creates a new user account and returns a token upon success.</li>
 *     <li><b>POST /login</b> — Authenticates an existing user and returns a JWT token.</li>
 * </ul>
 *
 * <p>All endpoints are CORS-enabled to allow cross-origin requests.
 *
 * <p><b>Dependencies:</b> Uses {@link AuthService} for the underlying authentication logic.
 *
 * @author
 * @version 1.0
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    /**
     * Service layer handling authentication and registration logic.
     */
    @Autowired
    private AuthService authService;

    /**
     * Handles user registration and returns a JWT token if successful.
     *
     * <p>Expected JSON body:
     * <pre>
     * {
     *   "fullName": "John Doe",
     *   "email": "john@example.com",
     *   "password": "SecurePass123!"
     * }
     * </pre>
     *
     * <p>Returns:
     * <ul>
     *     <li>200 OK with token and user info if registration succeeds</li>
     *     <li>400 Bad Request with error message if registration fails</li>
     * </ul>
     *
     * @param request The registration details (name, email, password).
     * @return ResponseEntity containing either the registration result or an error message.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(authService.registerAndReturnToken(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    /**
     * Handles user login by verifying credentials and returning a JWT token.
     *
     * <p>Expected JSON body:
     * <pre>
     * {
     *   "email": "john@example.com",
     *   "password": "SecurePass123!"
     * }
     * </pre>
     *
     * <p>Returns:
     * <ul>
     *     <li>200 OK with token and user info if credentials are valid</li>
     *     <li>401 Unauthorized if credentials are invalid</li>
     *     <li>500 Internal Server Error for unexpected exceptions</li>
     * </ul>
     *
     * @param request The login credentials (email and password).
     * @return ResponseEntity containing login result or an error message.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Map<String, Object> response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Internal server error: " + e.getMessage()));
        }
    }
}
