package com.example.aitutor.chatgpt;

import com.example.aitutor.controller.dto.TutorRequest;
import com.example.aitutor.model.Experiment;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatGPTService {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.model}")
    private String defaultOpenAPIModel;

    private Logger logger = LoggerFactory.getLogger(ChatGPTService.class);

    public CodeCorrectness isTheCodeCorrect(TutorRequest tutorRequest, Experiment experiment) {
        String prompt = getCodeCorrectnessPrompt(tutorRequest, experiment);
        String model = getModel(tutorRequest.getModel());

        // create a request
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);

        logger.info("Sending request to chatgpt : {}", request);
        // call the API
        ChatGPTResponse response = restTemplate.postForObject(apiUrl, request, ChatGPTResponse.class);
        logger.info("Got a response from chatgpt : {}", response);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return CodeCorrectness.NORESPONSE;
        }

        // return the first response
        String responseString = response.getChoices().get(0).getMessage().getContent();
        if(responseString.equalsIgnoreCase(CodeCorrectness.CORRECT.toString())) {
            return CodeCorrectness.CORRECT;
        }
        return CodeCorrectness.INCORRECT;
    }

    private String getModel(String model) {
        return null == model ? defaultOpenAPIModel : model;
    }

    private String getCodeCorrectnessPrompt(TutorRequest tutorRequest, Experiment experiment) {
        final String DEFAULT_CODE_CORRECTNESS_PROMPT = "Imagine you are a java programming teaching instructor, " +
                "this is the problem statement \"${problem}\". Below is the solution that a student has coded.  \n" +
                "${student_code}   \n" +
                "Tell me in one word if the above code is correct or incorrect";
        String prompt = tutorRequest.getPrompt() == null? DEFAULT_CODE_CORRECTNESS_PROMPT: tutorRequest.getPrompt();

        Map<String, Object> params = new HashMap<>();
        params.put("student_code", tutorRequest.getCode());
        params.put("problem", experiment.getProblem());
        return StringSubstitutor.replace(prompt, params, "${", "}");
    }
}
