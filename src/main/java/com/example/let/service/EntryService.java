package com.example.let.service;

import com.example.let.domain.Entry;

public interface EntryService {
    public String register(Long nfcId);
    public Entry[] get(String id);
    public Entry[] getByDate(String Time);
    public Entry[] get();
}