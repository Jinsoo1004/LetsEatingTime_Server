package com.example.let.service;

import com.example.let.domain.Access;
import com.example.let.domain.Card;
import com.example.let.domain.res.CardCheckResponse;

import java.util.List;

public interface AccessService {
    public Long register(Access access);
    public List<Access> getById(String id);
    public List<Access> getByType(String type);
}