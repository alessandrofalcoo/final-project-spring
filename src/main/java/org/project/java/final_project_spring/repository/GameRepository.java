package org.project.java.final_project_spring.repository;

import org.project.java.final_project_spring.model.Dev;
import org.project.java.final_project_spring.model.Game;
import org.project.java.final_project_spring.model.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {

    public Page<Game> findByTitleContaining(String title, Pageable pageable);

    public Page<Game> findByDev(Dev dev, Pageable pageable);

    public Page<Game> findByGenre(Genre genre, Pageable pageable);
}
