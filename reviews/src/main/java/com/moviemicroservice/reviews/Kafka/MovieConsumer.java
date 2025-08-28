package com.moviemicroservice.reviews.Kafka;


import com.moviemicroservice.reviews.Entites.Reviews;
import com.moviemicroservice.reviews.Repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieConsumer {

    private final ReviewRepository reviewRepository;

    @KafkaListener(topics = "movie-deleted-topic")
    public void consumeMovieDeleted(String movieId) {
        try {
            String trimmedId = movieId.trim();
            if (trimmedId.isEmpty()) {
                log.warn("Received empty movie ID");
                return;
            }

            Long id = Long.valueOf(trimmedId);

            List<Reviews> reviews = reviewRepository.findByMovieId(id);
            if (!reviews.isEmpty()) {
                reviewRepository.deleteAll(reviews);
                log.info("Deleted reviews for movie with ID - {}", id);
            } else {
                log.info("No reviews found for movie with ID - {}", id);
            }
        } catch (NumberFormatException e) {
            log.error("Failed to parse movie ID: '{}'. Error: {}", movieId, e.getMessage());
        }
    }
}