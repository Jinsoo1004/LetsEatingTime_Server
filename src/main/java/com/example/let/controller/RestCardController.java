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
    @PostMapping("/create.do")
    public Long RegisterCard(@RequestParam(name = "schoolNumber") String schoolNumber,
                             @RequestParam(name = "nfcId") Long nfcId) {
        return cardService.register(schoolNumber, nfcId);
    }
}
