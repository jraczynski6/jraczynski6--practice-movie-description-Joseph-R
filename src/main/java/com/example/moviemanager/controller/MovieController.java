package com.example.moviemanager.controller;

import com.example.moviemanager.model.Movie;
import com.example.moviemanager.repository.MovieRepository;
import com.example.moviemanager.service.GeminiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieRepository movieRepository;
    private final GeminiService geminiService;

    // GeminiService
    public MovieController(MovieRepository movieRepository, GeminiService geminiService) {
        this.movieRepository = movieRepository;
        this.geminiService = geminiService;
    }

    @PostMapping
    public Movie addMovie(@RequestParam String title, @RequestParam Double rating) {

        String description = geminiService.generateDescription(title);

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setRating(rating);
        movie.setDescription(description);

        return movieRepository.save(movie);
    }

    @GetMapping
    public List<Movie> getAllMovie() {
        return movieRepository.findAll();
    }
}
