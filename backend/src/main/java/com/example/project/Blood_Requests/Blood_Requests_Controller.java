package com.example.project.Blood_Requests;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for managing blood donation and transfusion requests.
 * <p>
 * This controller provides endpoints for creating, listing, and deleting blood requests.
 * It communicates directly with the {@link Blood_Requests_Repository} for database operations.
 *
 * <p><b>Base Path:</b> {@code /api/Blood_Requests}
 *
 * <p><b>Supported Operations:</b>
 * <ul>
 *   <li><b>GET /api/Blood_Requests</b> — Retrieve all blood requests.</li>
 *   <li><b>POST /api/Blood_Requests</b> — Create a new blood request entry.</li>
 *   <li><b>DELETE /api/Blood_Requests/{id}</b> — Delete a specific blood request by ID.</li>
 * </ul>
 *
 * <p>Each operation follows REST conventions and returns appropriate HTTP status codes.
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/api/Blood_Requests")
public class Blood_Requests_Controller {

    /**
     * Repository layer for database operations related to {@link Blood_Requests_Model}.
     */
    private final Blood_Requests_Repository repo;

    /**
     * Constructs a new {@code Blood_Requests_Controller} with the specified repository.
     *
     * @param repo the repository responsible for handling persistence
     */
    public Blood_Requests_Controller(Blood_Requests_Repository repo) {
        this.repo = repo;
    }

    /**
     * Retrieves a list of all existing blood requests.
     *
     * <p>Example response:
     * <pre>
     * [
     *   {
     *     "requestId": 1,
     *     "patientName": "Ali Hassan",
     *     "bloodType": "O",
     *     "rhesus": "+",
     *     "hospitalName": "City Hospital",
     *     "status": "Pending"
     *   },
     *   {
     *     "requestId": 2,
     *     "patientName": "Maria Ahmed",
     *     "bloodType": "A",
     *     "rhesus": "-",
     *     "hospitalName": "King's Medical Center",
     *     "status": "Fulfilled"
     *   }
     * ]
     * </pre>
     *
     * @return a list of {@link Blood_Requests_Model} objects
     */
    @GetMapping
    public List<Blood_Requests_Model> all() {
        return repo.findAll();
    }

    /**
     * Creates a new blood request record in the database.
     *
     * <p>Example request body:
     * <pre>
     * {
     *   "patientName": "Ali Hassan",
     *   "bloodType": "O",
     *   "rhesus": "+",
     *   "hospitalName": "City Hospital",
     *   "status": "Pending"
     * }
     * </pre>
     *
     * <p>Returns HTTP 201 Created upon success.</p>
     *
     * @param request a validated {@link Blood_Requests_Model} object containing the request details
     * @return the saved {@link Blood_Requests_Model} instance with an assigned ID
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Blood_Requests_Model create(@Valid @RequestBody Blood_Requests_Model request) {
        return repo.save(request);
    }

    /**
     * Deletes an existing blood request by its unique identifier.
     *
     * <p>Returns HTTP 204 No Content if successful, even if the record does not exist.</p>
     *
     * @param id the ID of the blood request to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
