package com.absolutecinema.service;

import com.absolutecinema.model.Movie;
import com.absolutecinema.model.Genre;
import com.absolutecinema.model.MovieStatus;
import com.absolutecinema.repository.MovieRepository;

import java.util.List;

public class MovieService {

    private MovieRepository movieRepository;
    //Constructor con inyección de dependencias
    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    //Metodo que devuelve la lista de películas en cartelera
    public List<Movie> getNowShowing(){
        return movieRepository.findByStatus(MovieStatus.NOW_SHOWING);
    }

    //Metodo que devuelve la lista de películas a estrenarse
    public List<Movie> getComingSoon(){
        return movieRepository.findByStatus(MovieStatus.COMING_SOON);
    }

    //Metodo que busca una película por el ID
    public Movie getMovieById(String id){
        return movieRepository.findById(id);
    }

    //Metodo que devuelve una lista con todas las películas
    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public List<Movie> filterMovies(List<Movie> sourceMovies, String title, Genre genre) {
        if (sourceMovies == null || sourceMovies.isEmpty()) {
            return List.of();
        }

        return sourceMovies.stream()
                .filter(movie -> {
                    if (title != null && !title.isBlank()) {
                        String search = title.trim().toLowerCase();
                        if (!movie.getTitle().toLowerCase().contains(search)) {
                            return false;
                        }
                    }
                    if (genre != null && genre != Genre.TODOS) {
                        if (movie.getGenre() != genre) {
                            return false;
                        }
                    }
                    return true;
                })
                .toList();
    }

}
