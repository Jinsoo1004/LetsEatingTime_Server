package com.example.let.service.impl;

import com.example.let.domain.Card;
import com.example.let.domain.Entry;
import com.example.let.exception.GlobalException;
import com.example.let.mapper.EntryMapper;
import com.example.let.service.CardService;
import com.example.let.service.EntryService;
import com.example.let.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {
    private final EntryMapper entryMapper;
    private final CardService cardService;
    @Override
    public String register(Long nfcId) {
        Entry entry = new Entry();
        Card card = cardService.getByNfcId(nfcId);
        entry.setUserId(card.getUserId());
        entry.setCardId(card.getIdx());
        switch (card.getStatus()) {
            case 'W':
                entry.setStatus('B');
                entryMapper.register(entry);
                throw new GlobalException(HttpStatus.BAD_REQUEST, "This card is awaiting approval.");
            case 'F':
                entry.setStatus('B');
                entryMapper.register(entry);
                throw new GlobalException(HttpStatus.BAD_REQUEST, "This card is frozen.");
            case 'U':
                entry.setStatus('N');
                entryMapper.register(entry);
                return entry.getEntryTime();
            default:
                entry.setStatus('B');
                entryMapper.register(entry);
                throw new GlobalException(HttpStatus.BAD_REQUEST, "unknown card");
        }
    }
    @Override
    public Entry[] get(String id) {
        return entryMapper.getById(id);
    }
    @Override
    public Entry[] getByDate(String date) {
        return entryMapper.getByDate(date);
    }
    @Override
    public Entry[] get() {
        return entryMapper.get();
    }
}
