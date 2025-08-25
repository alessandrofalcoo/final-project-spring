package org.project.java.final_project_spring.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "The title cannot be null, empty or blank")
    private String title;

    @NotBlank(message = "The year cannot be null, empty or blank")
    private LocalDate year;

    @NotNull(message = "The price must not be null")
    @Positive(message = "The price must be greater than zero")
    private Float price;
}
