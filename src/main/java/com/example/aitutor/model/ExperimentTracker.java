package com.example.aitutor.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tracker")
public class ExperimentTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "experimentId")
    private long experimentId;

    @Column(name = "details", columnDefinition = "text")
    private String details;

    @CreationTimestamp
    private Instant timestamp;

    @Column(name = "metricType")
    @Enumerated(EnumType.STRING)
    private ExperimentMetricType metricType;

    public ExperimentTracker() {
    }

    private ExperimentTracker(long experimentId, String details, ExperimentMetricType metricType) {
        this.experimentId = experimentId;
        this.details = details;
        this.metricType = metricType;
    }

    public static ExperimentTracker trackExecution(Long experimentId, String executionResult) {
        return new ExperimentTracker(experimentId, executionResult, ExperimentMetricType.EXECUTION);
    }

    public static ExperimentTracker trackHintGeneration(Long experimentId, String hintText) {
        return new ExperimentTracker(experimentId, hintText, ExperimentMetricType.HINT);
    }

    public static ExperimentTracker trackExperimentCreation(long experimentId) {
        return new ExperimentTracker(experimentId, null, ExperimentMetricType.CREATED);
    }

    public long getId() {
        return id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(long experimentId) {
        this.experimentId = experimentId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ExperimentMetricType getMetricType() {
        return metricType;
    }

    public void setMetricType(ExperimentMetricType metricType) {
        this.metricType = metricType;
    }

    @Override
    public String toString() {
        return "ExperimentTracker{" +
                "id=" + id +
                ", experimentId=" + experimentId +
                ", details='" + details + '\'' +
                ", timestamp=" + timestamp +
                ", metricType=" + metricType +
                '}';
    }
}
