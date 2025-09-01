package org.project.java.final_project_spring.controller;

import java.util.List;

import org.project.java.final_project_spring.model.Dev;
import org.project.java.final_project_spring.model.Game;
import org.project.java.final_project_spring.model.Genre;
import org.project.java.final_project_spring.repository.ConsoleRepository;
import org.project.java.final_project_spring.repository.DevRepository;
import org.project.java.final_project_spring.repository.GameRepository;
import org.project.java.final_project_spring.repository.GenreRepository;
import org.project.java.final_project_spring.service.GameService;
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
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;

    @Autowired
    private ConsoleRepository consoleRepository;

    @Autowired
    private DevRepository devRepository;

    @Autowired
    private GenreRepository genreRepository;

    @GetMapping()
    public String index(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Game> gamesPage = gameRepository.findAll(pageable);
        int totalPages = gamesPage.getTotalPages();
        model.addAttribute("games", gamesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("devs", devRepository.findAll());

        return "games/index";
    }

    @GetMapping("/dev/{id}")
    public String showDev(@PathVariable("id") Integer id, Model model) {
        Dev dev = devRepository.findById(id).get();
        List<Game> games = gameRepository.findAll();

        model.addAttribute("dev", dev);
        model.addAttribute("games", games);

        return "games/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Game game = gameRepository.findById(id).get();
        model.addAttribute("game", game);
        return "games/show";
    }

    @GetMapping("/searchByName")
    public String searchByName(@RequestParam(name = "title") String title, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Game> gamesPage = gameRepository.findByTitleContaining(title, pageable);
        model.addAttribute("games", gamesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", gamesPage.getTotalPages());
        model.addAttribute("searchTitle", title);
        return "games/index";
    }

    @GetMapping("/filters")
    public String filters(@RequestParam(required = false) Integer genreId,
            @RequestParam(required = false) Integer devId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, Model model) {

        Page<Game> gamesPage = gameService.getFilteredGames(genreId, devId, page, size);

        model.addAttribute("games", gamesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", gamesPage.getTotalPages());

        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("devs", devRepository.findAll());

        model.addAttribute("searchGenreId", genreId);
        model.addAttribute("searchDevId", devId);

        return "games/index";
    }

    // Mapping per reinderizzare alla view "create"
    @GetMapping("/create")
    public String add(Model model) {
        model.addAttribute("game", new Game());
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("devs", devRepository.findAll());
        model.addAttribute("consoles", consoleRepository.findAll());
        return "games/create";
    }

    // Mapping per validare le informazioni all'interno del form di creazione del
    // nuovo gioco
    @PostMapping("/create")
    public String store(Model model, @Valid @ModelAttribute("game") Game formGame, BindingResult bindingResult,
            @RequestParam Integer dev, @RequestParam Integer genre) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("devs", devRepository.findAll());
            model.addAttribute("genres", genreRepository.findAll());
            return "games/create";
        }
        Dev devObj = devRepository.findById(dev).get();
        Genre genreObj = genreRepository.findById(genre).get();

        formGame.setDev(devObj);
        formGame.setGenre(genreObj);

        gameRepository.save(formGame);
        return "redirect:/games";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("game", gameRepository.getReferenceById(id));
        model.addAttribute("devs", devRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("consoles", consoleRepository.findAll());

        return "games/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("game") Game formGame, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("devs", devRepository.findAll());
            model.addAttribute("genres", genreRepository.findAll());
            model.addAttribute("consoles", consoleRepository.findAll());

            return "games/edit";
        }

        gameRepository.save(formGame);
        return "redirect:/games";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Game game = gameRepository.getReferenceById(id);
        gameRepository.delete(game);

        return "redirect:/games";
    }
}
