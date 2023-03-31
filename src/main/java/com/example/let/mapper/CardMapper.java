package com.example.let.mapper;

import com.example.let.domain.Card;
import com.example.let.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface CardMapper {
    public void register(Card card);
    public Card[] getById(String id);
    public Card getByNfcId(Long nfcId);
    public Card[] get();
}