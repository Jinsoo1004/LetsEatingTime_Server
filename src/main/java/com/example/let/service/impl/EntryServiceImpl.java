package com.example.let.service.impl;

import com.example.let.domain.Card;
import com.example.let.domain.Entry;
import com.example.let.domain.Opening;
import com.example.let.domain.User;
import com.example.let.domain.res.CardCheckResponse;
import com.example.let.exception.GlobalException;
import com.example.let.mapper.*;
import com.example.let.service.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {
    private final EntryMapper entryMapper;
    private final AccessMapper accessMapper;
    private final CardMapper cardMapper;
    private final OpeningMapper openingMapper;
    private final UserMapper userMapper;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    private final DateTimeFormatter TimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ssXXX");

    @Override
    public CardCheckResponse register(String device, Long nfcId, String type) {
        Entry entry = new Entry();
        Card card = cardMapper.getByNfcId(nfcId);
        User deviceUser = userMapper.getById(device);
        List<Opening> opening = new ArrayList<>();

        User user = userMapper.getByIdx(card.getUserId());

        entry.setUserId(card.getUserId());
        entry.setCardId(card.getIdx());

        opening.addAll(openingMapper.getOriginByDevice(deviceUser.getIdx()));
        opening.addAll(openingMapper.getByDeviceAndDate(deviceUser.getIdx(), LocalDateTime.now().toLocalDate().toString()));

        for(int openingIndex = 0; openingIndex < opening.size(); openingIndex++) {
            Opening targetOpening = opening.get(openingIndex);
            if(LocalTime.parse(targetOpening.getOpenTime(), TimeFormatter).isBefore(LocalTime.now()) &&
               LocalTime.parse(targetOpening.getCloseTime(), TimeFormatter).isAfter(LocalTime.now()) ) {
                if(accessMapper.getByIdAndType(user.getId(), targetOpening.getInfo()) != null) {
                    entry.setInfo(targetOpening.getInfo());
                    switch (card.getStatus()) {
                        case 'W':
                            entry.setStatus('B');
                            entry.setType(type);
                            entryMapper.register(entry);
                            throw new GlobalException(HttpStatus.BAD_REQUEST, "This card is awaiting approval.");
                        case 'F':
                            entry.setStatus('B');
                            entry.setType(type);
                            entryMapper.register(entry);
                            throw new GlobalException(HttpStatus.BAD_REQUEST, "This card is frozen.");
                        case 'U':
                            entry.setStatus('N');
                            entry.setType(type);
                            entryMapper.register(entry);
                            Long entryIdx = entry.getIdx();
                            return entryMapper.getJoinUser(entryIdx);
                        default:
                            entry.setStatus('B');
                            entry.setType(type);
                            entryMapper.register(entry);
                            throw new GlobalException(HttpStatus.BAD_REQUEST, "unknown card");
                    }
                }
                if(entry.getInfo() == null) {
                    entry.setInfo(targetOpening.getInfo());
                } else if(targetOpening.getDate() != null) {
                    entry.setInfo(targetOpening.getInfo());
                }
            }
        }
        throw new GlobalException(HttpStatus.BAD_REQUEST, "no access");

    }
    @Override
    public List<Entry> get(String id, String date) {
        return entryMapper.getByIdAndDate(id, date);
    }

    @Override
    public List<Entry> get(String id) {
        return entryMapper.getById(id);
    }
    @Override
    public List<Entry> getByDate(String date) {
        return entryMapper.getByDate(date);
    }
    @Override
    public List<Entry> get() {
        return entryMapper.get();
    }
}
