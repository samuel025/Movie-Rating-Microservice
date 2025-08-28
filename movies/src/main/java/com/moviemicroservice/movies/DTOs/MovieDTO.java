package com.moviemicroservice.movies.DTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDTO {

    private Long id;

    private String title;

    private String genre;

    private String year;
}
