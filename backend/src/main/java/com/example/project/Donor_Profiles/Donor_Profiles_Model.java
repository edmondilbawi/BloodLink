package com.example.project.Donor_Profiles;

import com.example.project.Users.User_Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing an extended donor profile with scheduling metadata.
 */
@Entity
@Table(name = "donor_profiles")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Donor_Profiles_Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donor_id")
    private Long donorId;

    @Column(name = "blood_type")
    private String bloodType;

    @Column(name = "rhesus")
    private String rhesus;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "last_donation_date")
    private LocalDateTime lastDonationDate;

    @Column(name = "available_by")
    private LocalDateTime availableBy;

    @Column(name = "do_not_disturb_until")
    private LocalDateTime doNotDisturbUntil;

    @Column(name = "availability_status")
    private String availabilityStatus;

    @Column(name = "preferred_radius_km")
    private Integer preferredRadiusKm;

    @Column(name = "donations_count")
    private int donationsCount;

    @Column(name = "location")
    private String location;

    @Column(name = "home_address")
    private String homeAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User_Model user;

    public Donor_Profiles_Model() {}

    public Long getDonorId() { return donorId; }
    public void setDonorId(Long donorId) { this.donorId = donorId; }

    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }

    public String getRhesus() { return rhesus; }
    public void setRhesus(String rhesus) { this.rhesus = rhesus; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public LocalDateTime getLastDonationDate() { return lastDonationDate; }
    public void setLastDonationDate(LocalDateTime lastDonationDate) { this.lastDonationDate = lastDonationDate; }

    public LocalDateTime getAvailableBy() { return availableBy; }
    public void setAvailableBy(LocalDateTime availableBy) { this.availableBy = availableBy; }

    public LocalDateTime getDoNotDisturbUntil() { return doNotDisturbUntil; }
    public void setDoNotDisturbUntil(LocalDateTime doNotDisturbUntil) { this.doNotDisturbUntil = doNotDisturbUntil; }

    public String getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }

    public Integer getPreferredRadiusKm() { return preferredRadiusKm; }
    public void setPreferredRadiusKm(Integer preferredRadiusKm) { this.preferredRadiusKm = preferredRadiusKm; }

    public int getDonationsCount() { return donationsCount; }
    public void setDonationsCount(int donationsCount) { this.donationsCount = donationsCount; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getHomeAddress() { return homeAddress; }
    public void setHomeAddress(String homeAddress) { this.homeAddress = homeAddress; }

    public User_Model getUser() { return user; }
    public void setUser(User_Model user) { this.user = user; }
}
