package com.example.let.mapper;

import com.example.let.domain.Entry;
import com.example.let.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface EntryMapper {
    public void register(Entry entry);
    public Entry[] getBySchoolNumber(String schoolNumber);
    public Entry[] getByDate(String date);
    public Entry[] get();
}