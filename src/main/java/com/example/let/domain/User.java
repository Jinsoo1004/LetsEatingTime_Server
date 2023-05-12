package com.example.let.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 사용자
 */
@Data
@Builder
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
     * 아이디
     */
    private String id;
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
     * 유저 타입 (S:학생, T:교사, A:관리자, A:어드민, D:디바이스)
     */
    private char userType;
    /**
     * 학년
     */
    private short grade;
    /**
     * 반
     */
    private short className;
    /**
     * 번호
     */
    private short classNo;
    /**
     * 인증 여부
     */
    private char approvedYn;
    /**
     * 탈퇴 여부
     */
    private char withdrawedYn;
    /**
     * 탈퇴 시간
     */
    private String withdrawedTime;
    /**
     * refresh 토큰
     */
    @JsonIgnore
    private String refreshToken;
}
