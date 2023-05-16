package com.example.let.service;

import com.example.let.domain.TokenInfo;
import com.example.let.domain.User;
import com.example.let.domain.UserForMeal;
import com.example.let.domain.res.ChartResponse;
import java.util.List;

public interface UserService {
    /**
     * 유저를 생성한다.
     *
     * @param User user
     * @return String[id]
     */
    public String register(User user);
    /**
     * 해당 인덱스를 갖는 학생을 가져온다.
     *
     * @param Long idx
     * @return User
     */
    public UserForMeal get(Long idx);

    /**
     * 해당 학번을 갖는 학생을 가져온다.
     *
     * @param String id
     * @return User
     */
    public UserForMeal get(String id);
    /**
     * 모든 유저를 가져온다.
     *
     * @return User[]
     */
    public List<UserForMeal> get();
    /**
     * 사용자를 승인한다.
     *
     * @param String id
     * @return id
     */
    public void approve(String id);

    /**
     * 로그인 한다.
     *
     * @param String id, String password
     * @return TokenInfo
     */
    public TokenInfo login(String id, String password);
    /**
     * refresh token을 설정 한다.
     *
     * @param String id, String refreshToken
     * @return
     */
    public void setRefreshToken(String id, String refreshCode);
    /**
     * refresh token을 가져온다.
     *
     * @param String id
     * @return String
     */
    public String getRefreshToken(String id);
    /**
     * 해당 학번을 갖는 학생을 가져온다.
     *
     * @param String token
     * @return User
     */
    public TokenInfo refresh(String token);
    /**
     * 급식 신청 현황 통계를 가져온다.
     *
     * @param
     * @return ChartResponse
     */
    public List<ChartResponse> getChartByMealApplication();
    /**
     * 급식 참석 현황 통계를 가져온다.
     *
     * @param
     * @return ChartResponse
     */
    public List<ChartResponse> getChartByMealAttend(String type);
    /**
     * 회원을 삭제합니다.
     *
     * @param
     * @return ChartResponse
     */
    public void delete(String id);
}
