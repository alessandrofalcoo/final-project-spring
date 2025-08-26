package org.project.java.final_project_spring.repository;

import org.project.java.final_project_spring.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

}
