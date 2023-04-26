package com.example.let.mapper;

import com.example.let.domain.Entry;
import com.example.let.domain.Opening;
import com.example.let.domain.res.CardCheckResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface OpeningMapper {
    public void register(Opening opening);
    public void registerNow(Opening opening);
    public List<Opening> getByType(String type);
    public List<Opening> getByDate(String date);
    public Opening getByIdx(Long idx);
    public List<Opening> getByTypeAndDate(String type, String date);
    public List<Opening> get();
}