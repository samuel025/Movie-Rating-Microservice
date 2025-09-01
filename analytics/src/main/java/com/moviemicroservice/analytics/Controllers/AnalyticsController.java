package com.moviemicroservice.analytics.Controllers;


import com.moviemicroservice.analytics.DTOs.AnalyticsDTO;
import com.moviemicroservice.analytics.Services.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping
    public ResponseEntity<List<AnalyticsDTO>> getAllAnalytics() {
        return ResponseEntity.ok(analyticsService.getAllAnalytics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalyticsDTO> getAnalyticsById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(analyticsService.getAnalyticsById(id));
    }
}
