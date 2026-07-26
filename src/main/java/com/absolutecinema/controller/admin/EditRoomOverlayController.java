package com.absolutecinema.controller.admin;

import com.absolutecinema.model.Room;
import com.absolutecinema.repository.RoomRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class EditRoomOverlayController {

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

    private Room currentRoom;
    private final RoomRepository roomRepository = new RoomRepository();

    @FXML
    public void initialize() {
        txtRows.textProperty().addListener((obs, oldVal, newVal) -> updateCapacityPreview());
        txtColumns.textProperty().addListener((obs, oldVal, newVal) -> updateCapacityPreview());
    }

    public void setRoom(Room room) {
        this.currentRoom = room;

        txtRoomName.setText(room.getName());
        txtRows.setText(String.valueOf(room.getRows()));
        txtColumns.setText(String.valueOf(room.getColumns()));

        updateCapacityPreview();
        lblErrorMessage.setVisible(false);
        lblErrorMessage.setManaged(false);
    }

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

    @FXML
    void closeModal() {
        rootPane.setVisible(false);
        lblErrorMessage.setVisible(false);
        lblErrorMessage.setManaged(false);
    }

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

            currentRoom.setName(txtRoomName.getText().trim());
            currentRoom.setRows(rows);
            currentRoom.setColumns(columns);

            roomRepository.update(currentRoom);

            closeModal();

        } catch (NumberFormatException e) {
            showError("Las filas y columnas deben ser números enteros.");
        } catch (Exception e) {
            showError("Ocurrió un error al actualizar la sala.");
            System.err.println(e.getMessage());
        }
    }

    private void showError(String message) {
        lblErrorMessage.setManaged(true);
        lblErrorMessage.setVisible(true);
        lblErrorMessage.setText(message);
    }
}