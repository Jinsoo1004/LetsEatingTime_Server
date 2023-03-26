package com.example.let.mapper;

import com.example.let.domain.Entry;
import com.example.let.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface EntryMapper {
    public void register(Entry entry);
    public Entry[] getByUserCode(String userCode);
    public Entry[] getByDate(String dates);
    public Entry[] get();
}
