package com.example.aitutor.chatgpt;

import java.util.List;

class ChatGPTResponse {

    private List<Choice> choices;

    static class Choice {

        private int index;

        private ChatGPTMessage message;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public ChatGPTMessage getMessage() {
            return message;
        }

        public void setMessage(ChatGPTMessage message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "Choice{" +
                    "index=" + index +
                    ", responseMessage=" + message +
                    '}';
        }
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "ChatGPTResponse{" +
                "choices=" + choices +
                '}';
    }
}