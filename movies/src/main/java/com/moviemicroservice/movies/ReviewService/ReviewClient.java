package com.moviemicroservice.movies.ReviewService;


import com.moviemicroservice.movies.Exceptions.CouldNotAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ReviewClient {

    private final RestTemplate restTemplate;

    @Value("${application.config.review-url}")
    private String reviewUrl;

    public ReviewDTO createReview(ReviewDTO reviewDTO, String AuthHeader) {
        try {
            HttpHeaders headers = new HttpHeaders();
            if(AuthHeader != null) headers.set("Authorization", AuthHeader);
            HttpEntity<ReviewDTO> entity = new HttpEntity<>(reviewDTO, headers);
            ResponseEntity<ReviewDTO> response = restTemplate.exchange(reviewUrl, HttpMethod.POST, entity, ReviewDTO.class);
            return response.getBody();
        } catch (Exception e) {
            throw new CouldNotAccessService(e.getMessage());
        }
    }

}
