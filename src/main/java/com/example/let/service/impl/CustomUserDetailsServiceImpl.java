package com.example.let.service.impl;

import com.example.let.domain.User;
import com.example.let.domain.UserForSecurity;
import com.example.let.mapper.UserMapper;
import com.example.let.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getById(username);
        if (user != null) {
            return UserForSecurity.builder()
                    .user(user)
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
