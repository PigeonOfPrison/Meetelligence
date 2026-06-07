package com.hammad.meetelligence.Controller;

import com.hammad.meetelligence.Entity.Dto.EvaluationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/evaluation")
public class EvaluationController {

    @GetMapping
    public ResponseEntity<EvaluationResponse> getEvaluation() {
        EvaluationResponse response =
                EvaluationResponse.builder()
                        .candidateName("Mohammad Hammad")
                        .email("your-email")
                        .repositoryUrl("github-url")
                        .deployedUrl("deployment-url")
                        .externalIntegration("Discord Webhook")
                        .features(List.of(
                                "Authentication",
                                "Meeting Management",
                                "AI Analysis",
                                "Action Item Management",
                                "Overdue Detection",
                                "Reminder Scheduler",
                                "Discord Notifications"
                        ))
                        .build();

        return ResponseEntity.ok(response);
    }

}
