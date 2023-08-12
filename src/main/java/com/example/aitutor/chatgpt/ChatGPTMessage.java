package com.example.aitutor.chatgpt;

public class ChatGPTMessage {

    private String role = "user";

    private String content;

    public ChatGPTMessage() {
    }

    public ChatGPTMessage(String content) {
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ChatGPTMessage{" +
                "role='" + role + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
