package com.app.cardauth.card;

import com.app.cardauth.card.payload.DeductAmount;
import com.app.cardauth.card.payload.ValidateCardDto;
import com.app.cardauth.common.FieldValidationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/card")
@AllArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateCard(@RequestBody ValidateCardDto request){

        // controller level validations
        if(request.getCardType().isEmpty()){
            throw new FieldValidationException("Card type is required");
        }

        if(request.getCardType().equals(CardTypeConstant.VISA) ||request.getCardType().equals(CardTypeConstant.MASTER )|| request.getCardType().equals(CardTypeConstant.AMEX) ){
            throw new FieldValidationException("Card type miss match");
        }

        if(request.getCardNumber().isEmpty()){
            throw new FieldValidationException("card number is required");
        }

        if(request.getCardNumber().length() != 16){
            throw new FieldValidationException("Card number is not valid");
        }

        return new ResponseEntity<>(cardService.validateCard(request), HttpStatus.OK);

    }

    @PostMapping("/deduct")
    public ResponseEntity<Boolean> deductAmount(@RequestBody DeductAmount request){
        // controller level validations
        if(request.getCardType().isEmpty()){
            throw new FieldValidationException("Card type is required");
        }

        if(!(request.getCardType().equals(CardTypeConstant.VISA) || request.getCardType().equals(CardTypeConstant.MASTER) || request.getCardType().equals(CardTypeConstant.AMEX) )){
            throw new FieldValidationException("Card type miss match");
        }

        if(request.getCardNumber().isEmpty()){
            throw new FieldValidationException("card number is required");
        }

        if(request.getCardNumber().length() != 16){
            throw new FieldValidationException("Card number is not valid");
        }

        if(request.getAmount() <= 0){
            throw new FieldValidationException("give proper amount");
        }

        boolean result = cardService.diductAmount(request);

        if(result){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(result, HttpStatus.BAD_GATEWAY);
        }

    }

}
