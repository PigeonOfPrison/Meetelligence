package com.hammad.meetelligence.Entity.Dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneratedActionItemDto {

    private String task;
    private String assignee;
    private List<CitationDto> citations;

}
