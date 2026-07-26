package com.absolutecinema.controller.admin;

import com.absolutecinema.application.App;
import com.absolutecinema.model.Movie;
import com.absolutecinema.model.Showtime;
import com.absolutecinema.repository.MovieRepository;
import com.absolutecinema.repository.ShowtimeRepository;
import com.absolutecinema.service.MovieService;
import com.absolutecinema.utils.Paths;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminShowtimesController implements Initializable {

    @FXML
    private TextField txtSearch;

    @FXML
    private VBox vbxShowtimes;

    @FXML
    private AnchorPane addShowtimeOverlay;

    @FXML
    private AnchorPane editShowtimeOverlay;

    @FXML
    private AnchorPane deleteShowtimeOverlay;

    @FXML
    private EditShowtimeOverlayController editShowtimeOverlayController;

    @FXML
    private DeleteShowtimeOverlayController deleteShowtimeOverlayController;

    private final MovieService movieService = new MovieService(new MovieRepository());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadShowtimes();

        txtSearch.setOnAction(event -> loadShowtimes());

        addShowtimeOverlay.visibleProperty().addListener((obs, wasVisible, isVisible) -> {
            if (wasVisible && !isVisible) loadShowtimes();
        });
        editShowtimeOverlay.visibleProperty().addListener((obs, wasVisible, isVisible) -> {
            if (wasVisible && !isVisible) loadShowtimes();
        });
        deleteShowtimeOverlay.visibleProperty().addListener((obs, wasVisible, isVisible) -> {
            if (wasVisible && !isVisible) loadShowtimes();
        });
    }

    private void loadShowtimes() {
        vbxShowtimes.getChildren().clear();
        vbxShowtimes.setSpacing(15);

        List<Showtime> allShowtimes = new ShowtimeRepository().findAll();
        String searchText = txtSearch.getText() != null ? txtSearch.getText().trim().toLowerCase() : "";

        for (Showtime showtime : allShowtimes) {
            Movie movie = movieService.getMovieById(showtime.getMovieId());
            String movieTitle = (movie != null) ? movie.getTitle().toLowerCase() : "";

            if (!searchText.isEmpty() && !movieTitle.contains(searchText)) {
                continue;
            }

            HBox card = createShowtimeCard(showtime, movie);
            vbxShowtimes.getChildren().add(card);
        }
    }

    private HBox createShowtimeCard(Showtime showtime, Movie movie) {
        HBox card = new HBox(20);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle("-fx-background-color: rgb(255,255,255,0.02); -fx-background-radius: 8; -fx-padding: 15; -fx-border-color: #272a33; -fx-border-radius: 8;");

        String displayTitle = (movie != null) ? movie.getTitle() : "Película Desconocida";

        VBox detailsBox = new VBox(8);
        detailsBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(detailsBox, Priority.ALWAYS);

        Label lblTitle = new Label(displayTitle);
        lblTitle.setStyle("-fx-text-fill: #D6A85A; -fx-font-weight: 900; -fx-font-size: 16px;");

        HBox infoRow1 = new HBox(15);
        Label lblRoom = new Label("ID: " + showtime.getId());
        lblRoom.setStyle("-fx-text-fill: #888c96; -fx-font-size: 12px;");

        Label lblDate = new Label(showtime.getDate());
        lblDate.setStyle("-fx-text-fill: #888c96; -fx-font-size: 12px;");

        Label lblTime = new Label(showtime.getTime());
        lblTime.setStyle("-fx-text-fill: #888c96; -fx-font-size: 12px;");
        infoRow1.getChildren().addAll(lblRoom, lblDate, lblTime);

        Label lblFormat = new Label(showtime.getFormat().getDisplayName() + " • " + showtime.getLanguage().name());
        lblFormat.setStyle("-fx-text-fill: #888c96; -fx-font-size: 12px;");

        Label lblPrice = new Label(String.format("$%.2f", showtime.getPrice()));
        lblPrice.setStyle("-fx-text-fill: #F5F1E8; -fx-font-weight: bold; -fx-font-size: 14px;");

        detailsBox.getChildren().addAll(lblTitle, infoRow1, lblFormat, lblPrice);

        VBox actionBox = new VBox();
        actionBox.setAlignment(Pos.CENTER_RIGHT);

        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);

        Button btnEdit = new Button("Editar");
        btnEdit.getStyleClass().add("btn-action-edit");
        btnEdit.setOnAction(e -> {
            if (editShowtimeOverlayController != null) {
                editShowtimeOverlayController.setShowtime(showtime);
                editShowtimeOverlay.setVisible(true);
            }
        });

        Button btnDelete = new Button("Eliminar");
        btnDelete.getStyleClass().add("btn-action-delete");
        btnDelete.setOnAction(e -> {
            if (deleteShowtimeOverlayController != null) {
                deleteShowtimeOverlayController.setShowtime(showtime);
                deleteShowtimeOverlay.setVisible(true);
            }
        });

        buttonsBox.getChildren().addAll(btnEdit, btnDelete);
        actionBox.getChildren().add(buttonsBox);

        card.getChildren().addAll(detailsBox, actionBox);
        return card;
    }

    @FXML
    void addShowtimeOverlay() {
        addShowtimeOverlay.setVisible(true);
    }

    @FXML
    void openDashboardMenu() {
        App.app.setScene(Paths.ADMIN_VIEW);
        App.app.setTitle(" | Dashboard");
    }

    @FXML
    void openMovieMenu() {
        App.app.setScene(Paths.ADMIN_MOVIES_VIEW);
        App.app.setTitle(" | Películas");
    }

    @FXML
    void btnBackOnAction() {
        App.app.setTitle(" | Menú principal");
        App.app.setScene(Paths.MENU_VIEW);
    }
}