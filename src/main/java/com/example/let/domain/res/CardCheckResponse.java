package com.example.let.domain.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 카드체크(응답 도메인)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "카드체크(응답 도메인)")
public class CardCheckResponse {
    /**
     * idx
     */
    private Long idx;
    /**
     * 유저 idx
     */
    private Long userId;
    /**
     * 접근 시간
     */
    private String entryTime;
    /**
     * 카드 상태 (W:대기, U:사용, F:동결)
     */
    private char status;
    /**
     * 접근 제어자 정보
     */
    private String type;
    /**
     * 프로필 사진
     */
    private Long image;
    /**
     * 프로필 사진
     */
    private String name;
    /**
     * 유저 타입 (S:학생, T:교사, M:관리자, A:어드민, D:디바이스)
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
}