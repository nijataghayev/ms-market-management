package com.example.marketmanagement.service

import com.example.marketmanagement.dao.entity.BasketEntity
import com.example.marketmanagement.dao.entity.ProductEntity
import com.example.marketmanagement.dao.entity.UserProfileEntity
import com.example.marketmanagement.dao.repository.BasketRepository
import com.example.marketmanagement.dao.repository.ProductRepository
import com.example.marketmanagement.dao.repository.UserProfileRepository
import com.example.marketmanagement.mapper.BasketMapper
import com.example.marketmanagement.model.BasketDto
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

/**
 * @author: nijataghayev
 */

class BasketServiceTest extends Specification {
    private EnhancedRandom random
    private BasketService basketService
    private BasketRepository basketRepository;
    private UserProfileRepository userRepository;
    private ProductRepository productRepository;
    private BasketMapper basketMapper;

    void setup() {
        random = EnhancedRandomBuilder.aNewEnhancedRandom()
        basketRepository = Mock()
        userRepository = Mock()
        productRepository = Mock()
        basketMapper = Mock()

        basketService = new BasketService(basketRepository, userRepository, productRepository, basketMapper)
    }

    def "GetBasketById"() {
        given:
        def basketId = 1
        def basketEntity = random.nextObject(BasketEntity)
        def basketDto = random.nextObject(BasketDto)

        when:
        def result = basketService.getBasketById(basketId)

        then:
        1 * basketRepository.findById(basketId) >> Optional.of(basketEntity)
        1 * basketMapper.mapToDto(basketEntity) >> basketDto

        result == basketDto
    }

    def "CreateBasket"() {
        given:
        def userId = 1
        def userEntity = random.nextObject(UserProfileEntity)

        when:
        basketService.createBasket(userId)

        then:
        1 * userRepository.findById(userId) >> Optional.of(userEntity)
        1 * basketRepository.save(_ as BasketEntity)
    }

    def "AddProductToBasket"() {
        given:
        def basketId = 1L
        def productId = 2L
        def quantity = 5
        def basketEntity = random.nextObject(BasketEntity)
        def productEntity = random.nextObject(ProductEntity)
        def productQuantities = new HashMap<Long, Integer>()

        basketEntity.setProductQuantities(productQuantities)

        when:
        basketService.addProductToBasket(basketId, productId, quantity)

        then:
        1 * basketRepository.findById(basketId) >> Optional.of(basketEntity)
        1 * productRepository.findById(productId) >> Optional.of(productEntity)

        and:
        basketEntity.getProductQuantities().get(productId) == quantity
        1 * basketRepository.save(basketEntity)
    }

    def "RemoveProductFromBasket"() {
        given:
        def basketId = 1L
        def productId = 2L
        def basketEntity = random.nextObject(BasketEntity)
        def productQuantities = new HashMap<Long, Integer>()
        productQuantities.put(productId, 3)

        basketEntity.setProductQuantities(productQuantities)

        when:
        basketService.removeProductFromBasket(basketId, productId)

        then:
        1 * basketRepository.findById(basketId) >> Optional.of(basketEntity)

        and:
        !basketEntity.getProductQuantities().containsKey(productId)
        1 * basketRepository.save(basketEntity)
    }
}
