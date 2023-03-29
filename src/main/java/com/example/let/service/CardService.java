package com.example.let.service;

import com.example.let.domain.Card;

public interface CardService {
    public Long register(String schoolNumber, Long nfcId);
    public Card[] get(String userCode);
    public Card getByNfcId(Long nfcId);
    public Card[] get();
}