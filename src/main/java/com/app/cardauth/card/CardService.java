package com.app.cardauth.card;

import com.app.cardauth.card.payload.DeductAmount;
import com.app.cardauth.card.payload.ValidateCardDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CardService {

    private final CartRepository cartRepository;

    public boolean validateCard(ValidateCardDto request) {
        if (cartRepository.existsByCardTypeAndCardNumberAndCsv(request.getCardType() , request.getCardNumber() , request.getCsv()))
            return true;

        return false;

    }

    public boolean diductAmount(DeductAmount request) {
        CardEntity card = cartRepository.findByCardTypeAndCardNumberAndCsv(request.getCardType() , request.getCardNumber() , request.getCsv()).orElse(null);
        if(card != null){

            if(card.getAmount() >= request.getAmount()){
                card.setAmount(card.getAmount()- request.getAmount());
                cartRepository.save(card);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
