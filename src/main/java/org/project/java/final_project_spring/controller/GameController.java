package org.project.java.final_project_spring.controller;

import java.util.List;
import java.util.Optional;

import org.project.java.final_project_spring.model.Game;
import org.project.java.final_project_spring.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public String index(Model model) {

        List<Game> games = gameRepository.findAll();
        model.addAttribute("games", games);

        return "games/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Optional<Game> game = gameRepository.findById(id);
        if (game.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "This game doesn't exist");
        }
        model.addAttribute("game", game);
        return "games/show";
    }
}
