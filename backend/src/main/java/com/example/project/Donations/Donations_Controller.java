package com.example.project.Donations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.project.Blood_Requests.Blood_Requests_Model;
import com.example.project.Blood_Requests.Blood_Requests_Repository;
import com.example.project.Donor_Profiles.Donor_Profiles_Model;
import com.example.project.Donor_Profiles.Donor_Profiles_Repository;

import java.util.*;

/**
 * REST controller responsible for managing blood donation records.
 * <p>
 * This controller defines endpoints for creating, listing, and deleting donation records.
 * Each donation links a {@link Donor_Profiles_Model} (the donor)
 * to a {@link Blood_Requests_Model} (the fulfilled blood request).
 *
 * <p><b>Base Path:</b> {@code /api/Donations}
 *
 * <p><b>Supported Operations:</b>
 * <ul>
 *   <li><b>POST /api/Donations</b> — Create a new donation record.</li>
 *   <li><b>GET /api/Donations</b> — Retrieve all donations.</li>
 *   <li><b>DELETE /api/Donations/{id}</b> — Delete a donation by its unique ID.</li>
 * </ul>
 *
 * <p>All endpoints support cross-origin requests to allow communication with external clients.
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/api/Donations")
@CrossOrigin(origins = "*")
public class Donations_Controller {

    /**
     * Repository for performing CRUD operations on donation records.
     */
    @Autowired
    private Donations_Repository donationsRepo;

    /**
     * Repository for accessing donor profile data.
     */
    @Autowired
    private Donor_Profiles_Repository donorRepo;

    /**
     * Repository for accessing blood request data.
     */
    @Autowired
    private Blood_Requests_Repository requestRepo;

    /**
     * Creates a new donation entry that links a donor and a fulfilled blood request.
     *
     * <p>Before saving, this method ensures that:
     * <ul>
     *   <li>Both donor and request IDs are provided.</li>
     *   <li>Both entities exist in the database (to prevent foreign key constraint violations).</li>
     *   <li>The managed entities are re-attached to the persistence context before saving.</li>
     * </ul>
     *
     * <p>Example request body:
     * <pre>
     * {
     *   "donationDate": "2025-10-15T10:30:00",
     *   "volume": 450,
     *   "donorProfile": { "donorId": 3 },
     *   "fulfilledRequest": { "requestId": 7 }
     * }
     * </pre>
     *
     * <p>Returns:
     * <ul>
     *   <li>200 OK — Donation successfully created and returned.</li>
     *   <li>400 Bad Request — If donorProfile or fulfilledRequest IDs are missing or invalid.</li>
     * </ul>
     *
     * @param donation the {@link Donations_Model} object containing donation details
     * @return a {@link ResponseEntity} containing the saved donation or an error message
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Donations_Model donation) {

        // Extract donor and request IDs from the request body
        Long donorId = donation.getDonorProfile() != null ? donation.getDonorProfile().getDonorId() : null;
        Long requestId = donation.getFulfilledRequest() != null ? donation.getFulfilledRequest().getRequestId() : null;

        if (donorId == null || requestId == null)
            return ResponseEntity.badRequest().body(Map.of("error", "Missing donorProfile or fulfilledRequest IDs"));

        Optional<Donor_Profiles_Model> donorOpt = donorRepo.findById(donorId);
        Optional<Blood_Requests_Model> requestOpt = requestRepo.findById(requestId);

        if (donorOpt.isEmpty() || requestOpt.isEmpty())
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid donorId or requestId"));

        // Re-attach managed entities before saving (ensures JPA persistence consistency)
        donation.setDonorProfile(donorOpt.get());
        donation.setFulfilledRequest(requestOpt.get());

        Donations_Model saved = donationsRepo.save(donation);
        return ResponseEntity.ok(saved);
    }

    /**
     * Retrieves all donation records from the database.
     *
     * <p>Example response:
     * <pre>
     * [
     *   {
     *     "donationId": 1,
     *     "donationDate": "2025-10-15T10:30:00",
     *     "volume": 450,
     *     "donorProfile": { "donorId": 3, "fullName": "John Doe" },
     *     "fulfilledRequest": { "requestId": 7, "hospitalName": "City Hospital" }
     *   }
     * ]
     * </pre>
     *
     * @return a list of all {@link Donations_Model} entries
     */
    @GetMapping
    public List<Donations_Model> getAll() {
        return donationsRepo.findAll();
    }

    /**
     * Deletes a donation record by its unique identifier.
     *
     * <p>Returns:
     * <ul>
     *   <li>204 No Content — if deletion succeeds.</li>
     *   <li>404 Not Found — if no donation with the provided ID exists.</li>
     * </ul>
     *
     * @param id the unique identifier of the donation record to be deleted
     * @return a {@link ResponseEntity} indicating the operation result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!donationsRepo.existsById(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Donation not found"));

        donationsRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
