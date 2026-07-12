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

    public void addMovie(Movie movie) {}

    public void updateMovie(Movie movie) {}

    public void deleteMovie(String movieId) {}

    public void addShowtime(Object showtime) {}

    public void updateShowtime(Object showtime) {}

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