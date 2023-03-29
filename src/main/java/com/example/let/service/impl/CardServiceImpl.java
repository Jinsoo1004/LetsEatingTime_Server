package com.example.let.service.impl;

import com.example.let.domain.Card;
import com.example.let.mapper.CardMapper;
import com.example.let.service.CardService;
import com.example.let.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardMapper cardMapper;
    private final UserService userService;
    @Override
    public Long register(String schoolNumber, Long nfcId) {
        Card card = new Card();
        card.setUserId(userService.get(schoolNumber).getIdx());
        card.setNfcId(nfcId);
        cardMapper.register(card);
        return card.getNfcId();
    }
    @Override
    public Card[] get(String schoolNumber) {
        return cardMapper.getBySchoolNumber(schoolNumber);
    }
    public Card getByNfcId(Long nfcId) {
        return cardMapper.getByNfcId(nfcId);
    }
    @Override
    public Card[] get() {
        return cardMapper.get();
    }
}
