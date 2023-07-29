package com.example.aitutor.repository;

import com.example.aitutor.model.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperimentRepository extends JpaRepository<Experiment, Long> {
}
