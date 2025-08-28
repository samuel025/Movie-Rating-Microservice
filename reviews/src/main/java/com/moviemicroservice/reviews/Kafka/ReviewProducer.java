package com.moviemicroservice.reviews.Kafka;

import com.moviemicroservice.reviews.DTOs.ReviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewProducer {

    private final KafkaTemplate<String, ReviewDTO> kafkaTemplate;

    public void sendReviewCreatedEvent(ReviewDTO reviewDTO){
        log.info("Sending review created event: {}", reviewDTO);
        kafkaTemplate.send("review-created-topic", reviewDTO);
    }

}
