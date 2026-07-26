package com.absolutecinema.controller.admin;

import com.absolutecinema.model.Genre;
import com.absolutecinema.model.Movie;
import com.absolutecinema.model.MovieStatus;
import com.absolutecinema.repository.MovieRepository;
import com.absolutecinema.service.MovieService;
import javafx.collections.FXCollections;
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

public class AddMovieOverlayController {

    @FXML
    private ComboBox<Genre> cbxGenres;

    @FXML
    private TextField lblDuration;

    @FXML
    private TextField lblRating;

    @FXML
    private TextField lblScore;

    @FXML
    private TextArea lblSynopsis;

    @FXML
    private TextField lblTitle;

    @FXML
    private Label lblErrorMessage;

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

    private final MovieService movieService = new MovieService(new MovieRepository());

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

    @FXML
    void closeModal() {
        resetForm();
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

            String title = lblTitle.getText();
            String synopsis = lblSynopsis.getText();
            Genre genre = cbxGenres.getValue();
            int duration = Integer.parseInt(lblDuration.getText().trim());
            String rating = lblRating.getText();
            double score = Double.parseDouble(lblScore.getText().trim());

            MovieStatus movieStatus = rbShowing.isSelected() ? MovieStatus.NOW_SHOWING : MovieStatus.COMING_SOON;

            MovieRepository repo = new MovieRepository();
            String lastId = repo.getLastId();
            String newId = generateNextId(lastId);

            File posterFile = (selectedImagePath.isEmpty()) ? null : new File(selectedImagePath);
            File bannerFile = (selectedBannerPath.isEmpty()) ? null : new File(selectedBannerPath);

            String finalPosterPath = saveImageToProject(posterFile, newId, "images");
            String finalBannerPath = saveImageToProject(bannerFile, newId, "banners");

            Movie newMovie = new Movie(
                    newId,
                    title,
                    synopsis,
                    genre,
                    duration,
                    rating,
                    score,
                    finalPosterPath,
                    finalBannerPath,
                    movieStatus
            );

            repo.save(newMovie);
            resetForm();
            rootPane.setVisible(false);

        } catch (NumberFormatException e) {
            System.err.println("Error en formato de números (duración o score): " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al guardar la película: " + e.getMessage());
        }
    }

    private String saveImageToProject(File sourceFile, String movieId, String type) {
        if (sourceFile == null) return "";

        try {
            String folderPath = "src/main/resources/com/absolutecinema/images/portadas/" + type;
            File directory = new File(folderPath);

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

    private String generateNextId(String lastId) {
        if (lastId == null || !lastId.startsWith("MOV")) {
            return "MOV001";
        }
        int numericPart = Integer.parseInt(lastId.substring(3));
        return String.format("MOV%03d", numericPart + 1);
    }

    private void resetForm() {
        lblTitle.clear();
        lblSynopsis.clear();
        cbxGenres.setValue(null);
        lblDuration.clear();
        lblRating.clear();
        lblScore.clear();

        rbShowing.setSelected(true);

        selectedImagePath = "";
        selectedBannerPath = "";
    }
}