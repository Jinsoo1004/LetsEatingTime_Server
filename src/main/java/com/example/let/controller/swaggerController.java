package com.example.let.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/swagger-ui")
class SwaggerController {
    @GetMapping
    public String api() { return "redirect:/swagger-ui/index.html"; }
}