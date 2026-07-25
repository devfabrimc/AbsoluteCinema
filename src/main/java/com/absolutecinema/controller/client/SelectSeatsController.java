package com.absolutecinema.controller.client;


import com.absolutecinema.application.App;
import com.absolutecinema.model.Movie;
import com.absolutecinema.model.Room;
import com.absolutecinema.model.Showtime;
import com.absolutecinema.repository.MovieRepository;
import com.absolutecinema.repository.RoomRepository;
import com.absolutecinema.utils.Paths;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectSeatsController implements Initializable {

    @FXML private GridPane gridSeats;

    @FXML private Label lblDate;

    @FXML private Label lblFormat;

    @FXML private Label lblHour;

    @FXML private Label lblHeaderTitle;

    @FXML private Label lblName;

    @FXML private Label lblPrice;

    @FXML private Label lblSeats;

    @FXML private Label lblFormPrice;

    @FXML private Label lblCant;

    @FXML private Label lblCalculatedPrice;

    @FXML private Label lblErrorMessage;

    @FXML private AnchorPane CPOverlay;

    @FXML private ConfirmPurchaseOverlayController CPOverlayController;

    public static Showtime selectedShowtime;
    private Showtime showtime;
    private Movie movie;
    private Room room;
    private MovieRepository movieRepository;
    private RoomRepository roomRepository;

    private ToggleButton[][] seatButtons;
    private final List<String> selectedSeats = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showtime = selectedShowtime;
        movieRepository = new MovieRepository();
        roomRepository = new RoomRepository();

        movie = movieRepository.findById(showtime.getMovieId());
        room = roomRepository.findById(showtime.getRoomId());

        lblErrorMessage.setVisible(false);
        lblErrorMessage.setManaged(false);
        lblHeaderTitle.setText(movie.getTitle().toUpperCase());
        lblName.setText(room.getName());
        lblDate.setText(showtime.getDate());
        lblHour.setText(showtime.getTime());
        lblFormat.setText(showtime.getFormat().getDisplayName() + " - " + showtime.getLanguage());
        lblPrice.setText("$" + showtime.getPrice());
        lblFormPrice.setText(lblPrice.getText());

        loadSeats(room.getRows(), room.getColumns());
        updateSeatsLabel();
    }

    private void loadSeats(int rows, int columns) {
        gridSeats.getChildren().clear();
        gridSeats.getColumnConstraints().clear();
        gridSeats.getRowConstraints().clear();

        seatButtons = new ToggleButton[rows][columns];

        List<String> reservedSeats = showtime.getReservedSeats();

        for (int i = 0; i < rows; i++) {
            String rowLetter = String.valueOf((char) ('A' + i));

            for (int j = 0; j < columns; j++) {
                String seatLabel = rowLetter + (j + 1);

                ToggleButton seatButton = new ToggleButton(seatLabel);

                seatButton.setPrefSize(60, 40);
                seatButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                if (reservedSeats != null && reservedSeats.contains(seatLabel)) {
                    seatButton.setDisable(true);
                    seatButton.getStyleClass().add("seat-occupied");
                }

                seatButtons[i][j] = seatButton;
                gridSeats.add(seatButton, j, i);

                seatButton.setOnAction(event -> {
                    if (seatButton.isSelected()) {
                        selectedSeats.add(seatLabel);
                    } else {
                        selectedSeats.remove(seatLabel);
                    }

                    updateSeatsLabel();
                });
            }
        }
    }

    private void updateSeatsLabel() {
        if (selectedSeats.isEmpty()) {
            lblSeats.setText("Ninguno");
            lblCant.setText("0");
            lblCalculatedPrice.setText("$0.0");
            return;
        }

        String format = String.join(", ", selectedSeats);

        lblSeats.setText(format);
        lblCant.setText(String.valueOf(selectedSeats.size()));
        lblCalculatedPrice.setText("$" + (showtime.getPrice() * selectedSeats.size()));
    }

    @FXML void btnBackOnAction() {
        App.app.setScene(Paths.MOVIE_DETAILS_VIEW);
        App.app.setTitle(" | " + movieRepository.findById(selectedShowtime.getMovieId()).getTitle());
    }

    @FXML void btnContinueOnAction() {
        if (selectedSeats.isEmpty()) {
            lblErrorMessage.setText("Seleccione al menos un asiento.");
            lblErrorMessage.setVisible(true);
            lblErrorMessage.setManaged(true);
            return;
        }

        lblErrorMessage.setVisible(false);
        lblErrorMessage.setManaged(false);

        CPOverlayController.setData(movie, room, showtime, selectedSeats);

        CPOverlay.setVisible(true);
    }
}