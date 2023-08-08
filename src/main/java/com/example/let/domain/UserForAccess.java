package com.example.let.domain;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserForAccess {
    @NonNull
    private User user;
    private List<Access> access;
}
