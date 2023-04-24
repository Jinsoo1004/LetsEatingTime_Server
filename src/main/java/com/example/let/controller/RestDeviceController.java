package com.example.let.controller;

import com.example.let.domain.User;
import com.example.let.domain.res.CardCheckResponse;
import com.example.let.domain.res.ResponseDto;
import com.example.let.exception.GlobalException;
import com.example.let.service.EntryService;
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

@Tag(name = "디바이스", description = "임베디드 단말장치의 요청 응답 API")
@RestController
@RequestMapping("/api/device")
@AllArgsConstructor
@Log4j2
public class RestDeviceController {
    private final EntryService entryService;
    /**
     * @Name 출입 기록
     * @Path "api/device/check.do"
     * @Request RequestParam(nfcId) : int
     *
     * @text
     * DB에 카드가 존제하는지 확인하고, 유저를 가져온다.
     * 위 두 조건을 만족시 접근 기록을 남긴다
     *
     * @Return String(학번)
     */
    @Operation(summary = "태깅 작성", description = "출입시 태깅한 정보를 확인하고 저장합니다")
    @PostMapping("/check.do")
    public ResponseEntity<?> CheckEntry(@RequestParam(name = "nfcId") Long nfcId
            , @RequestParam(name = "type") String type) throws GlobalException {
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(200)
                        .data(entryService.register(nfcId, type))
                        .build()
                , HttpStatus.OK
        );
    }
}
