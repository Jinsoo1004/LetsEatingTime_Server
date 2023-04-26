package com.example.let.service.impl;

import com.example.let.JwtTokenProvider;
import com.example.let.domain.*;
import com.example.let.exception.GlobalException;
import com.example.let.mapper.EntryMapper;
import com.example.let.mapper.UserMapper;
import com.example.let.module.RandomStringGenerator;
import com.example.let.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final EntryMapper entryMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RandomStringGenerator randomStringGenerator;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    //private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ssXXX");

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
        String refreshCode = RandomStringGenerator.generateRandomString(64);
        userMapper.setRefreshToken(id, refreshCode);
        return jwtTokenProvider.generateToken(authentication, refreshCode);
    }

    @Override
    public TokenInfo refresh(String refreshToken) {
        if(jwtTokenProvider.validateRefreshToken(refreshToken)) {
            String id = jwtTokenProvider.getRefreshSubFromToken(refreshToken);
            String code = jwtTokenProvider.getRefreshCodeFromToken(refreshToken);

            if(userMapper.getRefreshTokenById(id).equals(code)) {
                String refreshCode = RandomStringGenerator.generateRandomString(64);
                userMapper.setRefreshToken(id, refreshCode);
                Authentication authentication = new UsernamePasswordAuthenticationToken(id, "", UserForSecurity.builder()
                        .user(userMapper.getById(id))
                        .build().getAuthorities());
                return jwtTokenProvider.generateToken(authentication, refreshCode);
            } else throw new GlobalException(HttpStatus.BAD_REQUEST, "different code");
        } return null;
    }

    @Override
    public UserForMeal get(String id) {
        User user = userMapper.getById(id);
        List<Entry> entry = entryMapper.getByIdAndDate(user.getId(), LocalDateTime.now().toLocalDate().toString());
        for(int entryIndex = 0; entryIndex < entry.size(); entryIndex++) {
            if(LocalDateTime.parse(entry.get(entryIndex).getEntryTime(), dateTimeFormatter).isBefore(LocalDateTime.now())) {
                System.out.println(entryIndex);
            }
        }
        return UserForMeal.builder()
                .user(user)
                //.breakfast(entry.)
                .build();
    }
    @Override
    public UserForMeal get(Long idx) {
        return new UserForMeal();//userMapper.getByIdx(idx);
    }
    @Override
    public List<UserForMeal> get() {
        List<User> user = userMapper.get();
        List<Entry> entry;
        List<UserForMeal> userForMeal = new ArrayList<>();
        for(int userIndex = 0; userIndex < user.size(); userIndex++) {
            entry = entryMapper.getByIdAndDate(user.get(userIndex).getId(), LocalDateTime.now().toLocalDate().toString());
            List<String> info = entry.stream().map(Entry::getInfo).toList();

            userForMeal.add(UserForMeal.builder()
                    .user(user.get(userIndex))
                    .mealTime(info)

                    .build()
            );
        }
        return userForMeal;
    }
    public void setRefreshToken(String id, String refreshCode) {
        userMapper.setRefreshToken(id, refreshCode);
    }
    public String getRefreshToken(String id) {
        return userMapper.getRefreshTokenById(id);
    }
}