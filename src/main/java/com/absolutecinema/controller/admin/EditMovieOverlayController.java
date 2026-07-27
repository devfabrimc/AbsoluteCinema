package com.absolutecinema.controller.admin;

import com.absolutecinema.model.Genre;
import com.absolutecinema.model.Movie;
import com.absolutecinema.model.MovieStatus;
import com.absolutecinema.repository.MovieRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class EditMovieOverlayController {

    @FXML
    private ComboBox<Genre> cbxGenres;

    @FXML
    private TextField lblDuration;

    @FXML
    private Label lblErrorMessage;

    @FXML
    private TextField lblRating;

    @FXML
    private TextField lblScore;

    @FXML
    private TextArea lblSynopsis;

    @FXML
    private TextField lblTitle;

    @FXML
    private RadioButton rbComingSoon;

    @FXML
    private RadioButton rbShowing;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ToggleGroup status;

    @FXML
    private VBox uploadBoxBanner;

    @FXML
    private VBox uploadBoxImage;

    private Movie currentMovie;
    private final MovieRepository movieRepository = new MovieRepository();

    private String selectedImagePath = "";
    private String selectedBannerPath = "";

    @FXML
    public void initialize() {
        cbxGenres.setItems(FXCollections.observableArrayList(Genre.values()));

        uploadBoxImage.setOnMouseClicked(event -> {
            File file = chooseImage("Seleccionar Imagen de Póster");
            if (file != null) {
                selectedImagePath = file.getAbsolutePath();
            }
        });

        uploadBoxBanner.setOnMouseClicked(event -> {
            File file = chooseImage("Seleccionar Imagen del Banner");
            if (file != null) {
                selectedBannerPath = file.getAbsolutePath();
            }
        });
    }

    private File chooseImage(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg")
        );
        return fileChooser.showOpenDialog(rootPane.getScene().getWindow());
    }

    public void setMovie(Movie movie) {
        this.currentMovie = movie;

        lblTitle.setText(movie.getTitle());
        lblSynopsis.setText(movie.getSynopsis());
        cbxGenres.setValue(movie.getGenre());
        lblDuration.setText(String.valueOf(movie.getDurationMinutes()));
        lblRating.setText(movie.getRating());
        lblScore.setText(String.valueOf(movie.getScore()));

        if (movie.getStatus() == MovieStatus.NOW_SHOWING) {
            rbShowing.setSelected(true);
        } else {
            rbComingSoon.setSelected(true);
        }

        selectedImagePath = "";
        selectedBannerPath = "";
    }

    @FXML
    void closeModal() {
        rootPane.setVisible(false);
    }

    @FXML
    void saveMovie() {
        try {
            if (lblTitle.getText().isBlank() || lblSynopsis.getText().isBlank() ||
                    cbxGenres.getValue() == null || lblDuration.getText().isBlank() ||
                    lblRating.getText().isBlank() || lblScore.getText().isBlank()) {

                lblErrorMessage.setManaged(true);
                lblErrorMessage.setVisible(true);
                lblErrorMessage.setText("Todos los campos son obligatorios.");
                return;
            }

            currentMovie.setTitle(lblTitle.getText().trim());
            currentMovie.setSynopsis(lblSynopsis.getText().trim());
            currentMovie.setGenre(cbxGenres.getValue());
            currentMovie.setDurationMinutes(Integer.parseInt(lblDuration.getText().trim()));
            currentMovie.setRating(lblRating.getText().trim());
            currentMovie.setScore(Double.parseDouble(lblScore.getText().trim()));
            currentMovie.setStatus(rbShowing.isSelected() ? MovieStatus.NOW_SHOWING : MovieStatus.COMING_SOON);

            if (!selectedImagePath.isEmpty()) {
                File posterFile = new File(selectedImagePath);
                String finalPosterPath = saveImageToProject(posterFile, currentMovie.getId(), "images");
                currentMovie.setImagePath(finalPosterPath);
            }

            if (!selectedBannerPath.isEmpty()) {
                File bannerFile = new File(selectedBannerPath);
                String finalBannerPath = saveImageToProject(bannerFile, currentMovie.getId(), "banners");
                currentMovie.setBannerPath(finalBannerPath);
            }

            movieRepository.update(currentMovie);

            lblErrorMessage.setVisible(false);
            lblErrorMessage.setManaged(false);
            rootPane.setVisible(false);

        } catch (NumberFormatException e) {
            lblErrorMessage.setManaged(true);
            lblErrorMessage.setVisible(true);
            lblErrorMessage.setText("Duración y puntuación deben ser numéricos.");
        } catch (Exception e) {
            lblErrorMessage.setManaged(true);
            lblErrorMessage.setVisible(true);
            lblErrorMessage.setText("Error al actualizar la película.");
            System.err.println(e.getMessage());
        }
    }

    private String saveImageToProject(File sourceFile, String movieId, String type) {
        if (sourceFile == null) return "";

        try {
            String folderPath = "src/main/resources/com/absolutecinema/images/portadas/" + type;

            String fileName = sourceFile.getName();
            String extension = fileName.substring(fileName.lastIndexOf("."));
            String newFileName = movieId + "_" + type + extension;

            Path destinationPath = Path.of(folderPath, newFileName);
            Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            return "portadas/" + type + "/" + newFileName;

        } catch (IOException e) {
            System.err.println("Error al guardar la imagen: " + e.getMessage());
            return "";
        }
    }
}