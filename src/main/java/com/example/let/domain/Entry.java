package com.example.let.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 출입 기록
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entry {
    /**
     * idx
     */
    private Long idx;
    /**
     * 유저 idx
     */
    private Long userId;
    /**
     * 카드
     */
    private Long cardId;
    /**
     * 마지막 작용 시간
     */
    private String entryTime;
    /**
     * 결과 (N:정상, B:출입 불허)
     */
    private char status;
    /**
     * 접근 제어자 정보
     */
    private String type;
}
