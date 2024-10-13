package com.example.marketmanagement.service;

import com.example.marketmanagement.dao.entity.ReviewEntity;
import com.example.marketmanagement.dao.repository.ProductRepository;
import com.example.marketmanagement.dao.repository.ReviewRepository;
import com.example.marketmanagement.dao.repository.UserProfileRepository;
import com.example.marketmanagement.exception.NotFoundException;
import com.example.marketmanagement.mapper.ReviewMapper;
import com.example.marketmanagement.model.ReviewReqDto;
import com.example.marketmanagement.model.ReviewResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: nijataghayev
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserProfileRepository userRepository;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;

    public List<ReviewResDto> getReviewsForProduct(Long productId) {
        log.info("ActionLog.getReviewsForProduct.start");
        List<ReviewEntity> reviewEntityList = reviewRepository.findByProductId(productId);
        List<ReviewResDto> reviewDtoList = reviewEntityList
                .stream()
                .map(reviewMapper::mapToDto)
                .toList();
        log.info("ActionLog.getReviewsForProduct.start");

        return reviewDtoList;
    }

    public void createReview(Long userId, Long productId, ReviewReqDto reviewDto) {
        log.debug("ActionLog.createReview.start review {}", reviewDto);
        var user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        "USER_NOT_FOUND",
                        String.format("ActionLog.createReview.id %s not found", userId)
                ));
        var product = productRepository
                .findById(productId)
                .orElseThrow(() -> new NotFoundException(
                        "PRODUCT_NOT_FOUND",
                        String.format("ActionLog.createReview.id %s not found", productId)
                ));

        var reviewEntity = reviewMapper.mapToEntityReq(reviewDto);
        reviewEntity.setUser(user);
        reviewEntity.setProduct(product);
        reviewRepository.save(reviewEntity);
        log.debug("ActionLog.createReview.end review {}", reviewDto);
    }

    public void deleteReview(Long reviewId) {
        log.info("ActionLog.deleteReview.start reviewId {}", reviewId);
        reviewRepository.deleteById(reviewId);
        log.info("ActionLog.deleteReview.end reviewId {}", reviewId);
    }
}
