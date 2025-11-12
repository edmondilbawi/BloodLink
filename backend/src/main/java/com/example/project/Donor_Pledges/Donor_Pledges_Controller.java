package com.example.project.Donor_Pledges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for managing donor pledges.
 * <p>
 * A donor pledge represents a commitment from a donor to fulfill a specific
 * {@link com.example.project.Blood_Requests.Blood_Requests_Model} in the future.
 * This controller provides endpoints to create pledges, retrieve them (by ID, by request),
 * and list all pledges with their full relational details.
 *
 * <p><b>Base Path:</b> {@code /api/Donor_Pledges}
 *
 * <p><b>Supported Operations:</b>
 * <ul>
 *   <li><b>POST /api/Donor_Pledges</b> — Create a new donor pledge.</li>
 *   <li><b>GET /api/Donor_Pledges</b> — Retrieve all donor pledges (with joined details).</li>
 *   <li><b>GET /api/Donor_Pledges/{id}</b> — Retrieve a specific pledge by ID.</li>
 *   <li><b>GET /api/Donor_Pledges/byRequest/{requestId}</b> — Retrieve all pledges linked to a specific blood request.</li>
 * </ul>
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/api/Donor_Pledges")
public class Donor_Pledges_Controller {

    /**
     * Repository interface used to perform CRUD and custom queries on donor pledges.
     */
    @Autowired
    private Donor_Pledges_Repository donorPledgesRepository;

    /**
     * Creates a new donor pledge and returns the saved entity with all relationships populated.
     *
     * <p>Validation logic ensures that:
     * <ul>
     *   <li>The pledge includes a valid donor profile reference.</li>
     *   <li>The pledge includes a valid matched request reference.</li>
     * </ul>
     *
     * <p>Example request body:
     * <pre>
     * {
     *   "pledgeDate": "2025-10-25T09:00:00",
     *   "pledgeStatus": "Active",
     *   "donorProfile": { "donorId": 12 },
     *   "matchedRequest": { "requestId": 5 }
     * }
     * </pre>
     *
     * <p>Returns:
     * <ul>
     *   <li>200 OK — Successfully created and returned with full details.</li>
     *   <li>400 Bad Request — Missing donor or request reference.</li>
     * </ul>
     *
     * @param pledge a {@link Donor_Pledges_Model} object representing the donor pledge
     * @return a {@link ResponseEntity} containing the fully populated pledge or a bad request response
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Donor_Pledges_Model> create(@RequestBody Donor_Pledges_Model pledge) {
        if (pledge.getDonorProfile() == null || pledge.getMatchedRequest() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Save and reload the entity with JOIN FETCH to return related entities
        Donor_Pledges_Model saved = donorPledgesRepository.save(pledge);
        Donor_Pledges_Model full = donorPledgesRepository.findByIdWithDetails(saved.getPledgeId()).orElse(saved);

        return ResponseEntity.ok(full);
    }

    /**
     * Retrieves all donor pledges from the database with related entities pre-fetched.
     *
     * <p>Example response:
     * <pre>
     * [
     *   {
     *     "pledgeId": 1,
     *     "pledgeStatus": "Active",
     *     "pledgeDate": "2025-10-25T09:00:00",
     *     "donorProfile": { "donorId": 12, "fullName": "Sarah Ali" },
     *     "matchedRequest": { "requestId": 5, "hospitalName": "City Hospital" }
     *   }
     * ]
     * </pre>
     *
     * @return a list of all {@link Donor_Pledges_Model} entities with joined donor and request details
     */
    @GetMapping(produces = "application/json")
    public List<Donor_Pledges_Model> getAll() {
        return donorPledgesRepository.findAllWithDetails();
    }

    /**
     * Retrieves a specific donor pledge by its unique identifier.
     *
     * <p>Returns:
     * <ul>
     *   <li>200 OK — If the pledge exists and is returned with full details.</li>
     *   <li>404 Not Found — If no pledge with the specified ID exists.</li>
     * </ul>
     *
     * @param id the unique identifier of the donor pledge
     * @return a {@link ResponseEntity} containing the pledge or a 404 error
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Donor_Pledges_Model> getById(@PathVariable Long id) {
        return donorPledgesRepository.findByIdWithDetails(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all pledges associated with a specific blood request.
     *
     * <p>Useful for identifying all donors who pledged to fulfill the same request.</p>
     *
     * <p>Example endpoint:</p>
     * <pre>
     * GET /api/Donor_Pledges/byRequest/5
     * </pre>
     *
     * @param requestId the ID of the matched {@link com.example.project.Blood_Requests.Blood_Requests_Model}
     * @return a list of pledges corresponding to the given request ID
     */
    @GetMapping(value = "/byRequest/{requestId}", produces = "application/json")
    public List<Donor_Pledges_Model> getByRequest(@PathVariable Long requestId) {
        return donorPledgesRepository.findByMatchedRequest_RequestId(requestId);
    }
}
