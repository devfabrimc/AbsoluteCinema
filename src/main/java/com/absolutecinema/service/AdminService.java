package com.absolutecinema.service;

import com.absolutecinema.model.Movie;
import com.absolutecinema.model.Showtime;
import com.absolutecinema.model.Room;
import com.absolutecinema.repository.MovieRepository;
import com.absolutecinema.repository.RoomRepository;
import com.absolutecinema.repository.ShowtimeRepository;

import java.util.Collections;
import java.util.List;

public class AdminService {
    private MovieRepository movieRepository;
    private ShowtimeRepository showtimeRepository;
    private RoomRepository roomRepository;
    // Constructor con inyección de dependencias
    public AdminService(MovieRepository movieRepository,
                        ShowtimeRepository showtimeRepository,
                        RoomRepository roomRepository) {
        this.movieRepository = movieRepository;
        this.showtimeRepository = showtimeRepository;
        this.roomRepository = roomRepository;
    }
    //Metodo para agregar una pelicula
    public void addMovie(Movie movie) {
        movieRepository.save(movie);
    }

    //Metodo para modificar una película
    public void updateMovie(Movie movie) {
        movieRepository.update(movie);
    }
    //Metodo para eliminar una película
    public void deleteMovie(String movieId) {
        movieRepository.delete(movieId);
    }
    //Metodo para agregar un showtime
    public void addShowtime(Showtime showtime) {
        showtimeRepository.save(showtime);
    }


    //Metodo para modificar un showtime
    public void updateShowtime(Showtime showtime) {
        showtimeRepository.update(showtime);
    }
    //Metodo para eliminar un showtime
    public void deleteShowtime(String showtimeId) {
        showtimeRepository.delete(showtimeId);
    }

    //Metodo para obtener todas las películas
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    //Metodo para obtener todos los showtimes
    public List<Showtime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }
    //Metodo para obtener todas las salas
    public List<Room> getAllRooms() {
        return  roomRepository.findAll();
    }
}