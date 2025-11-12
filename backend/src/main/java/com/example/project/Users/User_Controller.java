package com.example.project.Users;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for managing user accounts in the system.
 * <p>
 * This controller exposes endpoints to list, create, and delete users.
 * User records serve as the foundation for authentication, donor profiles,
 * and other linked entities throughout the application.
 *
 * <p><b>Base Path:</b> {@code /api/Users}
 *
 * <p><b>Supported Operations:</b>
 * <ul>
 *   <li><b>GET /api/Users</b> — Retrieve all users.</li>
 *   <li><b>POST /api/Users</b> — Create a new user record.</li>
 *   <li><b>DELETE /api/Users/{user_id}</b> — Delete an existing user by ID.</li>
 * </ul>
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/api/Users")
public class User_Controller {

    /**
     * Repository used for performing CRUD operations on user records.
     */
    private final User_Repository repo;

    /**
     * Constructs a new {@code User_Controller} with the specified repository.
     *
     * @param repo the repository responsible for user persistence
     */
    public User_Controller(User_Repository repo) {
        this.repo = repo;
    }

    /**
     * Retrieves all registered users from the database.
     *
     * <p>Example response:
     * <pre>
     * [
     *   {
     *     "userId": 1,
     *     "fullName": "John Doe",
     *     "email": "john@example.com",
     *     "phone": "0501234567",
     *     "role": "Donor",
     *     "homeAddress": "Dubai Marina, UAE",
     *     "bloodType": "A",
     *     "rhesus": "+"
     *   },
     *   {
     *     "userId": 2,
     *     "fullName": "Sarah Ahmed",
     *     "email": "sarah@example.com",
     *     "role": "Recipient"
     *   }
     * ]
     * </pre>
     *
     * @return a list of {@link User_Model} instances
     */
    @GetMapping
    public List<User_Model> all() {
        return repo.findAll();
    }

    /**
     * Creates a new user record in the database.
     *
     * <p>The request body must contain all required user fields and pass validation
     * as defined in {@link User_Model}. The created user is immediately persisted
     * and returned with an auto-generated ID.
     *
     * <p>Example request body:
     * <pre>
     * {
     *   "fullName": "Ali Hassan",
     *   "email": "ali@example.com",
     *   "phone": "0502345678",
     *   "passwordHash": "hashedPassword123",
     *   "role": "Donor",
     *   "homeAddress": "Abu Dhabi",
     *   "bloodType": "O",
     *   "rhesus": "-"
     * }
     * </pre>
     *
     * <p>Returns HTTP 201 (Created) on success.</p>
     *
     * @param user the {@link User_Model} object containing user data
     * @return the persisted {@link User_Model} with an assigned ID
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User_Model create(@Valid @RequestBody User_Model user) {
        return repo.save(user);
    }

    /**
     * Deletes a user record by its unique identifier.
     *
     * <p>Returns HTTP 204 (No Content) whether or not the user existed, following REST conventions.</p>
     *
     * <p>Example endpoint:</p>
     * <pre>
     * DELETE /api/Users/5
     * </pre>
     *
     * @param userId the ID of the user to delete
     */
    @DeleteMapping("/{user_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("user_id") Long userId) {
        repo.deleteById(userId);
    }


}
