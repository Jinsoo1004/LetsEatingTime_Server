package com.example.let.service;

import com.example.let.domain.Access;

import java.util.List;

public interface AccessService {
    public Long register(String targetId, String grantId, String type);
    public List<Access> getById(String id);
    public List<Access> getByType(String type);
}