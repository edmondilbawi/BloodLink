package com.example.project.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) representing a user registration request payload.
 * <p>
 * This class is used by the {@link AuthController} to receive user details from
 * incoming HTTP POST requests to the <b>/api/auth/register</b> endpoint.
 * It is validated using Jakarta Bean Validation annotations before processing.
 *
 * <p>Typical JSON payload:
 * <pre>
 * {
 *   "fullName": "John Doe",
 *   "email": "john@example.com",
 *   "phone": "0501234567",
 *   "password": "Password123!",
 *   "role": "Donor",
 *   "homeAddress": "123 Main St, Dubai",
 *   "bloodType": "A",
 *   "rhesus": "+"
 * }
 * </pre>
 *
 * <p>Validated and processed by:
 * <ul>
 *   <li>{@link AuthController#register(RegisterRequest)}</li>
 *   <li>{@link AuthService#registerAndReturnToken(RegisterRequest)}</li>
 * </ul>
 *
 * @version 1.0
 */
public class RegisterRequest {

    /**
     * The full name of the user (first and last name required).
     */
    @NotBlank
    private String fullName;

    /**
     * The user's unique email address used for login and identification.
     */
    @Email
    @NotBlank
    private String email;

    /**
     * The user's contact phone number.
     */
    @NotBlank
    private String phone;

    /**
     * The user's chosen password. Stored securely after hashing with BCrypt.
     */
    @NotBlank
    private String password;

    /**
     * The user's designated role within the system
     * (e.g., "Donor", "Recipient", or "Admin").
     */
    @NotBlank
    private String role;

    /**
     * The user's residential address, used for contact and verification.
     */
    @NotBlank
    private String homeAddress;

    /**
     * The user's blood type (e.g., "A", "B", "AB", "O").
     */
    @NotBlank
    private String bloodType;

    /**
     * The user's Rhesus factor (e.g., "+" or "-").
     */
    @NotBlank
    private String rhesus;

    // ========================
    // Getters and Setters
    // ========================

    /** @return the user's full name */
    public String getFullName() { return fullName; }

    /** @param fullName sets the user's full name */
    public void setFullName(String fullName) { this.fullName = fullName; }

    /** @return the user's email address */
    public String getEmail() { return email; }

    /** @param email sets the user's email address */
    public void setEmail(String email) { this.email = email; }

    /** @return the user's phone number */
    public String getPhone() { return phone; }

    /** @param phone sets the user's phone number */
    public void setPhone(String phone) { this.phone = phone; }

    /** @return the user's password (plain text before hashing) */
    public String getPassword() { return password; }

    /** @param password sets the user's plain-text password */
    public void setPassword(String password) { this.password = password; }

    /** @return the user's assigned role */
    public String getRole() { return role; }

    /** @param role sets the user's assigned role */
    public void setRole(String role) { this.role = role; }

    /** @return the user's home address */
    public String getHomeAddress() { return homeAddress; }

    /** @param homeAddress sets the user's home address */
    public void setHomeAddress(String homeAddress) { this.homeAddress = homeAddress; }

    /** @return the user's blood type */
    public String getBloodType() { return bloodType; }

    /** @param bloodType sets the user's blood type */
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }

    /** @return the user's rhesus factor */
    public String getRhesus() { return rhesus; }

    /** @param rhesus sets the user's rhesus factor */
    public void setRhesus(String rhesus) { this.rhesus = rhesus; }
}
