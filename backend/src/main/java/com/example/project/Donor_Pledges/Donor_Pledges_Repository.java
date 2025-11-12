package com.example.project.Donor_Pledges;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface Donor_Pledges_Repository extends JpaRepository<Donor_Pledges_Model, Long> {

    // ✅ Fetch both donor and request for all pledges
    @Query("SELECT p FROM Donor_Pledges_Model p " +
            "JOIN FETCH p.donorProfile d " +
            "JOIN FETCH p.matchedRequest r")
    List<Donor_Pledges_Model> findAllWithDetails();

    // ✅ Fetch one pledge by ID, including full donor and request
    @Query("SELECT p FROM Donor_Pledges_Model p " +
            "JOIN FETCH p.donorProfile d " +
            "JOIN FETCH p.matchedRequest r " +
            "WHERE p.pledgeId = :id")
    Optional<Donor_Pledges_Model> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT p FROM Donor_Pledges_Model p " +
            "JOIN FETCH p.donorProfile d " +
            "JOIN FETCH p.matchedRequest r " +
            "WHERE r.requestId = :requestId")
    List<Donor_Pledges_Model> findByMatchedRequest_RequestId(@Param("requestId") Long requestId);
}
