package com.example.marketmanagement.service;

import com.example.marketmanagement.client.NijatBankClient;
import com.example.marketmanagement.dao.entity.BasketEntity;
import com.example.marketmanagement.dao.entity.CardEntity;
import com.example.marketmanagement.dao.entity.PaymentEntity;
import com.example.marketmanagement.dao.entity.ProductEntity;
import com.example.marketmanagement.dao.entity.UserProfileEntity;
import com.example.marketmanagement.dao.repository.BasketRepository;
import com.example.marketmanagement.dao.repository.CardRepository;
import com.example.marketmanagement.dao.repository.PaymentRepository;
import com.example.marketmanagement.dao.repository.ProductRepository;
import com.example.marketmanagement.dao.repository.UserProfileRepository;
import com.example.marketmanagement.exception.NotFoundException;
import com.example.marketmanagement.mapper.PaymentMapper;
import com.example.marketmanagement.model.PaymentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: nijataghayev
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final NijatBankClient nijatBankClient;
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final CardRepository cardRepository;
    private final UserProfileRepository userRepository;
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;

    public void processPayment(Long userId, Long cardId, Long accountId) {
        UserProfileEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        "USER_NOT_FOUND",
                        String.format("ActionLog.processPayment.id %s not found", userId)
                ));
        CardEntity card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException(
                        "CARD_NOT_FOUND",
                        String.format("ActionLog.processPayment.id %s not found", cardId)
                ));
        BasketEntity basket = user.getBasket();

        if (basket == null || basket.getProducts().isEmpty()) {
            throw new RuntimeException("Basket is empty");
        }

        Double totalAmount = basket.getProducts()
                .stream()
                .mapToDouble(product -> product.getPrice() * basket.getProductQuantities()
                        .getOrDefault(product.getId(), 0))
                .sum();

        nijatBankClient.processPayment(accountId, totalAmount);

        Double cashback = totalAmount * 0.01;
        card.setAmount(cashback);

        for (ProductEntity product : basket.getProducts()) {
            int quantity = basket.getProductQuantities().get(product.getId());
            product.setQuantity(product.getQuantity() - quantity);
            productRepository.save(product);
        }

        PaymentEntity payment = new PaymentEntity();
        payment.setUser(user);
        payment.setCard(card);
        payment.setTotalAmount(totalAmount);
        payment.setPaymentDate(LocalDateTime.now());

        paymentRepository.save(payment);

        basket.getProducts().clear();
        basket.getProductQuantities().clear();

        basketRepository.save(basket);
        cardRepository.save(card);
    }

    public List<PaymentDto> getAllPayments() {
        log.info("ActionLog.getAllPayments.start");
        List<PaymentEntity> paymentEntityList = paymentRepository.findAll();
        List<PaymentDto> paymentDtoList = paymentEntityList.stream()
                .map(paymentMapper::mapToDto)
                .toList();
        log.info("ActionLog.getAllPayments.end");

        return paymentDtoList;
    }

    public PaymentDto getPayment(Long paymentId) {
        log.info("ActionLog.getPayment.start");
        PaymentEntity paymentEntity = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException(
                        "PAYMENT_NOT_FOUND",
                        String.format("ActionLog.getPayment.id %s not found", paymentId)
                ));
        PaymentDto paymentDto = paymentMapper.mapToDto(paymentEntity);
        log.info("ActionLog.getPayment.start");

        return paymentDto;
    }
}
