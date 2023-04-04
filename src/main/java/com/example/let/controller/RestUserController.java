package com.example.let.controller;

import com.example.let.domain.TokenInfo;
import com.example.let.domain.User;
import com.example.let.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저", description = "프로필 수정등 유저 권한 이상이 필요한 API")
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
     * @Request RequestBody(Json) : User[id, password, name, user_type]
     *
     * @text
     * DB에 존재하는 유저 프로필 정보를 변환한다.
     *
     * @Return String(학번)
     */
    @Operation(summary = "프로필 수정", description = "프로필을 변경합니다")
    @PostMapping("/signup.do")
    public String Signup(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserType('S');
        return userService.register(user);
    }
}
