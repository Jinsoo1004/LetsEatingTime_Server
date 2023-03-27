package com.example.let.controller;

import com.example.let.domain.TokenInfo;
import com.example.let.domain.User;
import com.example.let.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Log4j2
public class RestUserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * @Name 회원가입
     * @Path "api/user/signup.do"
     * @Request RequestBody(Json) : User[school_number, password, name, meal_application, user_type]
     *
     * @text
     * DB에 새로운 유저를 생성한다.
     *
     * @Return String(학번)
     */
    @PostMapping("/signup.do")
    public String Signup(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserType('S');
        return userService.register(user);
    }
    /**
     * @Name 로그인
     * @Path "api/user/login.do"
     * @Request RequestBody(Json) : User[school_number, password]
     *
     * @text
     * 로그인하여 권한을 전달한다.
     *
     * @Return String(학번)
     */
    @PostMapping("/login.do")
    public TokenInfo Login(@RequestBody User user) {
        TokenInfo tokenInfo = userService.login(user.getSchoolNumber(), user.getPassword());
        return tokenInfo;
    }
}
