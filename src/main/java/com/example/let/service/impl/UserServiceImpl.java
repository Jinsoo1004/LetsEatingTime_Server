package com.example.let.service.impl;

import com.example.let.JwtTokenProvider;
import com.example.let.domain.*;
import com.example.let.domain.req.PasswordChangeRequest;
import com.example.let.exception.GlobalException;
import com.example.let.mapper.*;
import com.example.let.module.RandomStringGenerator;
import com.example.let.service.UserService;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final CardMapper cardMapper;
    private final EntryMapper entryMapper;
    private final FileMapper fileMapper;
    private final AccessMapper accessMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RandomStringGenerator randomStringGenerator;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    //private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ssXXX");

    @Override
    public String register(User user) {
        if(user.getId().equals("null")) throw new GlobalException(HttpStatus.BAD_REQUEST, "id can't \"null\"");
        user.setImage(0L);
        userMapper.register(user);
        return user.getId();
    }
    @Override
    public void approve(String id) {
        userMapper.approve(id);
    }
    @Override
    public TokenInfo login(String id, String password) {
        if(userMapper.getById(id) == null) {
            throw new GlobalException(HttpStatus.FORBIDDEN, "Account without");
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            String refreshCode = RandomStringGenerator.generateRandomString(64);
            userMapper.setRefreshToken(id, refreshCode);
            return jwtTokenProvider.generateToken(authentication, refreshCode);
        } catch (Exception e) {
            throw new GlobalException(HttpStatus.FORBIDDEN, "Credentials failed.");
        }
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

        List<String> info = entry.stream().map(Entry::getInfo).toList();
        return UserForMeal.builder()
                .user(user)
                .mealType(info)
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
                    .mealType(info)
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
    @Override
    public List<Long> getChartByMealApplication() {
        return userMapper.getChartByMealApplication();
    }
    @Override
    public List<Long> getChartByMealAttend(String type) {
        if(type.equals("breakfast")) {
            return userMapper.getChartByMealAttendBreakfast();
        } else if(type.equals("lunch")) {
            return userMapper.getChartByMealAttendLunch();
        } else if(type.equals("dinner")) {
            return userMapper.getChartByMealAttendDinner();
        } else {
            return userMapper.getChartByMealAttend();
        }
    }
    @Override
    public void delete(String id) {
        User user = userMapper.getById(id);
        entryMapper.deleteByUserId(user.getIdx());
        accessMapper.delete(id);
        cardMapper.deleteByUserId(user.getIdx());
        if (user.getImage() != 0) {
            fileMapper.deleteFile(user.getImage());
        }
        userMapper.delete(id);
    }

    @Override
    public void withdraw(String id) {
        userMapper.withdraw(id);
    }

    @Override
    public void passwordChange(PasswordChangeRequest request) {
        User user = userMapper.getById(request.getId());
        if(user == null) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "not user");
        }
        if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            userMapper.passwordUpdate(request.getId(), passwordEncoder.encode(request.getNewPassword()));
        } else throw new GlobalException(HttpStatus.BAD_REQUEST, "password not match");
    }

    @Override
    public void reset(User user) {
        userMapper.reset(user);
    }
}