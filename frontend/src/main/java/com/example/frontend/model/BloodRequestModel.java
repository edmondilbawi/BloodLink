package com.example.frontend.model;

/**
 * Represents a blood request made by a hospital or recipient within the application.
 * <p>
 * This model is used on the frontend to deserialize JSON responses
 * received from the backend’s {@code /api/Blood_Requests} endpoints.
 * It encapsulates all details necessary to display or create a blood request,
 * including the required blood type, urgency, and hospital information.
 *
 * <p><b>Associated Backend Entity:</b> {@code com.example.project.Blood_Requests.Blood_Requests_Model}
 *
 * <p><b>Example JSON payload:</b>
 * <pre>
 * {
 *   "requestId": 12,
 *   "neededBloodType": "O",
 *   "neededRhesus": "+",
 *   "unitsNeeded": 4,
 *   "hospitalName": "Al Zahra Hospital",
 *   "hospitalAddress": "Dubai, UAE",
 *   "urgency": "High",
 *   "status": "Pending",
 *   "neededBefore": "2025-11-05T18:00:00",
 *   "notes": "Urgent surgery case",
 *   "createdAt": "2025-11-01T10:30:00"
 * }
 * </pre>
 *
 * <p><b>Typical Usage:</b>
 * <ul>
 *   <li>Mapping backend JSON data to JavaFX objects for display in tables or forms.</li>
 *   <li>Sending new request data to the backend through a POST request.</li>
 *   <li>Tracking request statuses or filtering requests based on urgency.</li>
 * </ul>
 *
 * @version 1.0
 * @see com.example.frontend.api.ApiClient
 * @see com.example.project.Blood_Requests.Blood_Requests_Model
 */
public class BloodRequestModel {

    // ============================================================
    // Fields
    // ============================================================

    /** Unique identifier of the blood request. */
    private Long requestId;

    /** Blood type required for the request (e.g., A, B, AB, O). */
    private String neededBloodType;

    /** Rhesus factor of the required blood type (+ or -). */
    private String neededRhesus;

    /** Number of blood units requested. */
    private int unitsNeeded;

    /** Name of the hospital or medical facility making the request. */
    private String hospitalName;

    /** Full address of the hospital or medical facility. */
    private String hospitalAddress;

    /** Urgency level of the blood request (e.g., Low, Medium, High, Critical). */
    private String urgency;

    /** Current status of the request (e.g., Pending, Fulfilled, Cancelled). */
    private String status;

    /** The deadline or required date/time by which the blood is needed. */
    private String neededBefore;

    /** Additional notes or context provided by the requester. */
    private String notes;

    /** Timestamp of when the request was created. */
    private String createdAt;

    // ============================================================
    // Getters and Setters
    // ============================================================

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getNeededBloodType() {
        return neededBloodType;
    }

    public void setNeededBloodType(String neededBloodType) {
        this.neededBloodType = neededBloodType;
    }

    public String getNeededRhesus() {
        return neededRhesus;
    }

    public void setNeededRhesus(String neededRhesus) {
        this.neededRhesus = neededRhesus;
    }

    public int getUnitsNeeded() {
        return unitsNeeded;
    }

    public void setUnitsNeeded(int unitsNeeded) {
        this.unitsNeeded = unitsNeeded;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNeededBefore() {
        return neededBefore;
    }

    public void setNeededBefore(String neededBefore) {
        this.neededBefore = neededBefore;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
