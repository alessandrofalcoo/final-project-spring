package org.project.java.final_project_spring.service;

import java.util.List;
import java.util.Optional;

import org.project.java.final_project_spring.model.Dev;
import org.project.java.final_project_spring.model.Game;
import org.project.java.final_project_spring.model.Genre;
import org.project.java.final_project_spring.repository.DevRepository;
import org.project.java.final_project_spring.repository.GameRepository;
import org.project.java.final_project_spring.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private DevRepository devRepository;

    @Autowired
    private GenreRepository genreRepository;

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Game getById(Integer id) {
        Optional<Game> gameAttempt = gameRepository.findById(id);
        if (gameAttempt.isEmpty()) {

        }
        return gameAttempt.get();
    }

    public Page<Game> findByTitle(String title, Pageable pageable) {
        return gameRepository.findByTitleContaining(title, pageable);

    }

    public Optional<Game> findById(Integer id) {
        return gameRepository.findById(id);
    }

    public Game create(Game game) {
        if (game.getDev() != null) {
            Dev dev = devRepository.findById(game.getDev().getId())
                    .orElseThrow(() -> new RuntimeException("Dev not found"));
            game.setDev(dev);
            dev.getGames().add(game);
        }
        if (game.getGenre() != null) {
            Genre genre = genreRepository.findById(game.getGenre().getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found"));
            game.setGenre(genre);
            genre.getGames().add(game);
        }
        return gameRepository.save(game);
    }

    public Game update(Game game) {
        return gameRepository.save(game);
    }

    public void delete(Game game) {
        if (game.getDev() != null) {
            Dev dev = game.getDev();
            dev.getGames().remove(game);
        }

        if (game.getGenre() != null) {
            Genre genre = game.getGenre();
            genre.getGames().remove(game);
        }

        gameRepository.delete(game);
    }

    public void deleteById(Integer id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));

        if (game.getDev() != null) {
            Dev dev = game.getDev();
            dev.getGames().remove(game);
        }

        if (game.getGenre() != null) {
            Genre genre = game.getGenre();
            genre.getGames().remove(game);
        }

        if (game.getConsoles() != null) {
            game.getConsoles().clear();
        }

        gameRepository.delete(game);
    }
}