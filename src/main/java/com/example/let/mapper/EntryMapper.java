package com.example.let.mapper;

import com.example.let.domain.Entry;
import com.example.let.domain.User;
import com.example.let.domain.res.CardCheckResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface EntryMapper {
    public void register(Entry entry);
    public CardCheckResponse getJoinUser(Long idx);
    public Entry[] getById(String id);
    public Entry[] getByDate(String date);
    public Entry[] get();
}