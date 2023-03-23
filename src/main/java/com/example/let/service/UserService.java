package com.example.let.service;

import com.example.let.domain.User;

public interface UserService {
    public String register(User user);
    public User[] get(String schoolNumber);
    public User[] get();
}
