package com.absolutecinema.repository;

import com.absolutecinema.model.Genre;
import com.absolutecinema.model.Movie;
import com.absolutecinema.model.MovieStatus;
import com.absolutecinema.utils.TxtFileManager;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository implements Repository<Movie> {
    // Declaramos la ruta del archivo que se encuentran las películas
    private static final String filePath = "resources/data/movies.txt";

    private final TxtFileManager fileManager = new TxtFileManager();

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();

        for (String line : fileManager.readLines(filePath)) {
            movies.add(Movie.printformat(line));
        }

        return movies;
    }

    @Override
    public Movie findById(String id) {
        for (Movie movie : findAll()) {
            if (movie.getId().equals(id)) {
                return movie;
            }
        }
        return null;
    }

    @Override
    public void save(Movie movie) {
        fileManager.appendLine(filePath, movie.toString());
    }

    @Override
    public void update(Movie movie) {
        List<Movie> movies = findAll();
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getId().equals(movie.getId())) {
                movies.set(i, movie);
                break;
            }
        }

        writeAll(movies);
    }

    @Override
    public void delete(String id) {
        List<Movie> movies = findAll();
        movies.removeIf(movie -> movie.getId().equals(id));

        writeAll(movies);
    }

    public List<Movie> findByStatus(MovieStatus status) {
        List<Movie> result = new ArrayList<>();

        for (Movie movie : findAll()) {
            if (movie.getStatus().equals(status)) {
                result.add(movie);
            }
        }

        return result;
    }

    public List<Movie> findByTitle(String title) {
        List<Movie> result = new ArrayList<>();

        for (Movie movie : findAll()) {
            if (movie.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(movie);
            }
        }

        return result;
    }

    public List<Movie> findByGenre(Genre genre) {
        List<Movie> result = new ArrayList<>();

        for (Movie movie : findAll()) {
            if (movie.getGenre().equals(genre)) {
                result.add(movie);
            }
        }

        return result;
    }

    public String getLastId() {
        List<Movie> movies = findAll();

        if (movies.isEmpty()) {
            return "MOV000";
        }
        return movies.get(movies.size() - 1).getId();
    }

    private void writeAll(List<Movie> movies) {
        List<String> lines = new ArrayList<>();

        for (Movie movie : movies) {
            lines.add(movie.toString());
        }

        fileManager.writeLines(filePath, lines);
    }
}
