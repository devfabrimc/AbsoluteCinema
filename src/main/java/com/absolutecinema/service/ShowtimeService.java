package com.absolutecinema.service;

import com.absolutecinema.model.Showtime;

import java.util.Collections;
import java.util.List;

public class ShowtimeService {

    private ShowtimeRepository showtimeRepository;
    private MovieRepository movieRepository;
    private RoomRepository roomRepository;

    //Metodo que devuelve el showtime por película
    public List<Showtime> getShowtimeByMovie (String movieId){
        return Collections.emptyList();
    }
    //Metodo que devuelve el showtime por ID
    public Showtime getShowtimeById (String id){
        return null;
    }
    //Metodo que devuelve el showtime por fecha
    public List<Showtime> getShowtimeByDate (String date){
        return Collections.emptyList();
    }


}