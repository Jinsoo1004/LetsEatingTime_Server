package com.example.let.controller;

import com.example.let.domain.User;
import com.example.let.domain.req.PasswordChangeRequest;
import com.example.let.domain.res.ResponseDto;
import com.example.let.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Tag(name = "계정", description = "회원가입, 로그인등 계정을 다루는 API")
@RestController
@RequestMapping("/v2/account")
@AllArgsConstructor
public class RestAccountController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * @Name 회원가입
     * @Path "api/account/signup.do"
     * @Request RequestBody(Json) : User[id, password, name, meal_application, user_type]
     *
     * @text
     * DB에 새로운 유저를 생성한다.
     *
     * @Return String(학번)
     */
    @Operation(summary = "회원가입", description = "회원가입을 요청 합니다")
    @PostMapping("/signup.do")
    public ResponseEntity<?> Signup(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(200)
                        .data(userService.register(user))
                        .build()
                , HttpStatus.OK
        );
    }
    /**
     * @Name 로그인
     * @Path "api/account/login.do"
     * @Request RequestBody(Json) : User[id, password]
     *
     * @text
     * 로그인하여 권한을 전달한다.
     *
     * @Return TokenInfo[grantType, accessToken, refreshToken]
     */
    @Operation(summary = "로그인", description = "로그인을 요청 합니다. id + pw 혹은 pw(refresh token)가 필요합니다")
    @PostMapping("/login.do")
    public ResponseEntity<?> Login(@RequestBody User user) {
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(200)
                        .data(userService.login(user.getId(), user.getPassword()))
                        .build()
                , HttpStatus.OK
        );
    }
    /**
     * @Name 재발급
     * @Path "api/account/refresh.do"
     * @Request
     *
     * @text
     * 로그인하여 권한을 전달한다.
     *
     * @Return TokenInfo[grantType, accessToken, refreshToken]
     */
    @Operation(summary = "재발급", description = "refresh token을 이용하여 access와 refresh 토큰을 재발급 받습니다")
    @PostMapping("/refresh.do")
    public ResponseEntity<?> Refresh(@RequestHeader("Authorization") String token) {

        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(200)
                        .data(userService.refresh(token.substring(7)))
                        .build()
                , HttpStatus.OK
        );
    }
    @Operation(summary = "비밀번호 변경", description = "비밀번호를 변경합니다.")
    @PostMapping(value="/pw-change")
    public ResponseEntity<?> changePassword(
            @RequestBody PasswordChangeRequest request
    )
    {
        userService.passwordChange(request);
        return new ResponseEntity<> (
                ResponseDto.builder()
                        .status(200)
                        .data("change success")
                        .build()
                , HttpStatus.OK
        );
    }
}
