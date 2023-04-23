package com.example.let.domain.res;

import com.example.let.domain.Meal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealResponse {
    private boolean exists;
    private Meal breakfast;
    private Meal lunch;
    private Meal dinner;
}