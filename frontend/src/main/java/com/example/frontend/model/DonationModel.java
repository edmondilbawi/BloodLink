package com.example.frontend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a blood donation record within the frontend application.
 * <p>
 * The {@code DonationModel} class is used to deserialize JSON data exchanged
 * between the frontend and the backend’s {@code /api/Donations} endpoints.
 * It encapsulates details about a donation event, including the number of units donated,
 * timestamps, donor profile information, and the fulfilled blood request.
 *
 * <p><b>Associated Backend Entity:</b> {@code com.example.project.Donations.Donations_Model}
 *
 * <p><b>Example JSON payload:</b>
 * <pre>
 * {
 *   "donationId": 101,
 *   "units_donated": 2,
 *   "outcome": "Successful",
 *   "donation_time": "2025-11-02T14:25:00",
 *   "confirmed_by_requester": true,
 *   "created_at": "2025-11-02T14:30:00",
 *   "donorProfile": {
 *       "donorId": 5,
 *       "bloodType": "A",
 *       "rhesus": "+",
 *       "location": "Abu Dhabi",
 *       "donationsCount": 3
 *   },
 *   "fulfilledRequest": {
 *       "requestId": 12,
 *       "hospitalName": "Cleveland Clinic",
 *       "urgency": "High"
 *   }
 * }
 * </pre>
 *
 * <p><b>Typical Usage:</b>
 * <ul>
 *   <li>Displaying donor activity history in the dashboard.</li>
 *   <li>Linking donations to specific requests for record-keeping.</li>
 *   <li>Building analytics dashboards showing donation outcomes and timing.</li>
 * </ul>
 *
 * @version 1.0
 * @see com.example.project.Donations.Donations_Model
 * @see DonorProfileModel
 * @see BloodRequestModel
 */
public class DonationModel {

    // ============================================================
    // Fields
    // ============================================================

    /** Unique identifier of the donation record. */
    @JsonProperty("donationId")
    private Long donationId;

    /** Number of blood units donated in this record. */
    @JsonProperty("units_donated")
    private int unitsDonated;

    /** Outcome of the donation (e.g., Successful, Failed, Deferred). */
    @JsonProperty("outcome")
    private String outcome;

    /** Timestamp indicating when the donation occurred. */
    @JsonProperty("donation_time")
    private String donationTime;

    /** Whether the requester (hospital or recipient) confirmed the donation. */
    @JsonProperty("confirmed_by_requester")
    private Boolean confirmedByRequester;

    /** Timestamp of when the record was created in the database. */
    @JsonProperty("created_at")
    private String createdAt;

    /** Donor profile linked to this donation. */
    @JsonProperty("donorProfile")
    private DonorProfileModel donorProfile;

    /** The blood request that this donation fulfilled. */
    @JsonProperty("fulfilledRequest")
    private BloodRequestModel fulfilledRequest;

    // ============================================================
    // Getters and Setters
    // ============================================================

    public Long getDonationId() {
        return donationId;
    }

    public void setDonationId(Long donationId) {
        this.donationId = donationId;
    }

    public int getUnitsDonated() {
        return unitsDonated;
    }

    public void setUnitsDonated(int unitsDonated) {
        this.unitsDonated = unitsDonated;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getDonationTime() {
        return donationTime;
    }

    public void setDonationTime(String donationTime) {
        this.donationTime = donationTime;
    }

    public Boolean getConfirmedByRequester() {
        return confirmedByRequester;
    }

    public void setConfirmedByRequester(Boolean confirmedByRequester) {
        this.confirmedByRequester = confirmedByRequester;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public DonorProfileModel getDonorProfile() {
        return donorProfile;
    }

    public void setDonorProfile(DonorProfileModel donorProfile) {
        this.donorProfile = donorProfile;
    }

    public BloodRequestModel getFulfilledRequest() {
        return fulfilledRequest;
    }

    public void setFulfilledRequest(BloodRequestModel fulfilledRequest) {
        this.fulfilledRequest = fulfilledRequest;
    }
}
