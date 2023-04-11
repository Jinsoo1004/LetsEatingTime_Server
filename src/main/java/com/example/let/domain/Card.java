package com.example.let.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 카드
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    public Card(Long userId, Long nfcId) {
        this.userId = userId;
        this.nfcId = nfcId;
    }
    /**
     * idx
     */
    private Long idx;
    /**
     * 유저 idx
     */
    private Long userId;
    /**
     * nfc 아이디
     */
    private Long nfcId;
    /**
     * 카드 상태 (W:대기, U:사용, F:동결)
     */
    private char status;
    /**
     * 최초 생성 날짜
     */
    private String createTime;
    /**
     * 마지막 사용 기록
     */
    private String LastTime;
}