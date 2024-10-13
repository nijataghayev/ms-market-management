package com.example.marketmanagement.dao.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

/**
 * @author: nijataghayev
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "cards")
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "card_number")
    private String cardNumber;
    private Double amount = 0.0;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "expire_date")
    private LocalDate expireDate;
    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL)
    private UserProfileEntity user;
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<PaymentEntity> payments;

    @PrePersist
    public void prePersist() {
        this.cardNumber = generateCardNumber();
        this.creationDate = LocalDate.now();
        this.expireDate = LocalDate.now().plusYears(15);
    }

    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }
}
