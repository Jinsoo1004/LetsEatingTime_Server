package com.example.let.controller;

import com.example.let.domain.Entry;
import com.example.let.domain.User;
import com.example.let.mapper.EntryMapper;
import com.example.let.mapper.UserMapper;
import com.example.let.service.EntryService;
import com.example.let.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@Log4j2
public class RestTeacherController {
    private final UserService userService;
    private final EntryService entryService;
    /**
     * @Name 사용자 정보 가져오기
     * @Path "api/teacher/get/user"
     * @Request RequestParam(schoolNumber) : String
     *
     * @text
     * schoolNumber가 비어있지 않으면 학번을 포함한 정보를 가져온다.
     * 단 비어있으면 모든 정보를 가져온다.
     *
     * @Return Entry[]
     */
    @PostMapping("/get/user")
    public User[] GetUser(@RequestParam(name = "schoolNumber") String schoolNumber) {
        if(schoolNumber.equals("")) {
            return userService.get();
        } else {
            return userService.get(schoolNumber);
        }
    }

    /**
     * @Name 출입 기록 가져오기
     * @Path "api/teacher/get/entry"
     * @Request RequestParam(schoolNumber) : String
     *
     * @text
     * user가 비어있지 않으면 학번을 포함한 정보를 가져온다.
     * 단 비어있으면 모든 정보를 가져온다.
     *
     * @Return Entry[]
     */
    @PostMapping("/get/entry")
    public Entry[] GetEntry(@RequestParam(name = "user") String userCode) {
        if(userCode.equals("")) {
            return entryService.get();
        } else {
            return entryService.get(userCode);
        }
    }
    /**
     * @Name 출입 기록 날자를 통해 가져오기
     * @Path "api/teacher/get/entry/date"
     * @Request RequestParam(date) : String[data["YYYY-MM-DD"]]
     *
     * @text
     * date와 같은 날자의 학번을 포함한 정보를 가져온다.
     *
     * @Return Entry[]
     */
    @PostMapping("/get/entry/date")
    public Entry[] GetEntryByTime(@RequestParam(name = "date") String date) {
        return entryService.getByDate(date);
    }
}