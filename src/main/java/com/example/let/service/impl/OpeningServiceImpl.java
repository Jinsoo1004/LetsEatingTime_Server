package com.example.let.service.impl;

import com.example.let.domain.Card;
import com.example.let.domain.Entry;
import com.example.let.domain.Opening;
import com.example.let.domain.res.CardCheckResponse;
import com.example.let.exception.GlobalException;
import com.example.let.mapper.EntryMapper;
import com.example.let.mapper.OpeningMapper;
import com.example.let.service.CardService;
import com.example.let.service.EntryService;
import com.example.let.service.OpeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpeningServiceImpl implements OpeningService {
    private final OpeningMapper openingMapper;

    @Override
    public Opening register(Opening opening) {
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
    public List<Opening> get(String type) {
        return openingMapper.getByType(type);
    }

    @Override
    public List<Opening> getByDate(String date) {
        return openingMapper.getByDate(date);
    }

    @Override
    public List<Opening> getByTypeAndDate(String type, String date) {
        return openingMapper.getByTypeAndDate(type, date);
    }

    @Override
    public List<Opening> get() {
        return openingMapper.get();
    }
}
