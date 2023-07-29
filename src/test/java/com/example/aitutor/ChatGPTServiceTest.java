package com.example.aitutor;

import com.example.aitutor.chatgpt.ChatGPTService;
import com.example.aitutor.chatgpt.CodeCorrectness;
import com.example.aitutor.controller.dto.TutorRequest;
import com.example.aitutor.model.Experiment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ChatGPTServiceTest {

    @Autowired
    private ChatGPTService chatGPTService;

    @Test
    public void testCodeCorrectness_whenCorrectCodeIsPassed() {
        TutorRequest tutorRequest = new TutorRequest();
        tutorRequest.setCode("class Fibonacci {\n" +
                "  public static void main(String[] args) {\n" +
                "\n" +
                "    int n = 100, firstTerm = 0, secondTerm = 1;\n" +
                "        \n" +
                "    System.out.println(\"Fibonacci Series Upto \" + n + \": \");\n" +
                "    \n" +
                "    while (firstTerm <= n) {\n" +
                "      System.out.print(firstTerm + \", \");\n" +
                "\n" +
                "      int nextTerm = firstTerm + secondTerm;\n" +
                "      firstTerm = secondTerm;\n" +
                "      secondTerm = nextTerm;\n" +
                "\n" +
                "    }\n" +
                "  }\n" +
                "}");
        Experiment exp = new Experiment("Display Fibonacci series up to a given number");
        CodeCorrectness result = chatGPTService.isTheCodeCorrect(tutorRequest, exp);

        assertEquals(CodeCorrectness.CORRECT, result);
    }
}
