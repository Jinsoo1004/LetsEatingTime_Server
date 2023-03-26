package com.example.let.service;

import com.example.let.domain.Entry;
import com.example.let.domain.User;

public interface EntryService {
    public String register(Entry entry);
    public Entry[] get(String userCode);
    public Entry[] getByDate(String Time);
    public Entry[] get();
}