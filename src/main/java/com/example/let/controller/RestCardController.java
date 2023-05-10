package com.example.let.controller;

import com.example.let.domain.res.ResponseDto;
import com.example.let.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "카드", description = "카드를 생성, 삭제, 편집등을 하는 API")
@RestController
@RequestMapping("/api/card")
@AllArgsConstructor
public class RestCardController {
    private final CardService cardService;

    /**
     * @Name 카드 생성
     * @Path "api/card/create.do"
     * @Request RequestParam(user_id) : String
     * @Request RequestParam(nfcId) : String
     *
     * @text
     * 새로운 카드를 등록 한다.
     *
     * @Return Long(nfc_Code)
     */
    @Operation(summary = "카드 생성", description = "카드를 생성 합니다")
    @PostMapping("/create.do")
    public ResponseEntity<?> RegisterCard(@RequestParam(name = "id") String id,
                                    @RequestParam(name = "nfcId") Long nfcId) {
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(200)
                        .data(cardService.register(id, nfcId))
                        .build()
                , HttpStatus.OK
        );
    }
}