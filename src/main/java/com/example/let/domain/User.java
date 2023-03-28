package com.example.let.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 사용자
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * idx
    */
    private Long idx;
    /**
     * 증명사진
     */
    private Long image;
    /**
     * 학번
     */
    private String schoolNumber;
    /**
     * 비밀번호
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    /**
     * 이름
     */
    private String name;
    /**
     * 생성 시간
     */
    private String createTime;
    /**
     * 급식 신청
     */
    private char mealApplication;
    /**
     * 유저 타입 (S:학생, T:교사, M:관리자, A:어드민)
     */
    private char userType;
}
