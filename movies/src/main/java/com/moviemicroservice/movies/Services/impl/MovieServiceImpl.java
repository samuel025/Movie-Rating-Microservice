package com.moviemicroservice.movies.Services.impl;

import com.moviemicroservice.movies.DTOs.MovieDTO;
import com.moviemicroservice.movies.Exceptions.CannotBeNull;
import com.moviemicroservice.movies.Exceptions.ResourceNotFound;
import com.moviemicroservice.movies.Mappers.MovieMapper;
import com.moviemicroservice.movies.Repositories.MovieRepository;
import com.moviemicroservice.movies.ReviewService.ReviewClient;
import com.moviemicroservice.movies.ReviewService.ReviewDTO;
import com.moviemicroservice.movies.Services.MovieService;
import com.moviemicroservice.movies.entities.Movie;
import com.moviemicroservice.movies.kafka.MovieProducer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieProducer movieProducer;
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final ReviewClient reviewClient;
    private final HttpServletRequest request;

    @Override
    public MovieDTO addMovie(MovieDTO movieDTO) {
        if(movieDTO.getTitle() == null || movieDTO.getGenre() == null || movieDTO.getYear() == null) {
            throw new CannotBeNull("Movie title, genre, and year cannot be null");
        }
        Movie savedMovie =  movieRepository.save(movieMapper.toEntity(movieDTO));
        return movieMapper.toDTO(savedMovie);
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(movieMapper::toDTO).toList();
    }

    @Override
    public MovieDTO updateMovie(MovieDTO movieDTO, Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Movie not found"));
        updateMovieFields(movie, movieDTO);
        Movie savedMovie = movieRepository.save(movie);
        return movieMapper.toDTO(savedMovie);
    }

    @Override
    public void deleteMovie(Long movieId) {
        movieRepository.deleteById(movieId);
        String id  = String.valueOf(movieId);
        movieProducer.sendMovieDeletedTopic(id);
    }

    @Override
    public MovieDTO getSingleMovie(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Movie not found"));
        return movieMapper.toDTO(movie);
    }

    @Override
    public ReviewDTO addReview(ReviewDTO reviewDTO) {
        String authHeader = request.getHeader("Authorization");
        return reviewClient.createReview(reviewDTO, authHeader);
    }


    public void updateMovieFields(Movie movie, MovieDTO movieDTO) {
        Optional.ofNullable(movieDTO.getTitle()).ifPresent(movie::setTitle);
        Optional.ofNullable(movieDTO.getGenre()).ifPresent(movie::setGenre);
        Optional.ofNullable(movieDTO.getYear()).ifPresent(movie::setYear);
    }
}
