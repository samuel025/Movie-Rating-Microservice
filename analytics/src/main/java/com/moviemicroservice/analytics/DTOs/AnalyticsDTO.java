package com.moviemicroservice.analytics.DTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsDTO {
    private Long id;
    private Long movieId;
    private Double averageRating;
    private Long reviewCount;
    private LocalDateTime createdAt;
    private  LocalDateTime updatedAt;
}
