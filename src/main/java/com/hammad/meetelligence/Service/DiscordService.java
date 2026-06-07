package com.hammad.meetelligence.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class DiscordService {

    @Value("${discord.server.webhook.url}")
    private String webhookUrl;

    private final RestClient restClient;

    @Autowired
    public DiscordService(RestClient restClient) {
        this.restClient = restClient;
    }

    public void sendReminder(String message) {
        Map<String, String> payload = Map.of("content", message);

        restClient.post()
                .uri(webhookUrl)
                .body(payload)
                .retrieve()
                .toBodilessEntity();

    }
}
