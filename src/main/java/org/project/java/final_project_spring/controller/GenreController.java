package org.project.java.final_project_spring.controller;

import java.util.List;

import org.project.java.final_project_spring.model.Game;
import org.project.java.final_project_spring.model.Genre;
import org.project.java.final_project_spring.repository.GameRepository;
import org.project.java.final_project_spring.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new RuntimeException("Genre not found"));
        List<Game> games = gameRepository.findByGenre(genre);
        model.addAttribute("games", games);
        model.addAttribute("genre", genre);
        return "genres/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("genre", new Genre());

        return "genres/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("genre") Genre formGenre, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "genres/create";
        }
        genreRepository.save(formGenre);

        return "redirect:/games";

    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Genre genre = genreRepository.findById(id).orElseThrow();

        genreRepository.delete(genre);
        return "redirect:/games";
    }
}
