package com.example.let.service;

import com.example.let.domain.Entry;
import com.example.let.domain.res.CardCheckResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EntryService {
    public CardCheckResponse register(Long nfcId, String type);
    public List<Entry> get(String id, String date);
    public List<Entry> get(String id);
    public List<Entry> getByDate(String Time);
    public List<Entry> get();
}