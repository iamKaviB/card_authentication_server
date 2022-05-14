package com.app.cardauth.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CardEntity , Long> {

    boolean existsByCardTypeAndCardNumberAndCsv(String cardType, String cardNumber, String csv);

    Optional<CardEntity> findByCardTypeAndCardNumberAndCsv(String cardType, String cardNumber, String csv);
}
