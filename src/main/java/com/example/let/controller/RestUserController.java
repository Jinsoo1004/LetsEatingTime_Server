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
     * @Name 프로필 수정
     * @Path "api/user/profile"
     * @Request RequestBody(Json) : User[schoolNumber, password, name, mealApplication, user_type]
     *
     * @text
     * DB에 존재하는 유저 프로필 정보를 변환한다.
     *
     * @Return String(학번)
     */
    @PostMapping("/signup.do")
    public String Signup(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserType('S');
        return userService.register(user);
    }
}
