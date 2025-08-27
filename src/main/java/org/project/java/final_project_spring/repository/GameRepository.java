package org.project.java.final_project_spring.repository;

import java.util.List;

import org.project.java.final_project_spring.model.Dev;
import org.project.java.final_project_spring.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {
    public List<Game> findByTitleContaining(String title);

    List<Game> findByDev(Dev dev);
}
