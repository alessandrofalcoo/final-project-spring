package org.project.java.final_project_spring.controller;

import org.project.java.final_project_spring.model.Game;
import org.project.java.final_project_spring.model.Genre;
import org.project.java.final_project_spring.repository.GameRepository;
import org.project.java.final_project_spring.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, Model model) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new RuntimeException("Genre not found"));
        Pageable pageable = PageRequest.of(page, size);
        Page<Game> gamesPage = gameRepository.findByGenre(genre, pageable);

        model.addAttribute("games", gamesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", gamesPage.getTotalPages());
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
