package org.project.java.final_project_spring.controller;

import java.util.Optional;

import org.project.java.final_project_spring.model.Game;
import org.project.java.final_project_spring.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games")
public class GameRestController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public Page<Game> index(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Game> games = gameService.findAll(pageable);
        return games;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> show(@PathVariable Integer id) {
        Optional<Game> gameAttempt = gameService.findById(id);
        if (gameAttempt.isEmpty()) {
            return new ResponseEntity<Game>(HttpStatusCode.valueOf(404));
        }

        return new ResponseEntity<Game>(gameAttempt.get(), HttpStatusCode.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<Game> store(@RequestBody Game game) {
        Game newGame = gameService.create(game);
        return new ResponseEntity<Game>(newGame, HttpStatusCode.valueOf(200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> update(@RequestBody Game game, @PathVariable Integer id) {
        if (gameService.findById(id).isEmpty()) {
            return new ResponseEntity<Game>(HttpStatusCode.valueOf(404));
        }
        game.setId(id);
        Game modifiedGame = gameService.update(game);
        return new ResponseEntity<Game>(modifiedGame, HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Game> delete(@PathVariable Integer id) {
        if (gameService.findById(id).isEmpty()) {
            return new ResponseEntity<Game>(HttpStatusCode.valueOf(404));
        }
        gameService.deleteById(id);
        return new ResponseEntity<Game>(HttpStatusCode.valueOf(200));
    }
}
