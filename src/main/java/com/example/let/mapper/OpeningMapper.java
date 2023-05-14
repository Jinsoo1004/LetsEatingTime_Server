package com.example.let.mapper;

import com.example.let.domain.Opening;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface OpeningMapper {
    public void register(Opening opening);
    public void registerNow(Opening opening);
    public List<Opening> getByDevice(Long device);
    public List<Opening> getOriginByDevice(Long device);
    public List<Opening> getByDate(String date);
    public Opening getByIdx(Long idx);
    public List<Opening> getByDeviceAndDate(Long device, String date);
    public List<Opening> get();
}