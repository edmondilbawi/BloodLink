package com.example.project.Blood_Requests;

import com.example.project.Users.User_Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class representing a blood request record in the system.
 * <p>
 * Each {@code Blood_Requests_Model} instance corresponds to a row in the
 * {@code blood_requests} database table. It captures all details related
 * to a hospital’s or patient’s need for specific blood units.
 *
 * <p><b>Table:</b> {@code blood_requests}
 *
 * <p><b>Relationships:</b>
 * <ul>
 *   <li>Many-to-One relationship with {@link User_Model} — the user who created the request.</li>
 * </ul>
 *
 * <p><b>Usage:</b>
 * <ul>
 *   <li>Managed through the {@link Blood_Requests_Controller} REST endpoints.</li>
 *   <li>Persisted via the {@link Blood_Requests_Repository} interface.</li>
 * </ul>
 *
 * @version 1.0
 */
@Entity
@Table(name = "blood_requests")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Blood_Requests_Model {

    // =========================
    // Primary Key
    // =========================

    /**
     * Unique identifier for each blood request record.
     * Auto-generated using the database identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    // =========================
    // Request Details
    // =========================

    /**
     * The blood type required (e.g., "A", "B", "AB", "O").
     */
    @Column(name = "needed_blood_type")
    private String neededBloodType;

    /**
     * The Rhesus factor required (e.g., "+" or "-").
     */
    @Column(name = "needed_rhesus")
    private String neededRhesus;

    /**
     * The total number of blood units required for this request.
     */
    @Column(name = "units_needed")
    private int unitsNeeded;

    /**
     * The name of the hospital requesting or receiving the blood.
     */
    @Column(name = "hospital_name")
    private String hospitalName;

    /**
     * The address of the hospital where the blood is needed.
     */
    @Column(name = "hospital_address")
    private String hospitalAddress;

    /**
     * Indicates the urgency level of the blood request (e.g., "High", "Medium", "Low").
     */
    @Column(name = "urgency")
    private String urgency;

    /**
     * Current status of the blood request (e.g., "Pending", "Fulfilled", "Cancelled").
     */
    @Column(name = "status")
    private String status;

    /**
     * The deadline or latest date/time by which the blood is needed.
     */
    @Column(name = "needed_before")
    private LocalDateTime neededBefore;

    /**
     * Additional notes or context for the request (e.g., medical condition, doctor name).
     */
    @Column(name = "notes")
    private String notes;

    /**
     * Timestamp indicating when the blood request was created.
     * Automatically initialized to the current system time.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // =========================
    // Relationships
    // =========================

    /**
     * The {@link User_Model} who created this blood request.
     * <p>
     * Uses EAGER fetching since request ownership is frequently accessed.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User_Model user;

    // =========================
    // Constructors
    // =========================

    /** Default constructor required by JPA. */
    public Blood_Requests_Model() {}

    // =========================
    // Getters and Setters
    // =========================

    /** @return the unique ID of the blood request */
    public Long getRequestId() { return requestId; }

    /** @param requestId sets the unique ID of the blood request */
    public void setRequestId(Long requestId) { this.requestId = requestId; }

    /** @return the requested blood type */
    public String getNeededBloodType() { return neededBloodType; }

    /** @param neededBloodType sets the requested blood type */
    public void setNeededBloodType(String neededBloodType) { this.neededBloodType = neededBloodType; }

    /** @return the requested rhesus factor */
    public String getNeededRhesus() { return neededRhesus; }

    /** @param neededRhesus sets the requested rhesus factor */
    public void setNeededRhesus(String neededRhesus) { this.neededRhesus = neededRhesus; }

    /** @return number of units required */
    public int getUnitsNeeded() { return unitsNeeded; }

    /** @param unitsNeeded sets the number of units required */
    public void setUnitsNeeded(int unitsNeeded) { this.unitsNeeded = unitsNeeded; }

    /** @return hospital name associated with the request */
    public String getHospitalName() { return hospitalName; }

    /** @param hospitalName sets the hospital name */
    public void setHospitalName(String hospitalName) { this.hospitalName = hospitalName; }

    /** @return hospital address */
    public String getHospitalAddress() { return hospitalAddress; }

    /** @param hospitalAddress sets the hospital address */
    public void setHospitalAddress(String hospitalAddress) { this.hospitalAddress = hospitalAddress; }

    /** @return urgency level of the request */
    public String getUrgency() { return urgency; }

    /** @param urgency sets the urgency level */
    public void setUrgency(String urgency) { this.urgency = urgency; }

    /** @return current status of the request */
    public String getStatus() { return status; }

    /** @param status sets the current request status */
    public void setStatus(String status) { this.status = status; }

    /** @return the deadline for fulfilling the request */
    public LocalDateTime getNeededBefore() { return neededBefore; }

    /** @param neededBefore sets the deadline for fulfilling the request */
    public void setNeededBefore(LocalDateTime neededBefore) { this.neededBefore = neededBefore; }

    /** @return any additional notes or context for the request */
    public String getNotes() { return notes; }

    /** @param notes sets any additional notes or context for the request */
    public void setNotes(String notes) { this.notes = notes; }

    /** @return timestamp of when the request was created */
    public LocalDateTime getCreatedAt() { return createdAt; }

    /** @param createdAt sets the creation timestamp (optional override) */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    /** @return the user who created this request */
    public User_Model getUser() { return user; }

    /** @param user sets the user who created this request */
    public void setUser(User_Model user) { this.user = user; }
}
