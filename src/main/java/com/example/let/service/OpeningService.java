package com.example.let.service;

import com.example.let.domain.Entry;
import com.example.let.domain.Opening;
import com.example.let.domain.res.CardCheckResponse;

import java.util.List;

public interface OpeningService {
    public Opening register(Opening opening);
    public List<Opening> get(String type);
    public List<Opening> getByDate(String date);
    public List<Opening> getByTypeAndDate(String type, String date);
    public List<Opening> get();
}