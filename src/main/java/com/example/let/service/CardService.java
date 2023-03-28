package com.example.let.service;

import com.example.let.domain.Card;

public interface CardService {
    public Long register(Card card);
    public Card[] get(String userCode);
    public Card getByNfcId(Long nfcId);
    public Card[] get();
}