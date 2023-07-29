package com.example.aitutor.chatgpt;

import java.util.ArrayList;
import java.util.List;

class ChatGPTRequest {

    private String model;
    private String prompt;
    private int n = 1;
    private double temperature;

    public ChatGPTRequest(String model, String prompt) {
        this.model = model;

        this.prompt = prompt;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "ChatGPTRequest{" +
                "model='" + model + '\'' +
                ", prompt='" + prompt + '\'' +
                ", n=" + n +
                ", temperature=" + temperature +
                '}';
    }
}
