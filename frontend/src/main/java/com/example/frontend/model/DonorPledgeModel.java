package com.example.frontend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a donor’s pledge to fulfill a specific blood request.
 * <p>
 * The {@code DonorPledgeModel} is used on the frontend to map JSON data
 * from the backend’s {@code /api/Donor_Pledges} endpoints. It stores all
 * details related to a pledge — including status, number of units pledged,
 * timestamps, and references to the donor profile and matched blood request.
 *
 * <p><b>Associated Backend Entity:</b> {@code com.example.project.Donor_Pledges.Donor_Pledges_Model}
 *
 * <p><b>Example JSON payload:</b>
 * <pre>
 * {
 *   "pledgeId": 35,
 *   "pledge_status": "Pending",
 *   "pledged_units": 3,
 *   "message": "Will donate by Friday",
 *   "updated_at": "2025-11-03T09:00:00",
 *   "created_at": "2025-11-01T12:15:00",
 *   "donorProfile": {
 *       "donorId": 8,
 *       "bloodType": "B",
 *       "rhesus": "+",
 *       "location": "Sharjah"
 *   },
 *   "matchedRequest": {
 *       "requestId": 22,
 *       "hospitalName": "Rashid Hospital",
 *       "urgency": "High",
 *       "status": "Open"
 *   }
 * }
 * </pre>
 *
 * <p><b>Typical Usage:</b>
 * <ul>
 *   <li>Displaying active or completed pledges in a donor dashboard.</li>
 *   <li>Binding pledge data to UI elements like tables or cards.</li>
 *   <li>Tracking donor commitment and linking to fulfilled blood requests.</li>
 *   <li>Synchronizing frontend and backend pledge data through the API.</li>
 * </ul>
 *
 * @version 1.0
 * @see com.example.project.Donor_Pledges.Donor_Pledges_Model
 * @see DonorProfileModel
 * @see BloodRequestModel
 */
public class DonorPledgeModel {

    // ============================================================
    // Fields
    // ============================================================

    /** Unique identifier of the donor pledge. */
    @JsonProperty("pledgeId")
    private Long pledgeId;

    /** Current status of the pledge (e.g., Pending, Fulfilled, Cancelled). */
    @JsonProperty("pledge_status")
    private String pledgeStatus;

    /** Number of blood units the donor has pledged to provide. */
    @JsonProperty("pledged_units")
    private int pledgedUnits;

    /** Optional message or note included with the pledge. */
    @JsonProperty("message")
    private String message;

    /** Timestamp of the last update to this pledge. */
    @JsonProperty("updated_at")
    private String updatedAt;

    /** Timestamp of when the pledge was first created. */
    @JsonProperty("created_at")
    private String createdAt;

    /** Reference to the donor profile that made this pledge. */
    @JsonProperty("donorProfile")
    private DonorProfileModel donorProfile;

    /** Reference to the blood request associated with this pledge. */
    @JsonProperty("matchedRequest")
    private BloodRequestModel matchedRequest;

    // ============================================================
    // Getters and Setters
    // ============================================================

    public Long getPledgeId() {
        return pledgeId;
    }

    public void setPledgeId(Long pledgeId) {
        this.pledgeId = pledgeId;
    }

    public String getPledgeStatus() {
        return pledgeStatus;
    }

    public void setPledgeStatus(String pledgeStatus) {
        this.pledgeStatus = pledgeStatus;
    }

    public int getPledgedUnits() {
        return pledgedUnits;
    }

    public void setPledgedUnits(int pledgedUnits) {
        this.pledgedUnits = pledgedUnits;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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

    public BloodRequestModel getMatchedRequest() {
        return matchedRequest;
    }

    public void setMatchedRequest(BloodRequestModel matchedRequest) {
        this.matchedRequest = matchedRequest;
    }
}
