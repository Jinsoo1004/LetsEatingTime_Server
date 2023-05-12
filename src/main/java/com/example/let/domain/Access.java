package com.example.let.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 접근 권한
 */
@Data
@Builder
@AllArgsConstructor
public class Access {
    /**
     * idx
     */
    private Long idx;
    /**
     * 유저 idx
     */
    private Long userId;
    /**
     * 부여자 idx
     */
    private Long grantId;
    /**
     * 권한 정보
     */
    private String type;
    /**
     * 권한 정보
     */
    private String grantTime;
}
