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
public class Opening {
    /**
     * idx
     */
    private Long idx;
    /**
     * 접근 제어자 정보
     */
    private String type;
    /**
     * 추가적인 정보
     */
    private String info;
    /**
     * 개방 날짜 (없을시 기본 설정)
     */
    private String date;
    /**
     * 개방 시간
     */
    private String openTime;
    /**
     * 패쇄 시간
     */
    private String closeTime;
}
