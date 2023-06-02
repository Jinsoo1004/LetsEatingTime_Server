package com.example.let.service;

import com.example.let.domain.res.MealResponse;
import java.io.IOException;

public interface MealService {
    /**
     * 해당 날짜의 모든 급식 정보를 가져온다.
     *
     * @param String date
     * @return MealResponse
     */
    public MealResponse get(String date) throws IOException;
}