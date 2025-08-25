package org.project.java.final_project_spring.model;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;

public class Console {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "The name cannot be null, empty or blank")
    private String name;

    @ManyToMany
    @JoinTable(name = "game_console", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "console_id"))
    private List<Game> games;
}
