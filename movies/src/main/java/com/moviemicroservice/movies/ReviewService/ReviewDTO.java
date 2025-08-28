package com.moviemicroservice.movies.ReviewService;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long movieId;
    private Long userId;
    private Long rating;
    private String comment;
}
