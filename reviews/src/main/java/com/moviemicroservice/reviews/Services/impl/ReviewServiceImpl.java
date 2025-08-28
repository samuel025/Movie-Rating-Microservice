package com.moviemicroservice.reviews.Services.impl;

import com.moviemicroservice.reviews.DTOs.ReviewDTO;
import com.moviemicroservice.reviews.Entites.Reviews;
import com.moviemicroservice.reviews.Kafka.ReviewProducer;
import com.moviemicroservice.reviews.Mappers.ReviewMapper;
import com.moviemicroservice.reviews.Repositories.ReviewRepository;
import com.moviemicroservice.reviews.Services.ReviewService;
import com.moviemicroservice.reviews.UserService.UserClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ReviewProducer reviewProducer;
    private final UserClient userClient;
    private final HttpServletRequest request;

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {

        if(reviewDTO.getRating() > 5) throw new IllegalArgumentException("Review Rating should not be greater than 5");
        String AuthHeader = request.getHeader("Authorization");
        if(userClient.existsById(reviewDTO.getUserId(), AuthHeader) == false) {
            throw new ResourceNotFoundException("User with ID " + reviewDTO.getUserId() + " does not exist");
        }
        Reviews existingReview = reviewRepository.findByMovieIdAndUserId(reviewDTO.getMovieId(), reviewDTO.getUserId());
        if (existingReview != null) {
            throw new IllegalArgumentException("User with ID " + reviewDTO.getUserId() +
                    " has already reviewed movie with ID " + reviewDTO.getMovieId());
        }
        Reviews review = reviewRepository.save(reviewMapper.toEntity(reviewDTO));
        ReviewDTO savedReviewDTO = reviewMapper.toDTO(review);
        reviewProducer.sendReviewCreatedEvent(savedReviewDTO);
        log.info("Sent review created event for review: {}", savedReviewDTO);
        return savedReviewDTO;
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Reviews> reviews = reviewRepository.findAll();
        return reviews.stream().map(reviewMapper::toDTO).toList();
    }

    @Override
    public ReviewDTO getReviewById(Long id) {
        Reviews review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review with ID " + id + " not found"));
        return reviewMapper.toDTO(review);
    }

}
