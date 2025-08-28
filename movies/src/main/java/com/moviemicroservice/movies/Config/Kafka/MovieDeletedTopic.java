package com.moviemicroservice.movies.Config.Kafka;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class MovieDeletedTopic {

    @Bean
    public NewTopic movieDeletedTopic() {
        return TopicBuilder
                .name("movie-deleted-topic")
                .build();
    }
}
