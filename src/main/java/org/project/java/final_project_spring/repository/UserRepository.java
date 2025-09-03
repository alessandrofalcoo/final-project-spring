package org.project.java.final_project_spring.repository;

import java.util.Optional;

import org.project.java.final_project_spring.controller.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
