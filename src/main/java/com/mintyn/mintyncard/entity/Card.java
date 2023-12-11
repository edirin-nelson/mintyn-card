package com.mintyn.mintyncard.entity;

import com.mintyn.mintyncard.enums.CardScheme;
import com.mintyn.mintyncard.enums.CardType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

//    @Enumerated(EnumType.STRING)
    private String scheme;

    @Enumerated(EnumType.STRING)
    private CardType type;

    @OneToOne
    private Bank bank;

    private Boolean valid;
}
