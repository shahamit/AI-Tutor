package com.example.aitutor.controller.dto;

public class TutorRequest {

    private String model;

    private String prompt;

    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "TutorRequest{" +
                "model='" + model + '\'' +
                ", prompt='" + prompt + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
