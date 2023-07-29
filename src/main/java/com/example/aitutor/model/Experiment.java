package com.example.aitutor.model;

import jakarta.persistence.*;

@Entity
@Table(name = "experiments")
public class Experiment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "problem")
    private String problem;

    public Experiment() {
    }

    public Experiment(String problem) {
        this.problem = problem;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    @Override
    public String toString() {
        return "Experiment{" +
                "id=" + id +
                ", problem='" + problem + '\'' +
                '}';
    }
}
