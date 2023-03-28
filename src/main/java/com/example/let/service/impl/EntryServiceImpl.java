package com.example.let.service.impl;

import com.example.let.domain.Card;
import com.example.let.domain.Entry;
import com.example.let.domain.User;
import com.example.let.exception.CustomException;
import com.example.let.mapper.EntryMapper;
import com.example.let.service.CardService;
import com.example.let.service.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {
    private final EntryMapper entryMapper;
    private final CardService cardService;
    @Override
    public String register(Long nfcId) throws CustomException {
        Entry entry = new Entry();
        Card card = cardService.getByNfcId(nfcId);
        switch (card.getStatus()) {
            case 'W':
                throw new CustomException("This card is awaiting approval.");
            case 'F':
                throw new CustomException("This card is frozen.");
            case 'U':
                entryMapper.register(entry);
                return entry.getEntryTime();
        }
        entryMapper.register(entry);
        return entry.getEntryTime();
    }
    @Override
    public Entry[] get(String schoolNumber) {
        return entryMapper.getBySchoolNumber(schoolNumber);
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
