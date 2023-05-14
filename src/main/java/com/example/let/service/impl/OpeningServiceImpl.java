package com.example.let.service.impl;

import com.example.let.domain.Opening;
import com.example.let.domain.User;
import com.example.let.exception.GlobalException;
import com.example.let.mapper.OpeningMapper;
import com.example.let.mapper.UserMapper;
import com.example.let.service.OpeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpeningServiceImpl implements OpeningService {
    private final OpeningMapper openingMapper;
    private final UserMapper userMapper;

    @Override
    public Opening register(Opening opening, String id) {
        try {
            User user = userMapper.getById(id);
            if(user.getUserType() == 'D') {
                opening.setDevice(user.getIdx());
            }
            else throw new GlobalException(HttpStatus.BAD_REQUEST, "not device");
        } catch (NullPointerException e) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "unknown user");
        }
        if(opening.getCloseTime() == null) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "closeTime can't null");
        } else if(opening.getOpenTime() == null) {
            openingMapper.registerNow(opening);
        } else {
            openingMapper.register(opening);
        }
        opening = openingMapper.getByIdx(opening.getIdx());

        return opening;
    }

    @Override
    public List<Opening> get(String device) {
        Long lDevice = userMapper.getById(device).getIdx();
        return openingMapper.getByDevice(lDevice);
    }

    @Override
    public List<Opening> getByDate(String date) {
        return openingMapper.getByDate(date);
    }

    @Override
    public List<Opening> getByDeviceAndDate(String device, String date) {
        Long lDevice = userMapper.getById(device).getIdx();
        return openingMapper.getByDeviceAndDate(lDevice, date);
    }

    @Override
    public List<Opening> get() {
        return openingMapper.get();
    }
}
