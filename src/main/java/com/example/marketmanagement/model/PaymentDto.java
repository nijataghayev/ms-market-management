package com.example.marketmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author: nijataghayev
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long id;
    private Double totalAmount;
    private LocalDateTime paymentDate;
    private User user;
    private Card card;

    @Data
    public static class User {
        private String name;
        private String surname;
    }

    @Data
    public static class Card {
        private String cardNumber;
        private LocalDate creationDate;
    }
}
