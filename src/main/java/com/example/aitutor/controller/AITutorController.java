package com.example.aitutor.controller;

import com.example.aitutor.controller.dto.TutorRequest;
import com.example.aitutor.model.Experiment;
import com.example.aitutor.repository.ExperimentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AITutorController {

    private static Logger logger = LoggerFactory.getLogger(AITutorController.class);

    @Autowired
    private ExperimentRepository experimentRepository;


    @PostMapping(path = "/experiments")
    public ResponseEntity<Experiment> createExperiment(@RequestBody Experiment experiment) {
        Experiment e = experimentRepository.save(new Experiment(experiment.getProblem()));
        return new ResponseEntity<>(e, HttpStatus.CREATED);
    }

    @GetMapping(path = "/experiments")
    public ResponseEntity<List<Experiment>> getAllExperiments() {
        return new ResponseEntity<>(experimentRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/experiments/{id}")
    public ResponseEntity<Experiment> getExperimentById(@PathVariable("id") long id) {
        Optional<Experiment> experiment = experimentRepository.findById(id);
        return experiment.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping(path = "/experiments/{id}/execute")
    public void executeCode(@RequestBody TutorRequest tutorRequest) {

    }
}
