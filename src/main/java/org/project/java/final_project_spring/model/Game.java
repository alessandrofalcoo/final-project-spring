package org.project.java.final_project_spring.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @Column(name = "title")
    private String title;

    @NotBlank(message = "The year cannot be null, empty or blank")
    @Column(name = "year-of-publication")
    private LocalDate year;

    @NotNull(message = "The price must not be null")
    @Positive(message = "The price must be greater than zero")
    @Column(name = "price")
    private Float price;

    @OneToMany(mappedBy = "games")
    @JoinColumn(name = "dev_id", nullable = false)
    private Dev dev;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @ManyToMany
    @JoinTable(name = "game_console", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "console_id"))
    private List<Console> consoles;

    public Game() {
    }

    public Game(Integer id, String title, LocalDate year, Float price, Dev dev, Genre genre,
            List<Console> consoles) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.price = price;
        this.dev = dev;
        this.genre = genre;
        this.consoles = consoles;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getYear() {
        return this.year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Dev getDev() {
        return this.dev;
    }

    public void setDev(Dev dev) {
        this.dev = dev;
    }

    public Genre getGenre() {
        return this.genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Console> getConsoles() {
        return this.consoles;
    }

    public void setConsoles(List<Console> consoles) {
        this.consoles = consoles;
    }

}
