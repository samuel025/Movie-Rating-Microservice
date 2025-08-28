package com.moviemicroservice.analytics.Kafka;


import com.moviemicroservice.analytics.Repositories.MovieAnalyticsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieConsumer {

    private  final MovieAnalyticsRepository movieAnalyticsRepository;

    @KafkaListener(topics = "movie-deleted-topic", groupId = "analytics-movie-del-group", containerFactory = "movieStringKafkaListenerContainerFactory")
    @Transactional
    public void consumeMovieDeleted(String movieId) {
        try {
            if (movieId == null || movieId.trim().isEmpty()) {
                log.warn("Received null or empty movie ID, skipping processing");
                return;
            }

            String trimmedId = movieId.trim();
            Long id = Long.valueOf(trimmedId);

            if (movieAnalyticsRepository.findByMovieId(id) != null) {
                movieAnalyticsRepository.deleteAllByMovieId(id);
                log.info("Deleted analytics for movie with ID - {}", id);
            } else {
                log.info("No analytics found for movie with ID - {}", id);
            }
        } catch (NumberFormatException e) {
            log.error("Failed to parse movie ID: '{}'. Error: {}", movieId, e.getMessage());
        }
    }

}
