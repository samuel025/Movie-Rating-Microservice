package com.moviemicroservice.analytics.Repositories;

import com.moviemicroservice.analytics.Entities.MovieAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieAnalyticsRepository extends JpaRepository<MovieAnalytics, Long> {
    List<MovieAnalytics> findByMovieId(long movieId);
    MovieAnalytics findFirstByMovieId(long movieId);
    void deleteAllByMovieId(Long id);
}
