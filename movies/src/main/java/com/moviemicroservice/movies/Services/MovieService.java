package com.moviemicroservice.movies.Services;

import com.moviemicroservice.movies.DTOs.MovieDTO;
import com.moviemicroservice.movies.ReviewService.ReviewDTO;

import java.util.List;

public interface MovieService {
     MovieDTO addMovie(MovieDTO movie);
     List<MovieDTO> getAllMovies();
     MovieDTO updateMovie(MovieDTO movieDTO, Long id);
    void deleteMovie(Long id);
    MovieDTO getSingleMovie(Long id);
    ReviewDTO addReview(ReviewDTO reviewDTO);
}
