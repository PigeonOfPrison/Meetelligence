package com.hammad.meetelligence.Entity.Dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationResponse {

    private String candidateName;
    private String email;
    private String repositoryUrl;
    private String deployedUrl;
    private String externalIntegration;
    private List<String> features;


}
