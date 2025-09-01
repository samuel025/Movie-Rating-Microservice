package com.moviemicroservice.analytics.Services;

import com.moviemicroservice.analytics.DTOs.AnalyticsDTO;
import org.springframework.stereotype.Service;

import java.util.List;




public interface AnalyticsService {
     List<AnalyticsDTO> getAllAnalytics();
     AnalyticsDTO getAnalyticsById(Long id);
}
