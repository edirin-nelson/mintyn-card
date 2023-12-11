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
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {
    private final WebClient webClient;

    private final CardRepository cardRepository;

    @Override
    public Mono<Card> verifyCard(String cardNumber) {
        // Call Binlist API
        var response = webClient.get()
                .uri("/{cardNumber}", cardNumber)
                .header("Accept-Version", "3")
                .retrieve()
                .bodyToMono(BinlistResponse.class)
                .publishOn(Schedulers.boundedElastic())
                .map(binlistResponse -> {
                    Card card = new Card();
                    card.setNumber(cardNumber);
                    card.setScheme(binlistResponse.getScheme());
//                    card.setType(mapType(binlistResponse.getType()));
//                    card.setBank(binlistResponse.getBank());
//                    card.setValid(binlistResponse.isValid());

                    cardRepository.save(card);
                    // cardRepository.save(card);

                    log.info("Request to Binlist API successful: " + binlistResponse);
                    return card;
                });
//        System.out.println(response.block().getScheme() + " test result");
        System.out.println("working");
        return response;
    }

//    private CardScheme mapScheme(String scheme) {
        // Map scheme string to enum
//    }

//    private CardType mapType(String type) {
        // Map type string to enum
//    }
}
