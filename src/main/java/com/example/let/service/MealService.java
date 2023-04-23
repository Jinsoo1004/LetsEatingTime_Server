package com.example.let.service;

import com.example.let.domain.Meal;
import com.example.let.domain.User;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public interface MealService {
    /**
     * 해당 날짜의 모든 급식 정보를 가져온다.
     *
     * @param String date
     * @return ArrayList<Meal>
     */
    public ArrayList<Meal> get(String date) throws IOException;
}
