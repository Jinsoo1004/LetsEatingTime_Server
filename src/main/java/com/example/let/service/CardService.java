package com.example.let.service;

import com.example.let.domain.Card;

public interface CardService {
    public Long register(String id, Long nfcId);
    public Card[] get(String userCode);
    public Card getByNfcId(Long nfcId);
    public Card[] get();
}