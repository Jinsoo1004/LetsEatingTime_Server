package com.example.let.service.impl;

import com.example.let.domain.Card;
import com.example.let.domain.Entry;
import com.example.let.domain.res.CardCheckResponse;
import com.example.let.exception.GlobalException;
import com.example.let.mapper.EntryMapper;
import com.example.let.service.CardService;
import com.example.let.service.EntryService;
import com.example.let.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.ibatis.session.SqlSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {
    private final EntryMapper entryMapper;
    private final CardService cardService;
    @Override
    public CardCheckResponse register(Long nfcId, String type) {
        Entry entry = new Entry();
        Card card = cardService.getByNfcId(nfcId);
        entry.setUserId(card.getUserId());
        entry.setCardId(card.getIdx());
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
                System.out.println(entryIdx);
                CardCheckResponse cardCheckResponse = entryMapper.getJoinUser(entryIdx);
                System.out.println(cardCheckResponse);
                return cardCheckResponse;
            default:
                entry.setStatus('B');
                entry.setType(type);
                entryMapper.register(entry);
                throw new GlobalException(HttpStatus.BAD_REQUEST, "unknown card");
        }
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
