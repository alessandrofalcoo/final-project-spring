package org.project.java.final_project_spring.service;

import java.util.Optional;

import org.project.java.final_project_spring.model.Dev;
import org.project.java.final_project_spring.repository.DevRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DevService {
    @Autowired
    private DevRepository devRepository;

    public Optional<Dev> findById(Integer id) {
        return devRepository.findById(id);
    }

    public Dev getById(Integer id) {
        Optional<Dev> devAttempt = devRepository.findById(id);
        return devAttempt.get();
    }

    public Dev create(Dev dev) {
        return devRepository.save(dev);
    }

    public void deleteById(Integer id) {
        Dev dev = devRepository.findById(id).orElseThrow();
        devRepository.delete(dev);
    }
}
