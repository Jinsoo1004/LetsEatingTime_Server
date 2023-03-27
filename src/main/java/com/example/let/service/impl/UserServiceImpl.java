package com.example.let.service.impl;

import com.example.let.JwtTokenProvider;
import com.example.let.domain.TokenInfo;
import com.example.let.domain.User;
import com.example.let.mapper.UserMapper;
import com.example.let.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @Override
    public String register(User user) {
        userMapper.register(user);
        return user.getSchoolNumber();
    }
    @Override
    public TokenInfo login(String schoolNumber, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(schoolNumber, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }
    @Override
    public User get(String schoolNumber) {
        return userMapper.getBySchoolNumber(schoolNumber);
    }
    @Override
    public User[] get() {
        return userMapper.get();
    }
}
