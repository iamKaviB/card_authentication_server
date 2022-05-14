package com.app.cardauth.card.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateCardDto {
    private String cardType;
    private String cardNumber;
    private String csv;
}
