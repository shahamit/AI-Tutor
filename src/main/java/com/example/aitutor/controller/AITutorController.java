package com.example.aitutor.controller;

import com.example.aitutor.chatgpt.ChatGPTService;
import com.example.aitutor.chatgpt.CodeCorrectness;
import com.example.aitutor.controller.dto.TutorRequest;
import com.example.aitutor.model.Experiment;
import com.example.aitutor.model.ExperimentTracker;
import com.example.aitutor.repository.ExperimentRepository;
import com.example.aitutor.repository.ExperimentTrackerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Autowired
    private ExperimentTrackerRepository experimentTrackerRepository;

    @Autowired
    private ChatGPTService chatGPTService;

    @PostMapping(path = "/experiments")
    public ResponseEntity<Experiment> createExperiment(@RequestBody Experiment experiment) {
        Experiment e = experimentRepository.save(new Experiment(experiment.getProblem()));
        experimentTrackerRepository.save(ExperimentTracker.trackExperimentCreation(e.getId()));
        logger.info("Registered a new experiment : {}", e);
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
    @PostMapping(path = "/experiments/{id}/execute", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String executeCode(@PathVariable("id") long experimentId, @RequestBody TutorRequest tutorRequest) {
        CodeCorrectness codeCorrectness = chatGPTService.isTheCodeCorrect(tutorRequest,
                                                            experimentRepository.getReferenceById(experimentId));
        if(codeCorrectness == CodeCorrectness.CORRECT) {
            experimentTrackerRepository.save(ExperimentTracker.trackExecution(experimentId, CodeCorrectness.CORRECT.name()));
            return "Your code is correct";
        } else if (codeCorrectness == CodeCorrectness.NORESPONSE) {
            experimentTrackerRepository.save(ExperimentTracker.trackExecution(experimentId, CodeCorrectness.CORRECT.name()));
            return "No response from chat gpt";
        }
        experimentTrackerRepository.save(ExperimentTracker.trackExecution(experimentId, CodeCorrectness.INCORRECT.name()));
        return "Your code is not correct";
    }
    @PostMapping(path = "/experiments/{id}/hints", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String generateCodeHint(@PathVariable("id") long experimentId, @RequestBody TutorRequest tutorRequest) {
        logger.info("Generating hint for input : {}", tutorRequest);
        String hintText = chatGPTService.generateHint(tutorRequest, experimentRepository.getReferenceById(experimentId));
        experimentTrackerRepository.save(ExperimentTracker.trackHintGeneration(experimentId, hintText));
        return hintText;
    }

    @GetMapping(path = "/experiments/{id}/metrics")
    public ResponseEntity<List<ExperimentTracker>> getMetrics(@PathVariable("id") long experimentId) {
        return new ResponseEntity<>(experimentTrackerRepository.findByExperimentId(experimentId), HttpStatus.OK);
    }
}
