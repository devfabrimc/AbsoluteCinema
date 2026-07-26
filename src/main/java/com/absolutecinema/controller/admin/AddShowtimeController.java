package com.absolutecinema.controller.admin;

import com.absolutecinema.model.*;
import com.absolutecinema.repository.MovieRepository;
import com.absolutecinema.repository.RoomRepository;
import com.absolutecinema.repository.ShowtimeRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AddShowtimeController {

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

    private final ShowtimeRepository showtimeRepository = new ShowtimeRepository();
    private final MovieRepository movieRepository = new MovieRepository();
    private final RoomRepository roomRepository = new RoomRepository();

    /**
     * Inicializa el formulario de registro
     * de funciones.
     * Carga películas, salas, formatos
     * e idiomas disponibles.
     */
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

        dpShowtimeDate.getEditor().setOnMouseClicked(event -> {
            if (!dpShowtimeDate.isShowing()) {
                dpShowtimeDate.show();
            }
        });
    }

    /**
     * Cierra la ventana y restablece
     * todos los campos del formulario.
     */
    @FXML
    void closeModal() {
        resetForm();
        rootPane.setVisible(false);
        lblErrorMessage.setVisible(false);
        lblErrorMessage.setManaged(false);
    }

    /**
     * Valída los datos ingresados y registra
     * una nueva función.
     * Genera un identificador único y almacena
     * la función en el repositorio.
     */
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

            String lastId = showtimeRepository.getLastId();
            String newId = generateNextId(lastId);

            Showtime newShowtime = new Showtime(
                    newId,
                    cbxMovies.getValue().getId(),
                    cbxRooms.getValue().getId(),
                    dateString,
                    lblHour.getText().trim(),
                    cbxLanguage.getValue(),
                    cbxFormat.getValue(),
                    Double.parseDouble(lblPrice.getText().trim()),
                    new ArrayList<>()
            );

            showtimeRepository.save(newShowtime);

            closeModal();

        } catch (NumberFormatException e) {
            showError("El precio debe ser un número válido.");
        } catch (Exception e) {
            showError("Ocurrió un error al guardar la función.");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Muestra un mensaje de error
     * en la interfaz gráfica.
     * @param message mensaje que se mostrará
     * al usuario.
     */
    private void showError(String message) {
        lblErrorMessage.setManaged(true);
        lblErrorMessage.setVisible(true);
        lblErrorMessage.setText(message);
    }

    /**
     * Genera el siguiente identificador
     * consecutivo para una función.
     * @param lastId último identificador registrado.
     * @return nuevo identificador generado. SHW00#
     */
    private String generateNextId(String lastId) {
        if (lastId == null || !lastId.startsWith("SHW")) {
            return "SHW001";
        }
        int numericPart = Integer.parseInt(lastId.substring(3));
        return String.format("SHW%03d", numericPart + 1);
    }

    /**
     * Restablece todos los campos del
     * formulario a sus valores iniciales.
     */
    private void resetForm() {
        cbxMovies.setValue(null);
        cbxRooms.setValue(null);
        dpShowtimeDate.setValue(null);
        cbxFormat.setValue(null);
        cbxLanguage.setValue(null);
        lblHour.clear();
        lblPrice.clear();
        lblErrorMessage.setVisible(false);
        lblErrorMessage.setManaged(false);
    }

    /**
     * Configura el ComboBox de películas
     * para mostrar únicamente el título
     * de cada película.
     */
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

    /**
     * Configura el ComboBox de salas
     * para mostrar únicamente el nombre
     * de cada sala.
     */
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