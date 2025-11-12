package com.example.project.Donor_Profiles;

import com.example.project.Users.User_Model;
import com.example.project.Users.User_Repository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing donor profiles.
 * <p>
 * Each donor profile is linked to a {@link User_Model} that provides authentication
 * and account-level information. This controller allows creating, retrieving,
 * and deleting donor profiles through RESTful endpoints.
 *
 * <p><b>Base Path:</b> {@code /api/Donor_Profiles}
 *
 * <p><b>Supported Operations:</b>
 * <ul>
 *   <li><b>GET /api/Donor_Profiles</b> — Retrieve all donor profiles.</li>
 *   <li><b>POST /api/Donor_Profiles</b> — Create a new donor profile linked to an existing user.</li>
 *   <li><b>GET /api/Donor_Profiles/{id}</b> — Retrieve a single donor profile by ID.</li>
 *   <li><b>DELETE /api/Donor_Profiles/{id}</b> — Delete a donor profile by ID.</li>
 * </ul>
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/api/Donor_Profiles")
public class Donor_Profiles_Controller {

    /**
     * Repository for performing CRUD operations on donor profiles.
     */
    private final Donor_Profiles_Repository donorRepo;

    /**
     * Repository for accessing user data linked to donor profiles.
     */
    private final User_Repository userRepo;

    /**
     * Constructs a new {@code Donor_Profiles_Controller} with injected repositories.
     *
     * @param donorRepo the repository handling donor profile persistence
     * @param userRepo the repository handling user persistence
     */
    public Donor_Profiles_Controller(Donor_Profiles_Repository donorRepo, User_Repository userRepo) {
        this.donorRepo = donorRepo;
        this.userRepo = userRepo;
    }

    /**
     * Retrieves all donor profiles stored in the database.
     *
     * <p>Example response:
     * <pre>
     * [
     *   {
     *     "donorId": 1,
     *     "bloodType": "O",
     *     "rhesus": "+",
     *     "availability": "Available",
     *     "user": {
     *       "userId": 5,
     *       "fullName": "John Doe",
     *       "email": "john@example.com"
     *     }
     *   }
     * ]
     * </pre>
     *
     * @return a list of {@link Donor_Profiles_Model} objects
     */
    @GetMapping
    public List<Donor_Profiles_Model> all() {
        return donorRepo.findAll();
    }

    /**
     * Creates a new donor profile associated with an existing {@link User_Model}.
     *
     * <p>Validation rules:
     * <ul>
     *   <li>The donor profile must include a non-null {@code user} object with a valid {@code userId}.</li>
     *   <li>The user must already exist in the database.</li>
     * </ul>
     *
     * <p>Example request body:
     * <pre>
     * {
     *   "bloodType": "A",
     *   "rhesus": "+",
     *   "availability": "Available",
     *   "user": { "userId": 5 }
     * }
     * </pre>
     *
     * <p>Returns:
     * <ul>
     *   <li>201 Created — if the donor profile was successfully saved.</li>
     *   <li>400 Bad Request — if the linked user ID is missing or invalid.</li>
     * </ul>
     *
     * @param donor the {@link Donor_Profiles_Model} to be created
     * @return the saved {@link Donor_Profiles_Model} with populated user details
     * @throws RuntimeException if the user ID is missing or does not exist
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Donor_Profiles_Model create(@Valid @RequestBody Donor_Profiles_Model donor) {
        if (donor.getUser() == null || donor.getUser().getUserId() == null) {
            throw new RuntimeException("User ID is required when creating a donor profile.");
        }

        // 🔍 Fetch the existing User_Model from the database
        User_Model existingUser = userRepo.findById(donor.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException(
                        "User not found with ID: " + donor.getUser().getUserId()));

        // 🔗 Attach the managed entity to maintain a valid relationship
        donor.setUser(existingUser);

        // 💾 Save the donor profile with a valid user reference
        Donor_Profiles_Model saved = donorRepo.save(donor);

        // 🧠 Force loading the user for full JSON serialization
        saved.getUser().getFullName();

        return saved;
    }

    /**
     * Retrieves a specific donor profile by its unique ID.
     *
     * <p>Example response:
     * <pre>
     * {
     *   "donorId": 1,
     *   "bloodType": "O",
     *   "rhesus": "-",
     *   "availability": "Unavailable",
     *   "user": {
     *     "userId": 5,
     *     "fullName": "John Doe",
     *     "email": "john@example.com"
     *   }
     * }
     * </pre>
     *
     * @param id the unique identifier of the donor profile
     * @return the matching {@link Donor_Profiles_Model} record
     * @throws RuntimeException if the donor profile with the given ID does not exist
     */
    @GetMapping("/{id}")
    public Donor_Profiles_Model getOne(@PathVariable Long id) {
        Donor_Profiles_Model donor = donorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found with ID: " + id));

        // Ensure that the user object is fully initialized for serialization
        donor.getUser().getFullName();
        return donor;
    }

    /**
     * Deletes a donor profile by its unique identifier.
     *
     * <p>Returns HTTP 204 (No Content) even if the donor profile no longer exists.</p>
     *
     * @param id the ID of the donor profile to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        donorRepo.deleteById(id);
    }
}
