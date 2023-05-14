package com.example.let.mapper;

import com.example.let.domain.Card;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface CardMapper {
    public void register(Card card);
    public List<Card> getById(String id);
    public Card getByNfcId(Long nfcId);
    public List<Card> get();
}