package com.example.let.domain;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserForMeal {
    @NonNull
    private User user;
    private List<String> mealTime;
}
