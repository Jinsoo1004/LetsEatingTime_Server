package com.example.let.service.impl;

import com.example.let.domain.Entry;
import com.example.let.domain.User;
import com.example.let.mapper.EntryMapper;
import com.example.let.service.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {
    private final EntryMapper entryMapper;
    @Override
    public String register(Entry entry) {
        entryMapper.register(entry);
        return entry.getEntryTime();
    }
    @Override
    public Entry[] get(String userCode) {
        return entryMapper.getByUserCode(userCode);
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
