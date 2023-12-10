package com.mintyn.mintyncard.service.serviceImpl;

import com.mintyn.mintyncard.dto.response.BinlistResponse;
import com.mintyn.mintyncard.entity.Card;
import com.mintyn.mintyncard.repository.CardRepository;
import com.mintyn.mintyncard.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {
    private final WebClient webClient;

    private final CardRepository cardRepository;

    @Override
    public Card verifyCard(String cardNumber) {
        // Call Binlist API
        Mono<BinlistResponse> responseMono = webClient.get()
                .uri("/{cardNumber}", cardNumber)
                .header("Accept-Version", "3")
                .retrieve()
                .bodyToMono(Mono.class)
                .block();

        BinlistResponse binlistResponse = responseMono.block();

        // Build Card entity
        Card card = new Card();
        card.setNumber(cardNumber);
//        card.setScheme(mapScheme(binlistResponse.getScheme().toString()));
//        card.setType(mapType(binlistResponse.getType().toString()));
        card.setBank(binlistResponse.getBank());
//        card.setValid(binlistResponse.isValid());

        // Save to DB
//        card = cardRepository.save(card);
log.info("this worked and => " + binlistResponse);
        return card;
    }

//    private CardScheme mapScheme(String scheme) {
        // Map scheme string to enum
//    }

//    private CardType mapType(String type) {
        // Map type string to enum
//    }
}
