package com.example.let.service;

import com.example.let.domain.Entry;
import com.example.let.domain.User;
import com.example.let.exception.CustomException;

public interface EntryService {
    public String register(Long nfcId) throws CustomException;
    public Entry[] get(String SchoolNumber);
    public Entry[] getByDate(String Time);
    public Entry[] get();
}