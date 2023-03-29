package com.example.let.mapper;

import com.example.let.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface UserMapper {
    public void register(User user);
    public User getBySchoolNumber(String schoolNumber);
    public User getByIdx(Long idx);
    public User[] get();
}
