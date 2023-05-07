package com.example.let.controller;

import com.example.let.domain.Opening;
import com.example.let.domain.User;
import com.example.let.domain.res.ResponseDto;
import com.example.let.mapper.AccessMapper;
import com.example.let.service.AccessService;
import com.example.let.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "통계", description = "통계에 대한 정보를 요청한다.")
@RestController
@RequestMapping("/api/statistic")
@AllArgsConstructor
public class RestStatisticsController {
    private final UserService userService;

    /**
     * @Name 급식 신청 통계 요청
     * @Path "api/statistic/meal-application"
     * @Request
     *
     * @text
     * 급식 신청 현황을 확인합니다.
     *
     * @Return
     */
    @Operation(summary = "급식 신청 통계 요청",
               description = "급식 신청 현황을 확인합니다.")
    @GetMapping("/meal-application")
    public ResponseEntity<?> resMealApp() {
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(200)
                        .data(userService.getChartByMealApplication())
                        .build()
                , HttpStatus.OK
        );
    }

    /**
     * @Name 급식 참석 통계 요청
     * @Path "api/statistic/meal-attend"
     * @Request
     *
     * @text
     * 급식 참석 현황을 확인합니다.
     *
     * @Return
     */
    @Operation(summary = "급식 참석 통계 요청",
            description = "급식 참석 현황을 확인합니다.")
    @GetMapping("/meal-attend")
    public ResponseEntity<?> resMealAttend(@RequestParam String type) {
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(200)
                        .data(userService.getChartByMealAttend(type))
                        .build()
                , HttpStatus.OK
        );
    }
}