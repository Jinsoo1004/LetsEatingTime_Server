package com.example.let.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "일반", description = "기타 api")
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class RestMainController {
    @GetMapping("/")
    public String healthCheck() {
        return "Server running";
    }
}
