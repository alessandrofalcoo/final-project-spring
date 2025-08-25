package org.project.java.final_project_spring.controller;

import java.util.List;

import org.project.java.final_project_spring.model.Game;
import org.project.java.final_project_spring.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
