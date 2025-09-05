package org.project.java.final_project_spring.controller;

import java.util.List;
import java.util.Optional;

import org.project.java.final_project_spring.model.Genre;
import org.project.java.final_project_spring.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/genre")
@CrossOrigin(origins = "http://localhost:5173")
public class GenreRestController {
    @Autowired
    private GenreService genreService;

    @GetMapping
    public List<Genre> index() {
        return genreService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> show(@PathVariable Integer id) {
        Optional<Genre> genreAttempt = genreService.findById(id);
        if (genreAttempt.isEmpty()) {
            return new ResponseEntity<Genre>(HttpStatusCode.valueOf(404));
        }
        return new ResponseEntity<Genre>(genreAttempt.get(), HttpStatusCode.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<Genre> store(@RequestBody Genre genre) {
        Genre newGenre = genreService.create(genre);
        return new ResponseEntity<Genre>(newGenre, HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Genre> delete(@PathVariable Integer id) {
        if (genreService.findById(id).isEmpty()) {
            return new ResponseEntity<Genre>(HttpStatusCode.valueOf(404));
        }
        genreService.deleteById(id);
        return new ResponseEntity<Genre>(HttpStatusCode.valueOf(200));
    }

}
