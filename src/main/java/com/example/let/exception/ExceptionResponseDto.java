package com.example.let.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ExceptionResponseDto {
    @JsonProperty(value = "timestamp", index = 1)
    private final String timeStamp = LocalDateTime.now().toString();
    private final int status;
    private final HttpStatus error;
    private final String message;
}
