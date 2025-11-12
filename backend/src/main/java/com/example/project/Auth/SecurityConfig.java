package com.example.project.Auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for Spring Security.
 * <p>
 * This class defines authentication and authorization settings
 * for the application. It ensures secure password encoding and configures
 * HTTP request permissions for authentication endpoints.
 *
 * <p><b>Responsibilities:</b>
 * <ul>
 *   <li>Provide a {@link PasswordEncoder} bean for secure password hashing.</li>
 *   <li>Expose an {@link AuthenticationManager} for authentication processes.</li>
 *   <li>Define the global {@link SecurityFilterChain} to manage HTTP security rules.</li>
 * </ul>
 *
 * <p><b>Key Security Decisions:</b>
 * <ul>
 *   <li>Disables CSRF (Cross-Site Request Forgery) protection — suitable for stateless REST APIs.</li>
 *   <li>Sets session management to <b>STATELESS</b>, as authentication is handled by JWT tokens.</li>
 *   <li>Permits unauthenticated access to <b>/api/auth/register</b> and <b>/api/auth/login</b>.</li>
 *   <li>All other endpoints are currently open (<code>permitAll()</code>), but can be restricted later.</li>
 * </ul>
 *
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Defines the password encoder used across the authentication module.
     * <p>
     * BCrypt is chosen for its adaptive hashing mechanism and built-in salting,
     * which enhances protection against brute-force and rainbow table attacks.
     *
     * @return a {@link BCryptPasswordEncoder} instance for password hashing
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Exposes the Spring {@link AuthenticationManager} as a bean.
     * <p>
     * The {@link AuthenticationManager} is required for performing
     * authentication operations (e.g., verifying credentials during login).
     *
     * @param config the {@link AuthenticationConfiguration} automatically provided by Spring
     * @return the application-wide {@link AuthenticationManager}
     * @throws Exception if authentication manager creation fails
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configures the HTTP security rules for the entire application.
     * <p>
     * This configuration:
     * <ul>
     *   <li>Disables CSRF (not needed for REST APIs).</li>
     *   <li>Enforces stateless session management to align with JWT-based authentication.</li>
     *   <li>Allows public access to registration and login endpoints.</li>
     *   <li>Permits all other requests (for now), but can be restricted in production.</li>
     * </ul>
     *
     * @param http the {@link HttpSecurity} configuration object
     * @return the built {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection for REST APIs
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions for JWT
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                        // Other endpoints (temporarily open, can be changed to authenticated())
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
