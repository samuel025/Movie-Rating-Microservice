package com.moviemicroservice.analytics.Mapper;


import com.moviemicroservice.analytics.DTOs.AnalyticsDTO;
import com.moviemicroservice.analytics.Entities.MovieAnalytics;
import org.springframework.stereotype.Component;

@Component
public class MovieAnalyticsMapper {

    public AnalyticsDTO toDTO(MovieAnalytics movieAnalytics) {
        return new AnalyticsDTO(
                movieAnalytics.getId(),
                movieAnalytics.getMovieId(),
                movieAnalytics.getAverageRating(),
                movieAnalytics.getReviewCount(),
                movieAnalytics.getCreatedAt(),
                movieAnalytics.getUpdatedAt()
        );
    }

    public MovieAnalytics toEntity(AnalyticsDTO analyticsDTO) {
        return MovieAnalytics.builder()
                .id(analyticsDTO.getId())
                .movieId(analyticsDTO.getMovieId())
                .reviewCount(analyticsDTO.getReviewCount())
                .averageRating(analyticsDTO.getAverageRating())
                .createdAt(analyticsDTO.getCreatedAt())
                .updatedAt(analyticsDTO.getUpdatedAt())
                .build();
    }

}
