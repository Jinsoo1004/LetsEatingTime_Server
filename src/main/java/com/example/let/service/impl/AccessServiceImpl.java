package com.example.let.service.impl;

import com.example.let.domain.Access;
import com.example.let.mapper.AccessMapper;
import com.example.let.service.AccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessServiceImpl implements AccessService {
    private final AccessMapper accessMapper;

    @Override
    public Long register(Access access) {
        accessMapper.register(access);
        return access.getIdx();
    }

    @Override
    public List<Access> getById(String id) {
        return accessMapper.getById(id);
    }

    @Override
    public List<Access> getByType(String type) {
        return accessMapper.getByType(type);
    }
}
