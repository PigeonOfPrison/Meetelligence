package com.hammad.meetelligence.Entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private Boolean success;
    private String message;
    private LocalDateTime timestamp;
}
