package org.project.java.final_project_spring.controller;

import java.util.List;

import org.project.java.final_project_spring.model.Dev;
import org.project.java.final_project_spring.model.Game;
import org.project.java.final_project_spring.repository.DevRepository;
import org.project.java.final_project_spring.repository.GameRepository;
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
@RequestMapping("/devs")
public class DevController {

    @Autowired
    private DevRepository devRepository;

    @Autowired
    private GameRepository gameRepository;

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Dev dev = devRepository.findById(id).orElseThrow(() -> new RuntimeException("Dev not found"));
        List<Game> games = gameRepository.findByDev(dev);
        model.addAttribute("games", games);
        model.addAttribute("dev", dev);
        return "/devs/show";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("dev") BindingResult bindingResult, Model model, Dev formDev) {
        if (bindingResult.hasErrors()) {
            return "devs/create-or-edit";
        }

        devRepository.save(formDev);
        return "redirect:/games" + formDev.getGames();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("devs", devRepository.findById(id).get());

        model.addAttribute("edit", true);
        return "devs/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("dev") BindingResult bindingResult, Model model, Dev formDev) {
        if (bindingResult.hasErrors()) {
            return "devs/create-or-edit";
        }

        devRepository.save(formDev);
        return "redirect:/games";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Dev dev = devRepository.findById(id).orElseThrow();

        devRepository.delete(dev);
        return "redirect:/games";
    }
}
