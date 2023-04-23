package com.example.let.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Meal {
    private boolean exists;
    private String MealType;
    private String[] Menu;
    private String Calorie;
    private String Carbohydrates;
    private String Protein;
    private String Fat;
    private String VitaminA;
    private String Thiamin;
    private String Riboflavin;
    private String VitaminC;
    private String Calcium;
    private String Iron;
}
