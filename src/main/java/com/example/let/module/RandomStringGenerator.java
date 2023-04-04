package com.example.let.module;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomStringGenerator {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateRandomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        Random random = new Random();

        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}