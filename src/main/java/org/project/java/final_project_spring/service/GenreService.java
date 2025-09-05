package org.project.java.final_project_spring.service;

import java.util.List;
import java.util.Optional;

import org.project.java.final_project_spring.model.Genre;
import org.project.java.final_project_spring.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Optional<Genre> findById(Integer id) {
        return genreRepository.findById(id);
    }

    public Genre getById(Integer id) {
        Optional<Genre> genreAttempt = genreRepository.findById(id);
        return genreAttempt.get();
    }

    public Genre create(Genre genre) {
        return genreRepository.save(genre);
    }

    public void deleteById(Integer id) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        genreRepository.delete(genre);
    }
}
