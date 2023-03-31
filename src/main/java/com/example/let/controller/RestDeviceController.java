package com.example.let.controller;

import com.example.let.exception.GlobalException;
import com.example.let.service.EntryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    @PostMapping("/check.do")
    public String CheckEntry(@RequestParam(name = "nfcId") Long nfcId) throws GlobalException {
        return entryService.register(nfcId);
    }
}
