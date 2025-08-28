package com.moviemicroservice.movies.kafka;


import com.moviemicroservice.movies.DTOs.MovieDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMovieDeletedTopic(String id) {
        log.info("Sending movie deleted event: Move Id - {}", id);
        kafkaTemplate.send("movie-deleted-topic", id);
    }

}
