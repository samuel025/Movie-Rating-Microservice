package com.moviemicroservice.reviews.config;


import com.moviemicroservice.reviews.DTOs.ReviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


public class KafkaReviewCreatedTopic {

    @Bean
    public NewTopic reviewTopic() {
        return TopicBuilder
                .name("review-created-topic")
                .build();
    }

}
