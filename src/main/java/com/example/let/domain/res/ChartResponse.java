package com.example.let.domain.res;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 차트 표시용 데이터
 */
@Data
@AllArgsConstructor
public class ChartResponse {
    /**
     * 아이디
     */
    private String id;
    /**
     * 값
     */
    private Long value;
}