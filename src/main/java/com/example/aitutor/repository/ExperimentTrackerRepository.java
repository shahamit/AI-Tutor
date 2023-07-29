package com.example.aitutor.repository;

import com.example.aitutor.model.ExperimentTracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperimentTrackerRepository extends JpaRepository<ExperimentTracker, Long> {
    List<ExperimentTracker> findByExperimentId(Long experimentId);
}
