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
import java.util.Optional;

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
        // create a request
        Optional<String> responseString = callChatGPTAPI(tutorRequest.getModel(), prompt);

        if (responseString.isEmpty()) return CodeCorrectness.NORESPONSE;
        if (responseString.get().toLowerCase().contains(CodeCorrectness.INCORRECT.name().toLowerCase())) {
            return CodeCorrectness.INCORRECT;
        }
        return CodeCorrectness.CORRECT;
    }

    private Optional<String> callLegacyChatGPTAPI(String inputModel, String prompt) {
        String model = getModel(inputModel);
        ChatGPT3Request request = new ChatGPT3Request(model, prompt);

        logger.info("Sending request to chatgpt : {}", request);
        // call the API
        ChatGPT3Response response = restTemplate.postForObject(apiUrl, request, ChatGPT3Response.class);
        logger.info("Got a response from chatgpt : {}", response);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return Optional.empty();
        }

        // return the first response
        return Optional.of(response.getChoices().get(0).getText().trim());
    }

    private Optional<String> callChatGPTAPI(String inputModel, String prompt) {
        String model = getModel(inputModel);
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);

        logger.info("Sending request to chatgpt : {}", request);
        // call the API
        ChatGPTResponse response = restTemplate.postForObject(apiUrl, request, ChatGPTResponse.class);
        logger.info("Got a response from chatgpt : {}", response);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()
                || response.getChoices().get(0).getMessage() == null) {
            return Optional.empty();
        }

        // return the first response
        return Optional.of(response.getChoices().get(0).getMessage().getContent().trim());
    }

    private String getModel(String model) {
        return null == model ? defaultOpenAPIModel : model;
    }

    private String getCodeCorrectnessPrompt(TutorRequest tutorRequest, Experiment experiment) {
        final String DEFAULT_CODE_CORRECTNESS_PROMPT = "Imagine you are a java programming teaching instructor, " +
                "this is the problem statement \"${problem}\". Below is the solution that a student has coded.  \n" +
                "${student_code}   \n" +
                "Tell me in one word if the above code is correct or incorrect";
        String prompt = tutorRequest.getPrompt() == null ? DEFAULT_CODE_CORRECTNESS_PROMPT : tutorRequest.getPrompt();

        return replaceStandardParametersInPrompt(tutorRequest, experiment, prompt);
    }

    private String replaceStandardParametersInPrompt(TutorRequest tutorRequest, Experiment experiment, String prompt) {
        Map<String, Object> params = new HashMap<>();
        params.put("student_code", tutorRequest.getCode());
        params.put("problem", experiment.getProblem());
        return StringSubstitutor.replace(prompt, params, "${", "}");
    }

    public String generateHint(TutorRequest tutorRequest, Experiment experiment) {
        String prompt = replaceStandardParametersInPrompt(tutorRequest, experiment, tutorRequest.getPrompt());
        Optional<String> response = callChatGPTAPI(tutorRequest.getModel(), prompt);
        return response.orElse("AI Tutor could not generate any hints");
    }
}
