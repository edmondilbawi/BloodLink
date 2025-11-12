package com.example.frontend.model;

/**
 * Represents a user account within the frontend application.
 * <p>
 * The {@code UserModel} mirrors the backend {@code User_Model} entity and
 * stores all relevant user information, including personal details,
 * credentials, and blood profile. It is used throughout the frontend for
 * authentication, registration, and session handling.
 *
 * <p><b>Associated Backend Entity:</b> {@code com.example.project.Users.User_Model}
 *
 * <p><b>Example JSON payload:</b>
 * <pre>
 * {
 *   "userId": 7,
 *   "fullName": "Jane Doe",
 *   "email": "jane@example.com",
 *   "phone": "+971501234567",
 *   "passwordHash": "hashed_password_value",
 *   "role": "Donor",
 *   "homeAddress": "Dubai Marina, Dubai, UAE",
 *   "bloodType": "B",
 *   "rhesus": "+",
 *   "createdAt": "2025-11-03T10:00:00"
 * }
 * </pre>
 *
 * <p><b>Typical Usage:</b>
 * <ul>
 *   <li>Registration via {@link com.example.frontend.api.AuthApi#register(UserModel)}.</li>
 *   <li>Login and session persistence via {@link com.example.frontend.utils.Session}.</li>
 *   <li>Displaying user details in dashboards and profile pages.</li>
 *   <li>Binding form input in JavaFX controllers like {@link com.example.frontend.Controller.SignUpController}.</li>
 * </ul>
 *
 * @version 1.0
 * @see com.example.project.Users.User_Model
 * @see com.example.frontend.api.AuthApi
 * @see com.example.frontend.utils.Session
 * @see com.example.frontend.Controller.SignUpController
 */
public class UserModel {

    // ============================================================
    // Fields
    // ============================================================

    /** Unique identifier of the user. */
    private Long userId;

    /** Full name of the user. */
    private String fullName;

    /** Registered email address (used for login). */
    private String email;

    /** User’s phone number. */
    private String phone;

    /** Hashed password stored in the backend. */
    private String passwordHash;

    /** The user's role in the system (e.g., Donor, Recipient, Admin, or UNASSIGNED). */
    private String role;

    /** Home address of the user (used for location-based matching). */
    private String homeAddress;

    /** The user's blood type (e.g., A, B, AB, O). */
    private String bloodType;

    /** The user's Rhesus factor (+ or -). */
    private String rhesus;

    /** Timestamp indicating when the account was created. */
    private String createdAt;

    // ============================================================
    // Constructors
    // ============================================================

    /** Default no-argument constructor (required for JSON deserialization). */
    public UserModel() {}

    // ============================================================
    // Getters and Setters
    // ============================================================

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getHomeAddress() { return homeAddress; }
    public void setHomeAddress(String homeAddress) { this.homeAddress = homeAddress; }

    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }

    public String getRhesus() { return rhesus; }
    public void setRhesus(String rhesus) { this.rhesus = rhesus; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
