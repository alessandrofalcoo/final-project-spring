package org.project.java.final_project_spring.controller;

import org.project.java.final_project_spring.model.Dev;
import org.project.java.final_project_spring.model.Game;
import org.project.java.final_project_spring.repository.GameRepository;
import org.project.java.final_project_spring.service.DevService;
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
@RequestMapping("/devs")
public class DevController {

    @Autowired
    private DevService devService;

    @Autowired
    private GameRepository gameRepository;

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, Model model) {
        Dev dev = devService.getById(id);
        Pageable pageable = PageRequest.of(page, size);
        Page<Game> gamesPage = gameRepository.findByDev(dev, pageable);
        model.addAttribute("games", gamesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", gamesPage.getTotalPages());
        model.addAttribute("dev", dev);
        return "devs/show";
    }

    @GetMapping("/create")
    public String create(Model model, Dev formDev) {
        model.addAttribute("dev", new Dev());

        return "devs/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("dev") Dev formDev, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "devs/create";
        }

        devService.create(formDev);
        return "redirect:/games";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        devService.deleteById(id);
        return "redirect:/games";
    }
}
