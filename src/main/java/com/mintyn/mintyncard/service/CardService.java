package com.mintyn.mintyncard.service;

import com.mintyn.mintyncard.entity.Card;
import reactor.core.publisher.Mono;

public interface CardService {
    Mono<Card> verifyCard(String cardNumber);
}
