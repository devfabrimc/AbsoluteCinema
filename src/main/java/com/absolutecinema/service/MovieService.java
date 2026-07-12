package com.absolutecinema.service;

import com.absolutecinema.model.Movie;
import com.absolutecinema.repository.MovieRepository;

import java.util.Collections;
import java.util.List;

public class MovieService {

    private MovieRepository movieRepository;

    //Metodo que devuelve la lista de películas en cartelera
    public List<Movie> getNowShowing(){
        return Collections.emptyList();
    }
    //Metodo que devuelve la lista de películas a estrenarse
    public List<Movie> getComingSoon(){
        return Collections.emptyList();
    }
    //Metodo que busca una película por el nombre
    public List<Movie> searchByTitle(String text){
        return Collections.emptyList();
    }
    //Metodo que busca una película por el id
    public Movie getMovieById(String id){
        return null;
    }
    //Metodo que devuelve una lista con todas las películas
    public List<Movie> getAllMovies(){
        return Collections.emptyList();
    }

}
