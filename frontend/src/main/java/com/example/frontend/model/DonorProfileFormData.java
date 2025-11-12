package com.example.frontend.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Simple DTO used by the donor profile form before sending data to the backend.
 */
public class DonorProfileFormData {

    private Long donorId;
    private String bloodType;
    private String rhesus;
    private String availabilityStatus;
    private LocalDate dateOfBirth;
    private LocalDateTime availableBy;
    private LocalDateTime doNotDisturbUntil;
    private LocalDateTime lastDonationDate;
    private Integer preferredRadiusKm;
    private String homeAddress;

    public Long getDonorId() { return donorId; }
    public void setDonorId(Long donorId) { this.donorId = donorId; }

    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }

    public String getRhesus() { return rhesus; }
    public void setRhesus(String rhesus) { this.rhesus = rhesus; }

    public String getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public LocalDateTime getAvailableBy() { return availableBy; }
    public void setAvailableBy(LocalDateTime availableBy) { this.availableBy = availableBy; }

    public LocalDateTime getDoNotDisturbUntil() { return doNotDisturbUntil; }
    public void setDoNotDisturbUntil(LocalDateTime doNotDisturbUntil) { this.doNotDisturbUntil = doNotDisturbUntil; }

    public LocalDateTime getLastDonationDate() { return lastDonationDate; }
    public void setLastDonationDate(LocalDateTime lastDonationDate) { this.lastDonationDate = lastDonationDate; }

    public Integer getPreferredRadiusKm() { return preferredRadiusKm; }
    public void setPreferredRadiusKm(Integer preferredRadiusKm) { this.preferredRadiusKm = preferredRadiusKm; }

    public String getHomeAddress() { return homeAddress; }
    public void setHomeAddress(String homeAddress) { this.homeAddress = homeAddress; }
}
