package org.project.java.final_project_spring.controller;

import java.util.Optional;

import org.project.java.final_project_spring.model.Dev;
import org.project.java.final_project_spring.service.DevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dev")
public class DevRestController {
    @Autowired
    private DevService devService;

    @GetMapping("/{id}")
    public ResponseEntity<Dev> show(@PathVariable Integer id) {
        Optional<Dev> devAttempt = devService.findById(id);
        if (devAttempt.isEmpty()) {
            return new ResponseEntity<Dev>(HttpStatusCode.valueOf(404));
        }
        return new ResponseEntity<Dev>(devAttempt.get(), HttpStatusCode.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<Dev> store(@RequestBody Dev dev) {
        Dev newDev = devService.create(dev);
        return new ResponseEntity<Dev>(newDev, HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Dev> delete(@PathVariable Integer id) {
        if (devService.findById(id).isEmpty()) {
            return new ResponseEntity<Dev>(HttpStatusCode.valueOf(404));
        }
        devService.deleteById(id);
        return new ResponseEntity<Dev>(HttpStatusCode.valueOf(200));
    }
}
