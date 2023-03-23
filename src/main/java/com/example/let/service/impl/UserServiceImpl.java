package com.example.let.service.impl;

import com.example.let.domain.User;
import com.example.let.mapper.UserMapper;
import com.example.let.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    @Override
    public String register(User user) {
        userMapper.register(user);
        return user.getSchoolNumber();
    }
    @Override
    public User[] get(String schoolNumber) {
        return userMapper.getBySchoolNumber(schoolNumber);
    }
    @Override
    public User[] get() {
        return userMapper.get();
    }
}
