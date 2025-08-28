package com.moviemicroservice.reviews.UserService;

import com.moviemicroservice.reviews.Exceptions.CouldNotAccessResource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserClient {

    private final RestTemplate restTemplate;

    @Value("${application.config.users-url}")
    private String userUrl;

    public Boolean existsById(Long id, String AuthHeader) {
        try {
            HttpHeaders headers = new HttpHeaders();
            if(AuthHeader != null) headers.set("Authorization", AuthHeader);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Boolean> response =  restTemplate.exchange(userUrl + "/exists/" + id, HttpMethod.GET, entity, Boolean.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new CouldNotAccessResource("Could not check user existence:" + e.getMessage());
        }catch (Exception e) {
            throw new CouldNotAccessResource("Unexpected error checking user existence" + e.getMessage());
        }
    }
}
