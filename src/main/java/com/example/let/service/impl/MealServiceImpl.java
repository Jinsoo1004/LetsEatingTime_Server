package com.example.let.service.impl;

import com.example.let.domain.Meal;
import com.example.let.service.MealService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@Service
public class MealServiceImpl implements MealService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private final String url;

    public MealServiceImpl(@Value("${openapi.nice.meal}") String url) {
        this.url = url;
    }

    public ArrayList<Meal> get(String date) throws IOException {
        StringBuilder result = new StringBuilder();

        HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(url + "&MLSV_YMD=" + date)).openConnection();
        httpURLConnection.setRequestMethod("GET");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
        String returnString;
        while ((returnString = bufferedReader.readLine()) != null) {
            result.append(returnString + "\n\r");
        }
        httpURLConnection.disconnect();
        JsonNode rootNode = objectMapper.readTree(result.toString());
        rootNode = rootNode.path("mealServiceDietInfo").get(1).path("row");
        
        ArrayList<Meal> mealData = new ArrayList<Meal>();
        for(int JsonIndex = 0; JsonIndex < rootNode.size(); JsonIndex++) {
            String[] menu = rootNode.path(0).get("DDISH_NM").asText().split("<br/>");
            String[] nutrient = rootNode.path(0).get("NTR_INFO").asText().split("<br/>");
            for(int nutrientIndex = 0; nutrientIndex < 9; nutrientIndex++) {
                nutrient[nutrientIndex] = nutrient[nutrientIndex].substring(nutrient[nutrientIndex].indexOf(" : ") + 3);
            }
            String cal = rootNode.path(0).get("CAL_INFO").asText();
            Meal meal = new Meal(
                    true,
                    rootNode.path(0).get("MMEAL_SC_NM").asText(),
                    menu,
                    cal.substring(0, cal.indexOf(" Kcal")),
                    nutrient[0],
                    nutrient[1],
                    nutrient[2],
                    nutrient[3],
                    nutrient[4],
                    nutrient[5],
                    nutrient[6],
                    nutrient[7],
                    nutrient[8]
            );
            mealData.add(meal);
        }

        return mealData;
    }
}
