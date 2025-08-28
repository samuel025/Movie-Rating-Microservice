package com.moviemicroservice.analytics.Kafka;

import com.moviemicroservice.analytics.DTOs.ReviewDTO;
import com.moviemicroservice.analytics.Entities.MovieAnalytics;
import com.moviemicroservice.analytics.Repositories.MovieAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewsConsumer {

    private final MovieAnalyticsRepository movieAnalyticsRepository;

    @KafkaListener(topics = "review-created-topic", groupId = "analytics-group")
    public void consumeReviewCreated(ReviewDTO reviewDTO) {
        log.info("Received Review Created Event {}", reviewDTO);
        MovieAnalytics movieAnalytic = movieAnalyticsRepository.findFirstByMovieId(reviewDTO.getMovieId());

        if(movieAnalytic != null) {
            var currentAverageRating = movieAnalytic.getAverageRating();
            var currentReviewCount = movieAnalytic.getReviewCount();
            movieAnalytic.setAverageRating(((currentAverageRating * currentReviewCount) + reviewDTO.getRating())/(currentReviewCount + 1));
            movieAnalytic.setReviewCount(movieAnalytic.getReviewCount()+1);
            movieAnalyticsRepository.save(movieAnalytic);
        } else {
            MovieAnalytics movieAnalytics = MovieAnalytics.builder()
                    .movieId(reviewDTO.getMovieId())
                    .averageRating(reviewDTO.getRating())
                    .reviewCount(1)
                    .build();
            movieAnalyticsRepository.save(movieAnalytics);
        }
    }

}
