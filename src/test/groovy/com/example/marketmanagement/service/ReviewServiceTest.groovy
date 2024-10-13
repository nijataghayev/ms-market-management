package com.example.marketmanagement.service

import com.example.marketmanagement.dao.entity.ProductEntity
import com.example.marketmanagement.dao.entity.ReviewEntity
import com.example.marketmanagement.dao.entity.UserProfileEntity
import com.example.marketmanagement.dao.repository.ProductRepository
import com.example.marketmanagement.dao.repository.ReviewRepository
import com.example.marketmanagement.dao.repository.UserProfileRepository
import com.example.marketmanagement.exception.NotFoundException
import com.example.marketmanagement.mapper.ReviewMapper
import com.example.marketmanagement.model.ReviewReqDto
import com.example.marketmanagement.model.ReviewResDto
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

/**
 * @author: nijataghayev
 */

class ReviewServiceTest extends Specification {
    private EnhancedRandom random
    private ReviewService reviewService
    private ReviewRepository reviewRepository
    private UserProfileRepository userRepository
    private ProductRepository productRepository
    private ReviewMapper reviewMapper

    void setup() {
        random = EnhancedRandomBuilder.aNewEnhancedRandom()
        reviewRepository = Mock()
        userRepository = Mock()
        productRepository = Mock()
        reviewMapper = Mock()
        reviewService = new ReviewService(reviewRepository, userRepository, productRepository, reviewMapper)
    }

    def "GetReviewsForProduct"() {
        given:
        def productId = 1L
        def reviewEntities = random.objects(ReviewEntity, 3).toList()
        def reviewDtos = reviewEntities.collect { random.nextObject(ReviewResDto) }

        reviewRepository.findByProductId(productId) >> reviewEntities
        reviewEntities.eachWithIndex { entity, i ->
            reviewMapper.mapToDto(entity) >> reviewDtos[i]
        }

        when:
        def result = reviewService.getReviewsForProduct(productId)

        then:
        result.size() == reviewDtos.size()
        result == reviewDtos
    }

    def "CreateReview - success"() {
        given:
        def userId = 1L
        def productId = 2L
        def reviewDto = random.nextObject(ReviewReqDto)
        def user = random.nextObject(UserProfileEntity)
        def product = random.nextObject(ProductEntity)
        def review = random.nextObject(ReviewEntity)

        when:
        reviewService.createReview(userId, productId, reviewDto)

        then:
        1 * userRepository.findById(userId) >> Optional.of(user)
        1 * productRepository.findById(productId) >> Optional.of(product)
        1 * reviewMapper.mapToEntityReq(reviewDto) >> review
        review.setUser(user);
        review.setProduct(product);
        1 * reviewRepository.save(review)
    }

    def "CreateReview - user not found"() {
        given:
        def userId = 1L
        def productId = 2L
        def reviewDto = random.nextObject(ReviewReqDto)

        userRepository.findById(userId) >> Optional.empty()

        when:
        reviewService.createReview(userId, productId, reviewDto)

        then:
        thrown(NotFoundException)
        0 * reviewRepository.save(_)
    }

    def "CreateReview - product not found"() {
        given:
        def userId = 1L
        def productId = 2L
        def reviewDto = random.nextObject(ReviewReqDto)
        def userEntity = random.nextObject(UserProfileEntity)

        userRepository.findById(userId) >> Optional.of(userEntity)
        productRepository.findById(productId) >> Optional.empty()

        when:
        reviewService.createReview(userId, productId, reviewDto)

        then:
        thrown(NotFoundException)
        0 * reviewRepository.save(_)
    }

    def "DeleteReview"() {
        given:
        def reviewId = 1L

        when:
        reviewService.deleteReview(reviewId)

        then:
        1 * reviewRepository.deleteById(reviewId)
    }
}
