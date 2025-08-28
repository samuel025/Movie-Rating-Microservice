package com.moviemicroservice.movies.Mappers;


import com.moviemicroservice.movies.DTOs.MovieDTO;
import com.moviemicroservice.movies.entities.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    public Movie toEntity(MovieDTO movieDTO) {
        if (movieDTO == null) {
            return null;
        }

        Movie movie = new Movie();
        movie.setId(movieDTO.getId());
        movie.setTitle(movieDTO.getTitle());
        movie.setGenre(movieDTO.getGenre());
        movie.setYear(movieDTO.getYear());
        return movie;
    }

    public  MovieDTO toDTO(Movie movie) {
        if (movie == null) {
            return null;
        }

        return MovieDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .year(movie.getYear())
                .build();
    }
}
