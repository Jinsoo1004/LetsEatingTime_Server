package com.example.let.controller;

import com.example.let.domain.Card;
import com.example.let.domain.TokenInfo;
import com.example.let.domain.User;
import com.example.let.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.examples.Example;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "계정", description = "회원가입, 로그인등 계정을 다루는 API")
@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
@Log4j2
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
    public String Signup(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserType('S');
        return userService.register(user);
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
    @PostMapping("/login.do")
    public TokenInfo Login(@RequestBody User user) {
        TokenInfo tokenInfo = userService.login(user.getId(), user.getPassword());
        return tokenInfo;
    }
}
