package com.javaopenai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.javaopenai.dto.ChatGPTRequest;
import com.javaopenai.dto.ChatGptResponse;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/bot")
public class CustomBotController {

    @Value("${openai.model}")
    private String model;

    @Value(("${openai.api.url}"))
    private String apiURL;

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/chat")
    public String chat(@RequestParam("prompt") String prompt) {
    	
    	System.out.println("Prompt: " + prompt);
    	
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        
        ChatGptResponse chatGptResponse = restTemplate.postForObject(apiURL, request, ChatGptResponse.class);
        
        if (chatGptResponse == null || chatGptResponse.getChoices() == null || chatGptResponse.getChoices().isEmpty()) {
            return "No response";
        }
        
        return chatGptResponse.getChoices().get(0).getMessage().getContent();
        
    }
}

/*
@Qualifier("openaiRestTemplate")
@Autowired
private RestTemplate restTemplate;

@Value("${openai.model}")
private String model;

@Value("${openai.api.url}")
private String apiUrl;


	Creates a chat request and sends it to the OpenAI API
	Returns the first message from the API response

	@param prompt the prompt to send to the API
	@return first message from the API response

@GetMapping("/chat")
public String chat(@RequestParam String prompt) {
    ChatRequest request = new ChatRequest(model, prompt);

    ChatResponse response = restTemplate.postForObject(
            apiUrl,
            request,
            ChatResponse.class);

    if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
        return "No response";
    }

    return response.getChoices().get(0).getMessage().getContent();
}
*/