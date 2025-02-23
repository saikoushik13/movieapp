package com.example.movieapp.service;

import com.example.movieapp.model.Movie;
import com.example.movieapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie movieDetails) {
        return movieRepository.findById(id).map(movie -> {
            movie.setTitle(movieDetails.getTitle());
            movie.setDirector(movieDetails.getDirector());
            movie.setReleaseYear(movieDetails.getReleaseYear());
            movie.setGenre(movieDetails.getGenre());
            movie.setRating(movieDetails.getRating());
            return movieRepository.save(movie);
        }).orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
