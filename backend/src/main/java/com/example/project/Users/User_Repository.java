package com.example.project.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface User_Repository extends JpaRepository<User_Model, Long> {
    Optional<User_Model> findByEmail(String email);
}
