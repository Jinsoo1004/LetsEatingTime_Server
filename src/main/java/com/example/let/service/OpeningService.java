package com.example.let.service;

import com.example.let.domain.Opening;
import java.util.List;

public interface OpeningService {
    public Opening register(Opening opening, String id);
    public List<Opening> get(String device);
    public List<Opening> getByDate(String date);
    public List<Opening> getByDeviceAndDate(String device, String date);
    public List<Opening> get();
}