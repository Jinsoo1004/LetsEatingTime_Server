package com.example.let.controller;

import com.example.let.domain.res.ResponseDto;
import com.example.let.domain.User;
import com.example.let.service.AccessService;
import com.example.let.service.EntryService;
import com.example.let.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "교사", description = "정보 열람등 교사 권한이 필요한 api")
@RestController
@RequestMapping("/api/teacher")
@AllArgsConstructor
@Log4j2
public class RestTeacherController {
    private final AccessService accessService;
    private final UserService userService;
    private final EntryService entryService;
    /**
     * @Name 사용자 정보 가져오기
     * @Path "api/teacher/get/user"
     * @Request RequestParam(id) : String
     *
     * @text
     * id가 비어있지 않으면 학번을 포함한 정보를 가져온다.
     * 단 비어있으면 모든 정보를 가져온다.
     *
     * @Return List<User>
     */
    @Operation(summary = "사용자 정보요청", description = "사용자 정보를 요청합니다. id param이 필요하지만, 값이 없을시 전부 가져옵니다")
    @PostMapping("/get/user")
    public ResponseEntity<?> GetUser(@RequestParam(name = "id") String id) {
        if(id.equals("")) {
            return new ResponseEntity<>(
                    ResponseDto.builder()
                            .status(200)
                            .data(userService.get())
                            .build()
                    , HttpStatus.OK
            );
        } else {
            List<User> res = new ArrayList<User>();
            //res.add(userService.get(id));
            return new ResponseEntity<>(
                    ResponseDto.builder()
                            .status(200)
                            .data(res)
                            .build()
                    , HttpStatus.OK
            );
        }
    }

    /**
     * @Name 출입 기록 가져오기
     * @Path "api/teacher/get/entry"
     * @Request RequestParam(id) : String
     *
     * @text
     * user가 비어있지 않으면 학번을 포함한 정보를 가져온다.
     * 단 비어있으면 모든 정보를 가져온다.
     *
     * @Return List<User>
     */
    @Operation(summary = "출입 정보요청", description = "nfc 태깅 기록을 가져옵니다. id param이 필요하지만, 값이 없을시 전부 가져옵니다")
    @PostMapping("/get/entry")
    public ResponseEntity<?> GetEntry(@RequestParam(name = "id") String id) {
        if(id.equals("")) {
            return new ResponseEntity<>(
                    ResponseDto.builder()
                            .status(200)
                            .data(entryService.get())
                            .build()
                    , HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    ResponseDto.builder()
                            .status(200)
                            .data(entryService.get(id))
                            .build()
                    , HttpStatus.OK
            );
        }
    }

    /**
     * @Name 출입 기록 날자를 통해 가져오기
     * @Path "api/teacher/get/entry/date"
     * @Request RequestParam(id) : String, RequestParam(date) : String[data["YYYY-MM-DD"]]
     *
     * @text
     * id, date와 같은 날자의 학번을 포함한 정보를 가져온다.
     *
     * @Return List<Object>
     */
    @Operation(summary = "출입 정보요청(날짜)", description = "출입 정보를 요청합니다, YYYY-MM-DD 로 포멧 된 date가 필요합니다")
    @PostMapping("/get/entry/date")
    public ResponseEntity<?> GetEntryByTime(@RequestParam(name = "date") String date) {
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(200)
                        .data(entryService.getByDate(date))
                        .build()
                , HttpStatus.OK
        );
    }

    /**
     * @Name id와 출입 기록 날자를 통해 가져오기
     * @Path "api/teacher/get/entry/id-date"
     * @Request RequestParam(date) : String[data["YYYY-MM-DD"]]
     *
     * @text
     * date와 같은 날자의 학번을 포함한 정보를 가져온다.
     *
     * @Return Entry[]
     */
    @Operation(summary = "출입 정보요청(날짜)", description = "출입 정보를 요청합니다, YYYY-MM-DD 로 포멧 된 date가 필요합니다")
    @PostMapping("/get/entry/id-date")
    public ResponseEntity<?> GetEntryForMeal(@RequestParam(name = "id") String id, @RequestParam(name = "date") String date) {
        List<Object> list = new ArrayList<>();
        list.addAll(entryService.get(id, date));
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(200)
                        .data(list)
                        .build()
                , HttpStatus.OK
        );
    }
}