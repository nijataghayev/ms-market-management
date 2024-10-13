package com.example.marketmanagement.service;

import com.example.marketmanagement.dao.entity.BasketEntity;
import com.example.marketmanagement.dao.repository.BasketRepository;
import com.example.marketmanagement.dao.repository.ProductRepository;
import com.example.marketmanagement.dao.repository.UserProfileRepository;
import com.example.marketmanagement.exception.NotFoundException;
import com.example.marketmanagement.mapper.BasketMapper;
import com.example.marketmanagement.model.BasketDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * @author: nijataghayev
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class BasketService {
    private final BasketRepository basketRepository;
    private final UserProfileRepository userRepository;
    private final ProductRepository productRepository;
    private final BasketMapper basketMapper;

    public BasketDto getBasketById(Long basketId) {
        log.info("ActionLog.getBasketById.start basketId {}", basketId);
        var basketEntity = basketRepository
                .findById(basketId)
                .orElseThrow(() -> new NotFoundException(
                        "BASKET_NOT_FOUND",
                        String.format("ActionLog.getBasketById.id %s not found", basketId)
                ));
        var basketDto = basketMapper.mapToDto(basketEntity);
        log.info("ActionLog.getBasketById.end basketId {}", basketId);

        return basketDto;
    }

    public void createBasket(Long userId) {
        var user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        "USER_NOT_FOUND",
                        String.format("ActionLog.createBasket.id %s not found", userId)
                ));

        BasketEntity basket = new BasketEntity();

        basket.setUser(user);

        basketRepository.save(basket);
    }

    public void addProductToBasket(Long basketId, Long productId, Integer quantity) {
        var basket = basketRepository
                .findById(basketId)
                .orElseThrow(() -> new NotFoundException(
                        "BASKET_NOT_FOUND",
                        String.format("ActionLog.addProductToBasket.id %s not found", basketId)
                ));

        var product = productRepository
                .findById(productId)
                .orElseThrow(() -> new NotFoundException(
                        "PRODUCT_NOT_FOUND",
                        String.format("ActionLog.addProductToBasket.id %s not found", productId)
                ));

        Map<Long, Integer> productQuantities = basket.getProductQuantities();
        productQuantities.put(productId, quantity);
        basket.setProductQuantities(productQuantities);
        basketRepository.save(basket);
    }

    public void removeProductFromBasket(Long basketId, Long productId) {
        var basket = basketRepository
                .findById(basketId)
                .orElseThrow(() -> new NotFoundException(
                        "BASKET_NOT_FOUND",
                        String.format("ActionLog.removeProductFromBasket.id %s not found", basketId)
                ));

        Map<Long, Integer> productQuantities = basket.getProductQuantities();
        productQuantities.remove(productId);
        basket.setProductQuantities(productQuantities);
        basketRepository.save(basket);
    }
}
