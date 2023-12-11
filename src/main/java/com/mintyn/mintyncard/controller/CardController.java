package com.mintyn.mintyncard.controller;

import com.mintyn.mintyncard.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @GetMapping("{cardNumber}")
    public ResponseEntity<?> testCard(@PathVariable String cardNumber){
        cardService.verifyCard(cardNumber);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }
}
