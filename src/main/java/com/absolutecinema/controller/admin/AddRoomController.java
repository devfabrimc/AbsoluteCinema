package com.absolutecinema.controller.admin;

import com.absolutecinema.model.Room;
import com.absolutecinema.repository.RoomRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddRoomController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField txtRoomName;

    @FXML
    private TextField txtRows;

    @FXML
    private TextField txtColumns;

    @FXML
    private Label lblCapacity;

    @FXML
    private Label lblErrorMessage;

    private final RoomRepository roomRepository = new RoomRepository();

    /**
     * Inicializa los eventos necesarios para
     * actualizar automáticamente la capacidad
     * de la sala cuando cambian las filas
     * o las columnas.
     */
    @FXML
    public void initialize() {
        txtRows.textProperty().addListener((obs, oldVal, newVal) -> updateCapacityPreview());
        txtColumns.textProperty().addListener((obs, oldVal, newVal) -> updateCapacityPreview());
    }

    /**
     * Calcula y muestra la capacidad total
     * de la sala a partir de las filas y
     * columnas ingresadas.
     * Si los valores ingresados no son
     * numéricos muestra una capacidad
     * no disponible.
     */
    private void updateCapacityPreview() {
        try {
            int rows = txtRows.getText().isBlank() ? 0 : Integer.parseInt(txtRows.getText().trim());
            int cols = txtColumns.getText().isBlank() ? 0 : Integer.parseInt(txtColumns.getText().trim());
            int total = rows * cols;
            lblCapacity.setText("Capacidad: " + total + " asientos");
        } catch (NumberFormatException e) {
            lblCapacity.setText("Capacidad: -- asientos");
        }
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
     * una nueva sala en el repositorio.
     * Genera automáticamente un identificador
     * único para la sala antes de almacenarla.
     */
    @FXML
    void saveRoom() {
        try {
            if (txtRoomName.getText().isBlank() || txtRows.getText().isBlank() || txtColumns.getText().isBlank()) {
                showError("Todos los campos son obligatorios.");
                return;
            }

            int rows = Integer.parseInt(txtRows.getText().trim());
            int columns = Integer.parseInt(txtColumns.getText().trim());

            if (rows <= 0 || columns <= 0) {
                showError("Las filas y columnas deben ser mayores a cero.");
                return;
            }

            String lastId = roomRepository.getLastId();
            String newId = generateNextId(lastId);

            Room newRoom = new Room(
                    newId,
                    txtRoomName.getText().trim(),
                    rows,
                    columns
            );

            roomRepository.save(newRoom);
            closeModal();

        } catch (NumberFormatException e) {
            showError("Las filas y columnas deben ser números enteros.");
        } catch (Exception e) {
            showError("Ocurrió un error al guardar la sala.");
            System.err.println(e.getMessage());
        }
    }
    /**
     * Genera el siguiente identificador
     * consecutivo para una sala.
     * @param lastId último identificador registrado.
     * @return nuevo identificador generado. ROM00#
     */
    private String generateNextId(String lastId) {
        if (lastId == null || !lastId.startsWith("ROM")) {
            return "ROM001";
        }
        int numericPart = Integer.parseInt(lastId.substring(3));
        return String.format("ROM%03d", numericPart + 1);
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
     * Restablece todos los campos del
     * formulario a sus valores iniciales.
     */
    private void resetForm() {
        txtRoomName.clear();
        txtRows.clear();
        txtColumns.clear();
        lblCapacity.setText("Capacidad: -- asientos");
        lblErrorMessage.setVisible(false);
        lblErrorMessage.setManaged(false);
    }
}