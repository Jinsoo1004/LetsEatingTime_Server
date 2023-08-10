package com.example.let.mapper;

import com.example.let.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface UserMapper {
    public void register(User user);
    public void approve(String id);
    public User getById(String id);
    public User getByIdx(Long idx);
    public List<User> get();
    public List<User> getStudent();
    public void setRefreshToken(String id, String refreshCode);
    public void setImage(String id, Long image);
    public String getRefreshTokenById(String id);
    public List<Long> getChartByMealApplication();
    public List<Long> getChartByMealAttendBreakfast();
    public List<Long> getChartByMealAttendLunch();
    public List<Long> getChartByMealAttendDinner();
    public List<Long> getChartByMealAttend();
    public void delete(String id);
    public void withdraw(String id);
    public void passwordUpdate(String id, String password);
}
