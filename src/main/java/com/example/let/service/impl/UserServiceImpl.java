package com.example.let.service.impl;

import com.example.let.JwtTokenProvider;
import com.example.let.domain.TokenInfo;
import com.example.let.domain.User;
import com.example.let.domain.UserForSecurity;
import com.example.let.exception.GlobalException;
import com.example.let.mapper.UserMapper;
import com.example.let.module.RandomStringGenerator;
import com.example.let.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
    private final RandomStringGenerator randomStringGenerator;
    @Override
    public String register(User user) {
        userMapper.register(user);
        return user.getId();
    }
    @Override
    public TokenInfo login(String id, String password) {
        log.info("login trial : " + id + " " + password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        String refreshCode = randomStringGenerator.generateRandomString(64);
        userMapper.setRefreshToken(id, refreshCode);
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication, refreshCode);
        return tokenInfo;
    }

    @Override
    public TokenInfo refresh(String refreshToken) {
        if(jwtTokenProvider.validateRefreshToken(refreshToken)) {
            String id = jwtTokenProvider.getRefreshSubFromToken(refreshToken);
            String code = jwtTokenProvider.getRefreshCodeFromToken(refreshToken);

            if(userMapper.getRefreshTokenById(id).equals(code)) {
                String refreshCode = randomStringGenerator.generateRandomString(64);
                userMapper.setRefreshToken(id, refreshCode);
                Authentication authentication = new UsernamePasswordAuthenticationToken(id, "", UserForSecurity.builder()
                        .user(userMapper.getById(id))
                        .build().getAuthorities());
                TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication, refreshCode);
                return tokenInfo;
            } else throw new GlobalException(HttpStatus.BAD_REQUEST, "different code");
        } return null;
    }

    @Override
    public User get(String id) {
        return userMapper.getById(id);
    }
    @Override
    public User get(Long idx) {
        return userMapper.getByIdx(idx);
    }
    @Override
    public User[] get() {
        return userMapper.get();
    }
    public void setRefreshToken(String id, String refreshCode) {
        userMapper.setRefreshToken(id, refreshCode);
    }
    public String getRefreshToken(String id) {
        return userMapper.getRefreshTokenById(id);
    }
}