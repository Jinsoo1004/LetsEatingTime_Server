package com.example.let.service.impl;

import com.example.let.domain.Access;
import com.example.let.domain.User;
import com.example.let.exception.GlobalException;
import com.example.let.mapper.AccessMapper;
import com.example.let.mapper.UserMapper;
import com.example.let.service.AccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessServiceImpl implements AccessService {
    private final AccessMapper accessMapper;
    private final UserMapper userMapper;

    @Override
    public Long register(String targetId, String grantId, String type) {
        Long grantIdx;
        try {
            if(!grantId.equals("null")) grantIdx = userMapper.getById(grantId).getIdx();
            else grantIdx = null;
        } catch (NullPointerException e) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "insert \"null\" to grantId");
        }
        Access access = Access.builder()
                .userId(userMapper.getById(targetId).getIdx())
                .grantId(grantIdx)
                .type(type)
                .build();
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
