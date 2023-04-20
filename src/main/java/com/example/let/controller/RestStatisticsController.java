package com.example.let.controller;

import com.example.let.domain.User;
import com.example.let.mapper.AccessMapper;
import com.example.let.service.AccessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "통계", description = "통계에 대한 정보를 요청한다.")
@RestController
@RequestMapping("/api/statistic")
@AllArgsConstructor
@Log4j2
public class RestStatisticsController {
    private final AccessService accessService;
}