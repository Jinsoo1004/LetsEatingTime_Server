package com.example.let.controller;

import com.example.let.domain.Card;
import com.example.let.domain.Entry;
import com.example.let.domain.User;
import com.example.let.exception.CustomException;
import com.example.let.service.CardService;
import com.example.let.service.EntryService;
import com.example.let.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card")
@AllArgsConstructor
@Log4j2
public class RestCardController {
    private final CardService cardService;
    private final EntryService entryService;

    /**
     * @Name 카드 생성
     * @Path "api/card/create.do"
     * @Request RequestBody(Json) : Card[schoolNumber, nfcId]
     *
     * @text
     * 새로운 카드를 등록 한다.
     *
     * @Return Long(nfc_Code)
     */
    @PostMapping("/create.do")
    public Long RegisterCard(@RequestBody Card card) {
        return cardService.register(card);
    }
    /**
     * @Name 출입
     * @Path "api/card/check.do"
     * @Request RequestParam(nfcId) : int
     *
     * @text
     * DB에 카드가 존제하는지 확인하고, 유저를 가져온다.
     * 위 두 조건을 만족시 접근 기록을 남긴다
     *
     * @Return String(학번)
     */
    @PostMapping("/check.do")
    public String CheckEntry(@RequestParam(name = "nfcId") Long nfcId) throws CustomException {
        return entryService.register(nfcId);
    }
}
