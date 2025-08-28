package com.moviemicroservice.movies.controllers;


import com.moviemicroservice.movies.DTOs.MovieDTO;
import com.moviemicroservice.movies.ReviewService.ReviewDTO;
import com.moviemicroservice.movies.Services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MoviesController {

    final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieDTO> addMovie(@RequestBody MovieDTO movie) {
        return ResponseEntity.ok(movieService.addMovie(movie));
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(movieService.getSingleMovie(id));
    }

    @PostMapping("/review")
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewDTO review) {
        return ResponseEntity.ok( movieService.addReview(review));
    }


    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable("id") Long id,  @RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok(movieService.updateMovie(movieDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie deleted successfully");
    }

}
