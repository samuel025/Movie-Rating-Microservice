package com.moviemicroservice.reviews.Services;


import com.moviemicroservice.reviews.DTOs.ReviewDTO;

import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(ReviewDTO reviewDTO);

    List<ReviewDTO> getAllReviews();

    ReviewDTO getReviewById(Long id);
}
