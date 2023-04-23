package com.example.let.mapper;

import com.example.let.service.Entry;
import com.example.let.domain.res.CardCheckResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface EntryMapper {
    public void register(Entry entry);
    public CardCheckResponse getJoinUser(Long idx);
    public List<Entry> getById(String id);
    public List<Entry> getByDate(String date);
    public List<Entry> getByIdAndDate(String id, String date);
    public List<Entry> get();
}