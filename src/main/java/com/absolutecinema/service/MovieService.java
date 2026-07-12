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

    //Metodo que busca películas por el nombre
    public List<Movie> searchByTitle(String title){
        return movieRepository.findByTitle(title);
    }

    //Metodo que busca películas por el género
    public List<Movie> filterByGenre(Genre genre){
        return movieRepository.findByGenre(genre);
    }

    //Metodo que busca una película por el ID
    public Movie getMovieById(String id){
        return movieRepository.findById(id);
    }

    //Metodo que devuelve una lista con todas las películas
    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

}
