package com.example.let.controller;

import com.example.let.JwtTokenProvider;
import com.example.let.domain.Card;
import com.example.let.domain.Meal;
import com.example.let.domain.TokenInfo;
import com.example.let.domain.User;
import com.example.let.domain.res.ResponseDto;
import com.example.let.exception.GlobalException;
import com.example.let.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Tag(name = "유저", description = "프로필 수정등 유저 권한 이상이 필요한 API")
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Log4j2
public class RestUserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * @Name 프로필 열람
     * @Path "api/user/profile"
     *
     * @text
     * DB에 존재하는 유저 프로필 정보를 변환한다.
     *
     * @Return User
     */
    @Operation(summary = "프로필 가져오기", description = "프로필을 반환합니다")
    @PostMapping("/profile")
    public ResponseEntity<?> Signup(@RequestHeader("Authorization") String token) {
        String id = jwtTokenProvider.getAccessSubFromToken(token.substring(7));
        log.info("> " + id);
        if(!id.isEmpty()) {
            return new ResponseEntity<>(
                    ResponseDto.builder()
                            .status(200)
                            .data(userService.get(id))
                            .build()
                    , HttpStatus.OK
            );
        } else throw new GlobalException(HttpStatus.BAD_REQUEST, "can not find user");
    }
}
