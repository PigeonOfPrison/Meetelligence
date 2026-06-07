package com.hammad.meetelligence.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hammad.meetelligence.Service.DiscordService;
import com.hammad.meetelligence.Service.GroqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final GroqService groqService;
    private final DiscordService discordService;

    @Autowired
    public TestController(GroqService groqService, DiscordService discordService) {
        this.groqService = groqService;
        this.discordService = discordService;
    }

    @GetMapping("/ai")
    public String aiMeetingAnalysis() throws JsonProcessingException {
        return groqService.analyzeTranscript("John: We should launch next Friday. Alice: I will prepare release notes.");
    }

    @GetMapping("/test-discord")
    public String testDiscord() {

        discordService.sendReminder("Hello Discord");

        return "sent";
    }
}
