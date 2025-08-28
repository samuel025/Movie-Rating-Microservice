package com.moviemicroservice.reviews.Repositories;


import com.moviemicroservice.reviews.Entites.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Long> {
    Reviews findByMovieIdAndUserId(Long movieId, Long userId);

    List<Reviews> findByMovieId(Long id);
}
