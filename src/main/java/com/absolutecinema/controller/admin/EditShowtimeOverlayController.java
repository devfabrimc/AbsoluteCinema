package com.absolutecinema.controller.admin;

import com.absolutecinema.model.*;
import com.absolutecinema.repository.MovieRepository;
import com.absolutecinema.repository.RoomRepository;
import com.absolutecinema.repository.ShowtimeRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EditShowtimeOverlayController {

    @FXML
    private ComboBox<Format> cbxFormat;

    @FXML
    private ComboBox<Language> cbxLanguage;

    @FXML
    private ComboBox<Movie> cbxMovies;

    @FXML
    private ComboBox<Room> cbxRooms;

    @FXML
    private DatePicker dpShowtimeDate;

    @FXML
    private Label lblErrorMessage;

    @FXML
    private TextField lblHour;

    @FXML
    private TextField lblPrice;

    @FXML
    private AnchorPane rootPane;

    private Showtime currentShowtime;
    private final ShowtimeRepository showtimeRepository = new ShowtimeRepository();
    private final MovieRepository movieRepository = new MovieRepository();
    private final RoomRepository roomRepository = new RoomRepository();

    @FXML
    public void initialize() {
        cbxFormat.setItems(FXCollections.observableArrayList(Format.values()));
        cbxLanguage.setItems(FXCollections.observableArrayList(Language.values()));

        List<Movie> movies = movieRepository.findAll();
        List<Room> rooms = roomRepository.findAll();

        cbxMovies.setItems(FXCollections.observableArrayList(movies));
        cbxRooms.setItems(FXCollections.observableArrayList(rooms));

        setupMovieComboBox();
        setupRoomComboBox();

        dpShowtimeDate.setOnAction(event -> {
        });

        dpShowtimeDate.getEditor().setOnMouseClicked(event -> {
            if (!dpShowtimeDate.isShowing()) {
                dpShowtimeDate.show();
            }
        });
    }

    public void setShowtime(Showtime showtime) {
        this.currentShowtime = showtime;

        lblHour.setText(showtime.getTime());
        lblPrice.setText(String.valueOf(showtime.getPrice()));

        cbxFormat.setValue(showtime.getFormat());
        cbxLanguage.setValue(showtime.getLanguage());

        try {
            if (showtime.getDate() != null) {
                LocalDate date;
                if (showtime.getDate().contains("-")) {
                    date = LocalDate.parse(showtime.getDate());
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    date = LocalDate.parse(showtime.getDate(), formatter);
                }
                dpShowtimeDate.setValue(date);
            } else {
                dpShowtimeDate.setValue(null);
            }
        } catch (Exception e) {
            dpShowtimeDate.setValue(null);
            System.err.println("Error al parsear la fecha: " + e.getMessage());
        }

        for (Movie movie : cbxMovies.getItems()) {
            if (movie.getId().equals(showtime.getMovieId())) {
                cbxMovies.setValue(movie);
                break;
            }
        }

        for (Room room : cbxRooms.getItems()) {
            if (room.getId().equals(showtime.getRoomId())) {
                cbxRooms.setValue(room);
                break;
            }
        }
    }

    @FXML
    void closeModal() {
        rootPane.setVisible(false);
        lblErrorMessage.setVisible(false);
        lblErrorMessage.setManaged(false);
    }

    @FXML
    void saveShowtime() {
        try {
            if (cbxMovies.getValue() == null || cbxRooms.getValue() == null ||
                    dpShowtimeDate.getValue() == null || lblHour.getText().isBlank() ||
                    lblPrice.getText().isBlank() || cbxFormat.getValue() == null ||
                    cbxLanguage.getValue() == null) {

                showError("Todos los campos son obligatorios.");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateString = dpShowtimeDate.getValue().format(formatter);

            currentShowtime.setMovieId(cbxMovies.getValue().getId());
            currentShowtime.setRoomId(cbxRooms.getValue().getId());
            currentShowtime.setDate(dateString);
            currentShowtime.setTime(lblHour.getText().trim());
            currentShowtime.setFormat(cbxFormat.getValue());
            currentShowtime.setLanguage(cbxLanguage.getValue());
            currentShowtime.setPrice(Double.parseDouble(lblPrice.getText().trim()));

            showtimeRepository.update(currentShowtime);

            closeModal();

        } catch (NumberFormatException e) {
            showError("El precio debe ser un número válido.");
        } catch (Exception e) {
            showError("Ocurrió un error al guardar la función.");
            System.err.println(e.getMessage());
        }
    }

    private void showError(String message) {
        lblErrorMessage.setManaged(true);
        lblErrorMessage.setVisible(true);
        lblErrorMessage.setText(message);
    }

    private void setupMovieComboBox() {
        cbxMovies.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Movie movie, boolean empty) {
                super.updateItem(movie, empty);
                setText(empty || movie == null ? null : movie.getTitle());
            }
        });
        cbxMovies.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Movie movie, boolean empty) {
                super.updateItem(movie, empty);
                setText(empty || movie == null ? null : movie.getTitle());
            }
        });
    }

    private void setupRoomComboBox() {
        cbxRooms.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Room room, boolean empty) {
                super.updateItem(room, empty);
                setText(empty || room == null ? null : room.getName());
            }
        });
        cbxRooms.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Room room, boolean empty) {
                super.updateItem(room, empty);
                setText(empty || room == null ? null : room.getName());
            }
        });
    }
}