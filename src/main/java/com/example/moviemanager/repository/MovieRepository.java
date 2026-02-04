package com.example.moviemanager.repository;

import com.example.moviemanager.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MovieRepository extends JpaRepository<Movie, Integer>{
}
