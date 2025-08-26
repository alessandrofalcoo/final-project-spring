package org.project.java.final_project_spring.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "consoles")
public class Console {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "The name cannot be null, empty or blank")
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "consoles")
    @JsonIgnore
    private List<Game> games;

    public Console() {
    }

    public Console(Integer id, String name, List<Game> games) {
        this.id = id;
        this.name = name;
        this.games = games;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Game> getGames() {
        return this.games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        return String.format(" %s", this.name);
    }
}
