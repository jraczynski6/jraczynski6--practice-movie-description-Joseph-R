package com.example.moviemanager.controller;

import com.example.moviemanager.model.Movie;
import com.example.moviemanager.model.MovieInfo;
import com.example.moviemanager.repository.MovieRepository;
import com.example.moviemanager.service.MovieInfoService;
import org.apache.http.HttpException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieRepository movieRepository;
    private final MovieInfoService movieInfoService;

    public MovieController(MovieRepository movieRepository, MovieInfoService movieInfoService) {
        this.movieRepository = movieRepository;
        this.movieInfoService = movieInfoService;
    }

    @GetMapping
    public List<Movie> getAllItems() {
        return movieRepository.findAll();
    }

    @PostMapping
    public Movie addItem(@RequestBody Movie movie) throws IOException {
        // Get movie info from the AI service
        MovieInfo movieInfo = movieInfoService.getMovieInfo(movie.getTitle());

        movie.setDescription(movieInfo.getDescription());
        movie.setDirector(movieInfo.getDirector());

        return movieRepository.save(movie);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable int id) {
        movieRepository.deleteById(id);
    }
}
