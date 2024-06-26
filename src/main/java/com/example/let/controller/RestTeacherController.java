package com.example.let.controller;

import com.example.let.domain.Opening;
import com.example.let.domain.User;
import com.example.let.domain.res.ResponseDto;
import com.example.let.exception.GlobalException;
import com.example.let.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "교사", description = "정보 열람등 교사 권한이 필요한 api")
@RestController
@RequestMapping("/api/teacher")
@AllArgsConstructor
public class RestTeacherController {
    private final AccessService accessService;
    private final UserService userService;
    private final EntryService entryService;
    private final OpeningService openingService;
    private final FileUploadService fileUploadService;
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
    @GetMapping("/get/user")
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
            return new ResponseEntity<>(
                    ResponseDto.builder()
                            .status(200)
                            .data(userService.get(id))
                            .build()
                    , HttpStatus.OK
            );
        }
    }

    @Operation(summary = "사용자 권한 정보요청", description = "사용자 권한 정보를 요청합니다. id param이 필요합니다")
    @GetMapping("/get/user/access")
    public ResponseEntity<?> GetAccess(@RequestParam(name = "id") String id) {
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(200)
                        .data(accessService.getById(id))
                        .build()
                , HttpStatus.OK
        );
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
    @GetMapping("/get/entry")
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
    @GetMapping("/get/entry/date")
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
    @Operation(summary = "출입 정보요청(id/날짜)", description = "출입 정보를 요청합니다, YYYY-MM-DD 로 포멧 된 date와 id가 필요합니다")
    @GetMapping("/get/entry/id-date")
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

    /**
     * @Name 개방 요청
     * @Path "api/teacher/opening"
     * @Request RequestParam(opening) : Opening
     *
     * @text
     * 특정 개체의 개방을 요청합니다. openTime에 대한 명시가 없으면 현시간부터로 판단합니다.
     *
     * @Return Opening[]
     */
    @Operation(summary = "개방 요청",
            description = "특정 개체의 개방을 요청합니다." +
                    "openTime에 대한 명시가 없으면 현시간부터로 판단합니다.")
    @PostMapping("/opening")
    public ResponseEntity<?> ReqOpening(@RequestBody Opening opening
                                       ,@RequestParam(name = "id") String id) {
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(200)
                        .data(openingService.register(opening, id))
                        .build()
                , HttpStatus.OK
        );
    }

    /**
     * @Name 개방 정보 확인
     * @Path "api/teacher/get/opening"
     * @Request RequestParam(opening) : Opening
     *
     * @text
     * 모든 개체의 개방 정보를 요청합니다. type에 대한 명시가 없을시 모두 가져오며, 마찬가지로 date에 관한 명시가 없으면 모두 가져옵니다.
     *
     * @Return Opening[]
     */
    @Operation(summary = "개방 정보 요청",
            description = "모든 개체의 개방 정보를 요청합니다." +
                    "type에 대한 명시가 없을시 모두 가져오며," +
                    "마찬가지로 date에 관한 명시가 없으면 모두 가져옵니다.")
    @GetMapping("/get/opening")
    public ResponseEntity<?> GetOpening(@RequestParam(name = "type") String type,
                                        @RequestParam(name = "date") String date) {
        List<Opening> openings;
        if(type.isEmpty()) {
            if(date.isEmpty()) {
                openings = openingService.get();
            } else {
                openings = openingService.getByDate(date);
            }
        } else if(date.isEmpty()) {
            openings = openingService.get(type);
        } else {
            openings = openingService.getByDeviceAndDate(type, date);
        }

        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(200)
                        .data(openings)
                        .build()
                , HttpStatus.OK
        );
    }

    /**
     * @Name 증명 사진 등록
     * @Path "api/teacher/id-photo/upload"
     * @Request RequestParam(form) : Form
     *
     * @text
     * 증명 이미지를 등록합니다.
     *
     * @Return Opening[]
     */
    @Operation(summary = "증명 사진 등록",
            description = "증명 사진을 등록합니다.")
    @PostMapping("/id-photo/upload")
    public ResponseEntity<?> idPhoto(@RequestParam("image") MultipartFile multipartFile
            ,@RequestParam(name = "id") String id) {
        fileUploadService.registerFile(multipartFile, id);
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(200)
                        .data("successfully completed")
                        .build()
                , HttpStatus.OK
        );
    }

    /**
     * @Name 접근 권한 추가
     * @Path "api/teacher/access/add"
     * @Request RequestParam(form) : Form
     *
     * @text
     * 특정 사용자에 대한 접근 권한을 상속합니다.
     *
     * @Return
     */
    @Operation(summary = "접근 권한 추가",
            description = "특정 사용자에 대한 접근 권한을 상속합니다.")
    @PostMapping("/access/add")
    public ResponseEntity<?> accessAdd(
            @RequestParam(name = "targetId") String targetId
            ,@RequestParam(name = "grantId") String grantId
            ,@RequestParam(name = "type") String type) {
        accessService.register(targetId, grantId, type);
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(200)
                        .data("successfully completed")
                        .build()
                , HttpStatus.OK
        );
    }
    /**
     * @Name 회원 승인
     * @Path "api/teacher/approve"
     * @Request RequestParam(form) : Form
     *
     * @text
     * 새로운 유저를 승인합니다.
     *
     * @Return
     */
    @Operation(summary = "회원 승인",
            description = "새로운 유저를 승인합니다.")
    @PostMapping("/approve")
    public ResponseEntity<?> accessAdd(
            @RequestParam(name = "id") String id) {
        userService.approve(id);
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(200)
                        .data("successfully completed")
                        .build()
                , HttpStatus.OK
        );
    }
    /**
     * @Name 회원 편집
     * @Path "api/teacher/edit/student"
     * @Request RequestParam(form) : Form
     *
     * @text
     * 유저정보를 편집합니다.
     *
     * @Return
     */
    @Operation(summary = "회원 편집",
            description = "유저정보를 편집합니다.")
    @PostMapping("/edit/student")
    public ResponseEntity<?> editStudent(
            @RequestBody User user) {
        userService.reset(user);
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(200)
                        .data("successfully completed")
                        .build()
                , HttpStatus.OK
        );
    }
    /**
     * @Name 회원 삭제
     * @Path "api/teacher/delete/user"
     * @Request RequestParam(form) : Form
     *
     * @text
     * 새로운 유저를 승인합니다.
     *
     * @Return
     */
    @Operation(summary = "회원 삭제",
            description = "유저를 삭제합니다.")
    @DeleteMapping(value="/delete/user")
    public ResponseEntity<?> userDelete(
            @RequestParam(name = "id") String id) {
        userService.delete(id);
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(200)
                        .data("successfully completed")
                        .build()
                , HttpStatus.OK
        );
    }

    /**
     * @Name 사용자 급식 신청 일괄 처리
     * @Path "form/upload/meal"
     *
     * @text
     * excel을 이용하여 사용자 급식 신청을 일괄 처리를 등록한다
     *
     * @Return Meal
     */
    @Operation(summary = "사용자 급식 신청 일괄 처리", description = "excel을 이용하여 사용자 급식 신청을 일괄 처리를 등록한다.")
    @PostMapping(value="/form/upload/meal")
    public ResponseEntity<?> setMealBundle(
            @RequestParam("bundle") MultipartFile multipartFile
    ) throws IOException {
        return new ResponseEntity<>(ResponseDto.builder()
                .status(200)
                .data(fileUploadService.setMealBundle(multipartFile))
                .build()
                , HttpStatus.OK
        );
    }
    /**
     * @Name 사용자 급식 신청 일괄 처리 양식
     * @Path "form/download/meal"
     *
     * @text
     * 사용자 급식 신청을 일괄 처리하는 excel 양식을 반환한다
     *
     * @Return BinaryData
     */
    @Operation(summary = "사용자 급식 신청 일괄 처리 양식", description = "사용자 급식 신청을 일괄 처리하는 excel 양식을 반환한다")
    @GetMapping(value="/form/download/meal")
    public ResponseEntity<byte[]> getMealApplicationForm()
    {
        try {
            byte[] bytes = fileUploadService.getMealBundleForm();

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition",
                            "attachment; filename=\"" +
                                    URLEncoder.encode("주말 급식 신청 양식.xlsx",
                                            "UTF-8") +
                                    "\""
                    )
                    .body(bytes);
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
        throw new GlobalException(HttpStatus.BAD_REQUEST, "unknown error");
    }
}