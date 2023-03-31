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
     * 식사 유형 (B:아침, L:점심, D:저녁, N:예외)
     */
    private char mealType;
}
