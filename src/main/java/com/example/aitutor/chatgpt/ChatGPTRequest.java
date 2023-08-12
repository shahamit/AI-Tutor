package com.example.aitutor.chatgpt;

import java.util.ArrayList;
import java.util.List;

class ChatGPTRequest {

    private String model;

    private List<ChatGPTMessage> messages = new ArrayList<>();

    public ChatGPTRequest(String model, String prompt) {
        this.model = model;
        this.messages.add(new ChatGPTMessage(prompt));
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ChatGPTMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatGPTMessage> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "ChatGPTRequest{" +
                "model='" + model + '\'' +
                ", messages=" + messages +
                '}';
    }
}
