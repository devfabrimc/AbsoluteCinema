package com.absolutecinema.controller.admin;

import com.absolutecinema.model.Movie;
import com.absolutecinema.repository.MovieRepository;
import com.absolutecinema.utils.DurationFormatter;
import com.absolutecinema.utils.GenreFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.nio.file.Files;
import java.nio.file.Path;

public class DeleteMovieOverlayController {

    @FXML
    private Label lblDuration;

    @FXML
    private Label lblErrorMessage;

    @FXML
    private Label lblGenre;

    @FXML
    private Label lblScore;

    @FXML
    private Label lblTitle;

    @FXML
    private AnchorPane rootPane;

    private Movie currentMovie;
    private final MovieRepository movieRepository = new MovieRepository();

    public void setMovie(Movie movie) {
        this.currentMovie = movie;
        lblTitle.setText(movie.getTitle());
        lblDuration.setText(DurationFormatter.format(movie.getDurationMinutes()));
        lblGenre.setText(GenreFormatter.format(movie.getGenre()));
        lblScore.setText(String.valueOf(movie.getScore()));
    }

    @FXML
    void closeModal() {
        rootPane.setVisible(false);
    }

    @FXML
    void confirmDelete() {
        if (currentMovie != null) {
            deleteAssociatedImages();

            movieRepository.delete(currentMovie.getId());
        }
        rootPane.setVisible(false);
    }

    private void deleteAssociatedImages() {
        try {
            if (currentMovie.getImagePath() != null && !currentMovie.getImagePath().isBlank()) {
                Path imagePath = Path.of("src/main/resources/com/absolutecinema/images", currentMovie.getImagePath());
                Files.deleteIfExists(imagePath);
            }

            if (currentMovie.getBannerPath() != null && !currentMovie.getBannerPath().isBlank()) {
                Path bannerPath = Path.of("src/main/resources/com/absolutecinema/images", currentMovie.getBannerPath());
                Files.deleteIfExists(bannerPath);
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar los archivos de imagen asociados: " + e.getMessage());
        }
    }
}