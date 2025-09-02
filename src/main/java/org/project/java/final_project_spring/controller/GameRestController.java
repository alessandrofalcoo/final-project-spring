package org.project.java.final_project_spring.controller;

import java.util.Optional;

import org.project.java.final_project_spring.model.Game;
import org.project.java.final_project_spring.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/games")
public class GameRestController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public Page<Game> index(Pageable pageable) {
        Page<Game> games = gameService.findAll(pageable);
        return games;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> show(@PathVariable Integer id) {
        Optional<Game> gameAttempt = gameService.findById(id);
        if (gameAttempt.isEmpty()) {
            return new ResponseEntity<Game>(HttpStatusCode.valueOf(404));
        }

        return new ResponseEntity<Game>(HttpStatusCode.valueOf(200));
    }
}
