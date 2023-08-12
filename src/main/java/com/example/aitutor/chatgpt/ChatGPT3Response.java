package com.example.aitutor.chatgpt;

import java.util.List;

class ChatGPT3Response {

    private List<Choice> choices;

    static class Choice {

        private int index;
        private String text;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return "Choice{" +
                    "index=" + index +
                    ", text='" + text + '\'' +
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