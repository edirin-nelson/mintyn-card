package com.mintyn.mintyncard.service;

import com.mintyn.mintyncard.entity.Card;

public interface CardService {
    Card verifyCard(String cardNumber);
}
