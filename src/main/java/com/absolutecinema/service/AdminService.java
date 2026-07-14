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

    public void updateShowtime(Showtime showtime) {}

    public void deleteShowtime(String showtimeId) {}

    public List<Movie> getAllMovies() {
        return Collections.emptyList();
    }

    public List<Showtime> getAllShowtimes() {
        return Collections.emptyList();
    }

    public List<Room> getAllRooms() {
        return Collections.emptyList();
    }
}