package com.hammad.meetelligence.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class GroqService {

    private final ObjectMapper objectMapper;
    @Value("${groq.api.key}")
    private String apiKey;
    private final String url = "https://api.groq.com/openai/v1/chat/completions";

    private final RestClient restClient;

    @Autowired
    public GroqService(RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    public String analyzeTranscript(String transcript) {
        System.out.println("Entering Groq analyzeTranscript");

        String prompt = """
        You are a meeting analysis assistant.
        
        Analyze the following meeting transcript.
        
        Rules:
        1. Use ONLY information present in the transcript.
        2. Do NOT invent attendees.
        3. Do NOT invent decisions.
        4. Do NOT invent action items.
        5. Every insight must contain citations.
        6. Return ONLY valid JSON.
        7. Do not include markdown.
        8. Do not include explainations.
        
        Required format:
        
        {
          "summary": [],
          "actionItems": [],
          "decisions": [],
          "followUpSuggestions": []
        }
        
        Transcript:
        
        %s
        """.formatted(transcript);

        Map<String, Object> request = new HashMap<>();

        request.put("model", "llama-3.3-70b-versatile");

        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        request.put("messages", List.of(message));

        String requestBody = objectMapper.writeValueAsString(request);

        String res = restClient.post()
                .uri(url)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .retrieve()
                .body(String.class);

        JsonNode root = null;
        String content = "error";

        try {
            root = objectMapper.readTree(res);
            System.out.println(root.toString());
            content = root.path("choices").get(0).path("message").path("content").asString()  ;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(content);
        return content;
    }
}
