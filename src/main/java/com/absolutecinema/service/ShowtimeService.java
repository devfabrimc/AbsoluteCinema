package com.absolutecinema.service;

import com.absolutecinema.model.Showtime;
import com.absolutecinema.repository.MovieRepository;
import com.absolutecinema.repository.RoomRepository;
import com.absolutecinema.repository.ShowtimeRepository;

import java.util.List;

public class ShowtimeService {

    private ShowtimeRepository showtimeRepository;
    private MovieRepository movieRepository;
    private RoomRepository roomRepository;

    //Constructor con inyección de dependencias
    public ShowtimeService(MovieRepository movieRepository,
                           RoomRepository roomRepository,
                           ShowtimeRepository showtimeRepository){
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.showtimeRepository = showtimeRepository;
    }

    //Metodo que devuelve el showtime por movieId
    public List<Showtime> getShowtimeByMovie (String movieId){
        return showtimeRepository.findByMovieId(movieId);
    }
}