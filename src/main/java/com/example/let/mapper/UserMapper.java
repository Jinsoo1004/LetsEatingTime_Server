package com.example.let.mapper;

import com.example.let.domain.User;
import com.example.let.domain.res.ChartResponse;
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
    public void setRefreshToken(String id, String refreshCode);
    public void setImage(String id, Long image);
    public String getRefreshTokenById(String id);
    public List<ChartResponse> getChartByMealApplication();
    public List<ChartResponse> getChartByMealAttendBreakfast();
    public List<ChartResponse> getChartByMealAttendLunch();
    public List<ChartResponse> getChartByMealAttendDinner();
    public List<ChartResponse> getChartByMealAttend();
}
