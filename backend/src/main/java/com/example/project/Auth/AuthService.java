package com.example.project.Auth;

import com.example.project.Users.User_Model;
import com.example.project.Users.User_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * AuthService handles the business logic for user authentication and registration.
 * <p>
 * This service interacts with the {@link User_Repository} for data persistence,
 * uses {@link BCryptPasswordEncoder} for password hashing, and
 * generates JWT tokens through {@link JwtUtil}.
 *
 * <p><b>Responsibilities:</b>
 * <ul>
 *   <li>Register new users with secure password hashing.</li>
 *   <li>Authenticate existing users using email and password verification.</li>
 *   <li>Generate and return JWT tokens upon successful registration or login.</li>
 *   <li>Build standardized user response objects for the API layer.</li>
 * </ul>
 *
 * <p>Typical usage via {@link AuthController} endpoints:
 * <ul>
 *   <li>POST /api/auth/register</li>
 *   <li>POST /api/auth/login</li>
 * </ul>
 *
 * @version 1.0
 */
@Service
public class AuthService {

    /**
     * Repository for performing CRUD operations on {@link User_Model}.
     */
    @Autowired
    private User_Repository userRepository;

    /**
     * Utility class for generating and validating JWT tokens.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Password encoder for hashing and verifying user passwords.
     */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Registers a new user and returns a response containing the generated JWT token and user info.
     *
     * <p>Steps performed:
     * <ol>
     *   <li>Checks if the email already exists in the database.</li>
     *   <li>Hashes the user password using BCrypt.</li>
     *   <li>Saves the new user record in the database.</li>
     *   <li>Generates a JWT token for the newly registered user.</li>
     *   <li>Builds and returns a standardized response.</li>
     * </ol>
     *
     * <p>Example input:
     * <pre>
     * {
     *   "fullName": "John Doe",
     *   "email": "john@example.com",
     *   "password": "Password123!",
     *   "phone": "0501234567",
     *   "role": "Donor",
     *   "homeAddress": "123 Main St",
     *   "bloodType": "A",
     *   "rhesus": "+"
     * }
     * </pre>
     *
     * @param request the {@link RegisterRequest} object containing user details.
     * @return a map containing the generated token and basic user info.
     * @throws RuntimeException if the email is already registered.
     */
    public Map<String, Object> registerAndReturnToken(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User_Model user = new User_Model();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setHomeAddress(request.getHomeAddress());
        user.setBloodType(request.getBloodType());
        user.setRhesus(request.getRhesus());

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return buildResponse(user, token);
    }

    /**
     * Authenticates a user by validating credentials and returning a new JWT token if valid.
     *
     * <p>Steps performed:
     * <ol>
     *   <li>Fetches the user by email from the database.</li>
     *   <li>Validates the password using BCrypt password matching.</li>
     *   <li>Generates a new JWT token.</li>
     *   <li>Builds and returns a response object containing token and user info.</li>
     * </ol>
     *
     * <p>Example input:
     * <pre>
     * {
     *   "email": "john@example.com",
     *   "password": "Password123!"
     * }
     * </pre>
     *
     * @param request the {@link LoginRequest} containing login credentials.
     * @return a map with token and user data.
     * @throws RuntimeException if the user is not found or the password is invalid.
     */
    public Map<String, Object> login(LoginRequest request) {
        Optional<User_Model> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User_Model user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return buildResponse(user, token);
    }

    /**
     * Constructs a consistent API response payload containing user details and JWT token.
     *
     * @param user  the authenticated or newly registered {@link User_Model}.
     * @param token the JWT token assigned to the user.
     * @return a map representing the standardized response.
     */
    private Map<String, Object> buildResponse(User_Model user, String token) {
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", user.getUserId());
        response.put("fullName", user.getFullName());
        response.put("email", user.getEmail());
        response.put("role", user.getRole());
        response.put("bloodType", user.getBloodType());
        response.put("rhesus", user.getRhesus());
        response.put("homeAddress", user.getHomeAddress());
        return response;
    }
}
