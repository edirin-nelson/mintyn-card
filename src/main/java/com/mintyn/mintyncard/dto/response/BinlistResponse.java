package com.mintyn.mintyncard.dto.response;

import com.mintyn.mintyncard.enums.CardScheme;
import com.mintyn.mintyncard.enums.CardType;
import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class BinlistResponse {
    private String number;

    private String scheme;

    private CardType type;

    private String bank;

    private Boolean valid;
}
