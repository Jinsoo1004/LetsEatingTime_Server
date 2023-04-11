package com.example.let.service;

import com.example.let.domain.Entry;
import com.example.let.domain.res.CardCheckResponse;
import org.springframework.http.ResponseEntity;

public interface EntryService {
    public CardCheckResponse register(Long nfcId);
    public Entry[] get(String id);
    public Entry[] getByDate(String Time);
    public Entry[] get();
}