package com.example.frontend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a donor’s profile and availability information.
 * <p>
 * The {@code DonorProfileModel} is used on the frontend to deserialize
 * JSON data from the backend’s {@code /api/Donor_Profiles} endpoints.
 * It provides information about a donor’s verification status, availability,
 * donation history, and geographical reach radius.
 *
 * <p><b>Associated Backend Entity:</b> {@code com.example.project.Donor_Profiles.Donor_Profiles_Model}
 *
 * <p><b>Example JSON payload:</b>
 * <pre>
 * {
 *   "donorId": 15,
 *   "date_of_birth": "1995-06-10",
 *   "is_verified": true,
 *   "radius_km": 25,
 *   "availability_status": "Available",
 *   "unavailable_until": null,
 *   "last_donation_at": "2025-10-20T14:00:00",
 *   "cooldown_ends_at": "2025-12-20T14:00:00",
 *   "dnd_until": null,
 *   "created_at": "2025-03-15T11:32:00"
 * }
 * </pre>
 *
 * <p><b>Typical Usage:</b>
 * <ul>
 *   <li>Displaying donor availability status in dashboards.</li>
 *   <li>Managing search filters (radius, verification status, etc.).</li>
 *   <li>Linking donor profiles to pledges and donation records.</li>
 *   <li>Tracking donor cooldown and last donation information.</li>
 * </ul>
 *
 * @version 1.0
 * @see com.example.project.Donor_Profiles.Donor_Profiles_Model
 * @see DonationModel
 * @see DonorPledgeModel
 */
public class DonorProfileModel {

    // ============================================================
    // Fields
    // ============================================================

    /** Unique identifier of the donor profile. */
    @JsonProperty("donorId")
    private Long donorId;

    /** Donor’s date of birth (used for eligibility verification). */
    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    /** Indicates whether the donor has been verified by the system or admin. */
    @JsonProperty("is_verified")
    private boolean verified;

    /** Search or donation radius in kilometers for matching requests. */
    @JsonProperty("radius_km")
    private int radiusKm;

    /** Current availability status of the donor (e.g., Available, Unavailable, DND). */
    @JsonProperty("availability_status")
    private String availabilityStatus;

    /** Timestamp indicating when the donor will become available again, if currently unavailable. */
    @JsonProperty("unavailable_until")
    private String unavailableUntil;

    /** Timestamp of the donor’s most recent donation. */
    @JsonProperty("last_donation_at")
    private String lastDonationAt;

    /** Timestamp marking the end of the donor’s cooldown period after last donation. */
    @JsonProperty("cooldown_ends_at")
    private String cooldownEndsAt;

    /** Timestamp indicating when “Do Not Disturb” mode ends, if active. */
    @JsonProperty("dnd_until")
    private String dndUntil;

    /** Timestamp of when this profile was created in the database. */
    @JsonProperty("created_at")
    private String createdAt;

    // ============================================================
    // Getters and Setters
    // ============================================================

    public Long getDonorId() {
        return donorId;
    }

    public void setDonorId(Long donorId) {
        this.donorId = donorId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getRadiusKm() {
        return radiusKm;
    }

    public void setRadiusKm(int radiusKm) {
        this.radiusKm = radiusKm;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getUnavailableUntil() {
        return unavailableUntil;
    }

    public void setUnavailableUntil(String unavailableUntil) {
        this.unavailableUntil = unavailableUntil;
    }

    public String getLastDonationAt() {
        return lastDonationAt;
    }

    public void setLastDonationAt(String lastDonationAt) {
        this.lastDonationAt = lastDonationAt;
    }

    public String getCooldownEndsAt() {
        return cooldownEndsAt;
    }

    public void setCooldownEndsAt(String cooldownEndsAt) {
        this.cooldownEndsAt = cooldownEndsAt;
    }

    public String getDndUntil() {
        return dndUntil;
    }

    public void setDndUntil(String dndUntil) {
        this.dndUntil = dndUntil;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
