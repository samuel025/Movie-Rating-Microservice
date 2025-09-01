package com.moviemicroservice.analytics.Services.impl;

import com.moviemicroservice.analytics.DTOs.AnalyticsDTO;
import com.moviemicroservice.analytics.Mapper.MovieAnalyticsMapper;
import com.moviemicroservice.analytics.Repositories.MovieAnalyticsRepository;
import com.moviemicroservice.analytics.Services.AnalyticsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final MovieAnalyticsRepository movieAnalyticsRepository;
    private final MovieAnalyticsMapper movieAnalyticsMapper;

    @Override
    public List<AnalyticsDTO> getAllAnalytics() {
        return movieAnalyticsRepository.findAll().stream().map(movieAnalyticsMapper::toDTO).toList();
    }

    @Override
    public AnalyticsDTO getAnalyticsById(Long id) {
        return movieAnalyticsMapper.toDTO(movieAnalyticsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Analytics not ")));
    }


}
