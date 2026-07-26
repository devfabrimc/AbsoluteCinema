package com.absolutecinema.controller.admin;

import com.absolutecinema.application.App;
import com.absolutecinema.model.Movie;
import com.absolutecinema.model.MovieStatus;
import com.absolutecinema.repository.MovieRepository;
import com.absolutecinema.repository.RoomRepository;
import com.absolutecinema.repository.ShowtimeRepository;
import com.absolutecinema.repository.UserRepository;
import com.absolutecinema.service.MovieService;
import com.absolutecinema.utils.DurationFormatter;
import com.absolutecinema.utils.GenreFormatter;
import com.absolutecinema.utils.Paths;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminMoviesController implements Initializable {
    @FXML
    private ComboBox<MovieStatus> cbxMovieStatus;

    @FXML
    private TextField txtSearch;

    @FXML
    private VBox vbxMovies;

    @FXML
    private AnchorPane addMovieOverlay;

    @FXML
    private AnchorPane editMovieOverlay;

    @FXML
    private AnchorPane deleteMovieOverlay;

    @FXML
    private EditMovieOverlayController editMovieOverlayController;

    @FXML
    private DeleteMovieOverlayController deleteMovieOverlayController;

    private final MovieService movieService = new MovieService(new MovieRepository());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMovies();
        loadCbx();
        txtSearch.setOnAction(event -> loadMovies());

        addMovieOverlay.visibleProperty().addListener((obs, wasVisible, isVisible) -> {
            if (wasVisible && !isVisible) {
                loadMovies();
            }
        });

        editMovieOverlay.visibleProperty().addListener((obs, wasVisible, isVisible) -> {
            if (wasVisible && !isVisible) {
                loadMovies();
            }
        });

        deleteMovieOverlay.visibleProperty().addListener((obs, wasVisible, isVisible) -> {
            if (wasVisible && !isVisible) {
                loadMovies();
            }
        });
    }

    private void loadMovies() {
        vbxMovies.getChildren().clear();
        vbxMovies.getChildren().add(createHeader());

        List<Movie> allMovies = movieService.getAllMovies();
        MovieStatus selectedStatus = cbxMovieStatus.getValue();
        String searchText = txtSearch.getText() != null ? txtSearch.getText().trim().toLowerCase() : "";

        for (Movie movie : allMovies) {
            if (selectedStatus != null && selectedStatus != MovieStatus.ALL && movie.getStatus() != selectedStatus) {
                continue;
            }

            if (!searchText.isEmpty() && !movie.getTitle().toLowerCase().contains(searchText)) {
                continue;
            }

            String statusLabel = "";
            switch (movie.getStatus()) {
                case NOW_SHOWING:
                    statusLabel = "EN CARTELERA";
                    break;
                case COMING_SOON:
                    statusLabel = "PRÓXIMAMENTE";
                    break;
                default:
                    statusLabel = "OTRO";
            }

            String genreLabel = GenreFormatter.format(movie.getGenre());
            String idLabel = "ID: " + movie.getId();
            String durationLabel = DurationFormatter.format(movie.getDurationMinutes());
            String scoreLabel = String.valueOf(movie.getScore());

            HBox row = addMovieRow(
                    movie.getTitle(),
                    idLabel,
                    genreLabel,
                    durationLabel,
                    statusLabel,
                    scoreLabel
            );

            vbxMovies.getChildren().add(row);
        }
    }

    private void loadCbx() {
        cbxMovieStatus.getItems().setAll(MovieStatus.values());

        cbxMovieStatus.setConverter(new StringConverter<>() {
            @Override
            public String toString(MovieStatus status) {
                if (status == null) return "";

                switch (status) {
                    case NOW_SHOWING:
                        return "EN CARTELERA";
                    case COMING_SOON:
                        return "PRÓXIMAMENTE";
                    default:
                        return "TODOS LOS ESTADOS";
                }
            }

            @Override
            public MovieStatus fromString(String string) {
                return null;
            }
        });

        cbxMovieStatus.getSelectionModel().selectFirst();

        cbxMovieStatus.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadMovies();
        });
    }

    @FXML
    void btnBackOnAction() {
        App.app.setTitle(" | Menú principal");
        App.app.setScene(Paths.MENU_VIEW);
    }

    private HBox createHeader() {
        HBox header = new HBox(30);
        header.setAlignment(Pos.CENTER_LEFT);

        header.setStyle("-fx-padding: 0 10 10 15;");

        Label lblName = new Label("NOMBRE");
        lblName.getStyleClass().add("table-header-text");
        lblName.setPrefWidth(220);

        Label lblGenre = new Label("GÉNERO");
        lblGenre.getStyleClass().add("table-header-text");
        lblGenre.setPrefWidth(120);

        Label lblDuration = new Label("DURACIÓN");
        lblDuration.getStyleClass().add("table-header-text");
        lblDuration.setPrefWidth(90);

        Label lblStatus = new Label("ESTADO");
        lblStatus.getStyleClass().add("table-header-text");
        lblStatus.setPrefWidth(110);

        Label lblScore = new Label("SCORE");
        lblScore.getStyleClass().add("table-header-text");
        lblScore.setPrefWidth(70);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox actionHeaderBox = new HBox();
        actionHeaderBox.setAlignment(Pos.CENTER_RIGHT);
        actionHeaderBox.setPrefWidth(130);

        Label lblActions = new Label("ACCIONES");
        lblActions.getStyleClass().add("table-header-text");
        actionHeaderBox.getChildren().add(lblActions);

        header.getChildren().addAll(lblName, lblGenre, lblDuration, lblStatus, lblScore, spacer, actionHeaderBox);
        return header;
    }

    private HBox addMovieRow(String title, String id, String genre, String duration, String status, String score) {
        HBox row = new HBox(30);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-padding: 12 10 12 15; -fx-border-color: transparent transparent #272a33 transparent; -fx-border-width: 0 0 1 0;");

        VBox titleBox = new VBox(3);
        Label lblMovieTitle = new Label(title);
        lblMovieTitle.setStyle("-fx-text-fill: #F5F1E8; -fx-font-weight: 900; -fx-font-size: 14px;");
        Label lblId = new Label(id);
        lblId.setStyle("-fx-text-fill: #888c96; -fx-font-size: 11px;");
        titleBox.getChildren().addAll(lblMovieTitle, lblId);
        titleBox.setPrefWidth(220);

        Label lblGenre = new Label(genre);
        lblGenre.setStyle("-fx-text-fill: #d1d5db; -fx-font-size: 13px;");
        lblGenre.setPrefWidth(120);

        Label lblDuration = new Label(duration);
        lblDuration.setStyle("-fx-text-fill: #d1d5db; -fx-font-size: 13px;");
        lblDuration.setPrefWidth(90);

        Label lblStatus = new Label(status);
        if (status.equalsIgnoreCase("EN CARTELERA")) {
            lblStatus.getStyleClass().add("badge-green");
        } else {
            lblStatus.getStyleClass().add("badge-blue");
        }
        lblStatus.setMaxWidth(Double.MAX_VALUE);

        Label lblScore = new Label(score);
        lblScore.setStyle("-fx-text-fill: #F5F1E8; -fx-font-weight: 900;");
        lblScore.setPrefWidth(70);
        lblScore.setPadding(new Insets(0, 0, 0, 15));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox actionBox = new HBox(10);
        actionBox.setAlignment(Pos.CENTER_RIGHT);
        Button btnEdit = new Button("Editar");
        btnEdit.getStyleClass().add("btn-action-edit");
        btnEdit.setOnAction(event -> {
            if (editMovieOverlayController != null) {
                // Limpiamos el texto para quedarnos solo con el código (ej. "MOV001")
                String cleanId = id.replace("ID: ", "").trim();
                editMovieOverlayController.setMovie(movieService.getMovieById(cleanId));
                editMovieOverlay.setVisible(true);
            }
        });
        Button btnDelete = new Button("Eliminar");
        btnDelete.getStyleClass().add("btn-action-delete");
        btnDelete.setOnAction(event -> {
            if (deleteMovieOverlayController != null) {
                String cleanId = id.replace("ID: ", "").trim();
                deleteMovieOverlayController.setMovie(movieService.getMovieById(cleanId));
                deleteMovieOverlay.setVisible(true);
            }
        });
        actionBox.getChildren().addAll(btnEdit, btnDelete);

        row.getChildren().addAll(titleBox, lblGenre, lblDuration, lblStatus, lblScore, spacer, actionBox);
        return row;
    }

    @FXML
    void addMovieOverlay() {
        addMovieOverlay.setVisible(true);
    }

    @FXML
    void openDashboardMenu() {
        App.app.setScene(Paths.ADMIN_VIEW);
        App.app.setTitle(" | Películas");
    }

    @FXML
    void openShowtimeMenu() {
        App.app.setScene(Paths.ADMIN_SHOWTIMES_VIEW);
        App.app.setTitle(" | Funciones");
    }
}