package com.example.let.service;

import com.example.let.domain.TokenInfo;
import com.example.let.domain.User;

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
    public User get(Long idx);
    /**
     * 해당 학번을 갖는 학생을 가져온다.
     *
     * @param String id
     * @return User
     */
    public User get(String id);
    /**
     * 모든 유저를 가져온다.
     *
     * @return User[]
     */
    public User[] get();
    /**
     * 로그인 한다.
     *
     * @param String id, String password
     * @return TokenInfo
     */
    public TokenInfo login(String id, String password);
}
