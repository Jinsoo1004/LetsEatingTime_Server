package com.example.let.mapper;

import com.example.let.domain.Access;
import com.example.let.domain.Card;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface AccessMapper {
    public void register(Access access);
    public List<Access> getById(String id);
    public List<Access> getByGrantId(String id);
    public List<Access> getByType(String type);
    public List<Access> getByIdAndType(String id, String type);
    public List<Access> getByIdAndTypeAndDate(String id, String type, String date);
}