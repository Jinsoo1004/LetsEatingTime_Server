package com.example.let.domain.req;

import lombok.Data;

@Data
public class PasswordChangeRequest {
    private String id;
    private String password;
    private String newPassword;
}
