package com.example.let.service.impl;

import com.example.let.domain.Card;
import com.example.let.mapper.CardMapper;
import com.example.let.service.CardService;
import com.example.let.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardMapper cardMapper;
    private final UserService userService;
    @Override
    public Long register(String id, Long nfcId) {
        Card card = new Card();
        card.setUserId(userService.get(id).getUser().getIdx());
        card.setNfcId(nfcId);
        cardMapper.register(card);
        return card.getNfcId();
    }
    @Override
    public List<Card> get(String id) {
        return cardMapper.getById(id);
    }
    public Card getByNfcId(Long nfcId) {
        return cardMapper.getByNfcId(nfcId);
    }
    @Override
    public List<Card> get() {
        return cardMapper.get();
    }
}
