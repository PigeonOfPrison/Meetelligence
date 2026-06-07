package com.hammad.meetelligence.Entity.Dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowUpSuggestionDto {
    private String text;
    private List<CitationDto> citations;
}
