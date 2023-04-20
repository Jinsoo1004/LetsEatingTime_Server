package com.example.let.service;

import com.example.let.domain.Card;

import java.util.List;

public interface CardService {
    public Long register(String id, Long nfcId);
    public List<Card> get(String userCode);
    public Card getByNfcId(Long nfcId);
    public List<Card> get();
}