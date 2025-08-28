package com.moviemicroservice.reviews.Mappers;

import com.moviemicroservice.reviews.DTOs.ReviewDTO;
import com.moviemicroservice.reviews.Entites.Reviews;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public Reviews toEntity(ReviewDTO reviewDTO) {
        return Reviews.builder()
                .movieId(reviewDTO.getMovieId())
                .userId(reviewDTO.getUserId())
                .rating(reviewDTO.getRating())
                .comment(reviewDTO.getComment())
                .build();
    }

    public ReviewDTO toDTO(Reviews reviews) {
        return ReviewDTO.builder()
                .movieId(reviews.getMovieId())
                .userId(reviews.getUserId())
                .rating(reviews.getRating())
                .comment(reviews.getComment())
                .build();
    }
}
