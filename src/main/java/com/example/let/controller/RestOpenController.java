package com.example.let.controller;

import com.example.let.JwtTokenProvider;
import com.example.let.domain.Meal;
import com.example.let.domain.User;
import com.example.let.domain.res.ObjectResponseDto;
import com.example.let.exception.GlobalException;
import com.example.let.service.MealService;
import com.example.let.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@Tag(name = "오픈 API", description = "누구든 사용 가능한 OpenAPI")
@RestController
@RequestMapping("/openapi")
@AllArgsConstructor
@Log4j2
public class RestOpenController {
    private final MealService mealService;
    /**
     * @Name 급식 API
     * @Path "openapi/meal"
     *
     * @text
     * 급식 정보를 가공하여 전달한다.
     *
     * @Return User
     */
    @Operation(summary = "급식 가져오기", description = "해당일 급식을 모두 반환합니다")
    @PostMapping("/meal")
    public ObjectResponseDto GetMeal(@RequestParam(name = "date") String date
    ) throws IOException {
        return ObjectResponseDto.builder()
                .status("200")
                .data(mealService.get(date))
                .build();
    }
}
