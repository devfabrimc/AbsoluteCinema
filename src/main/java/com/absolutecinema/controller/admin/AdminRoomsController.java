package com.absolutecinema.controller.admin;

import com.absolutecinema.application.App;
import com.absolutecinema.model.Room;
import com.absolutecinema.repository.RoomRepository;
import com.absolutecinema.utils.Paths;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminRoomsController implements Initializable {

    @FXML
    private TextField txtSearch;

    @FXML
    private VBox vbxRooms;

    @FXML
    private AnchorPane addRoomOverlay;

    @FXML
    private AnchorPane editRoomOverlay;

    @FXML
    private AnchorPane deleteRoomOverlay;

    @FXML
    private EditRoomOverlayController editRoomOverlayController;

    @FXML
    private DeleteRoomOverlayController deleteRoomOverlayController;

    private final RoomRepository roomRepository = new RoomRepository();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRooms();

        txtSearch.setOnAction(event -> loadRooms());
        txtSearch.textProperty().addListener((obs, oldValue, newValue) -> loadRooms());

        addRoomOverlay.visibleProperty().addListener((obs, wasVisible, isVisible) -> {
            if (wasVisible && !isVisible) loadRooms();
        });
        editRoomOverlay.visibleProperty().addListener((obs, wasVisible, isVisible) -> {
            if (wasVisible && !isVisible) loadRooms();
        });
        deleteRoomOverlay.visibleProperty().addListener((obs, wasVisible, isVisible) -> {
            if (wasVisible && !isVisible) loadRooms();
        });
    }

    private void loadRooms() {
        vbxRooms.getChildren().clear();
        vbxRooms.setSpacing(15);

        List<Room> allRooms = roomRepository.findAll();
        String searchText = txtSearch.getText() != null ? txtSearch.getText().trim().toLowerCase() : "";

        for (Room room : allRooms) {
            String roomName = room.getName() != null ? room.getName().toLowerCase() : "";
            String roomId = room.getId() != null ? room.getId().toLowerCase() : "";

            if (!searchText.isEmpty() && !roomName.contains(searchText) && !roomId.contains(searchText)) {
                continue;
            }

            HBox card = createRoomCard(room);
            vbxRooms.getChildren().add(card);
        }
    }

    private HBox createRoomCard(Room room) {
        HBox card = new HBox(20);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle("-fx-background-color: rgb(255,255,255,0.02); -fx-background-radius: 8; -fx-padding: 15; -fx-border-color: #272a33; -fx-border-radius: 8;");

        VBox detailsBox = new VBox(8);
        detailsBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(detailsBox, Priority.ALWAYS);

        Label lblTitle = new Label(room.getName());
        lblTitle.setStyle("-fx-text-fill: #D6A85A; -fx-font-weight: 900; -fx-font-size: 16px;");

        HBox infoRow = new HBox(20);

        Label lblId = new Label("ID: " + room.getId());
        lblId.setStyle("-fx-text-fill: #888c96; -fx-font-size: 12px;");

        Label lblCapacity = new Label("Capacidad: " + room.getCapacity() + " asientos");
        lblCapacity.setStyle("-fx-text-fill: #888c96; -fx-font-size: 12px;");

        Label lblDimensions = new Label("Dimensiones: " + room.getRows() + " filas x " + room.getColumns() + " columnas");
        lblDimensions.setStyle("-fx-text-fill: #888c96; -fx-font-size: 12px;");

        infoRow.getChildren().addAll(lblId, lblCapacity, lblDimensions);
        detailsBox.getChildren().addAll(lblTitle, infoRow);

        VBox actionBox = new VBox();
        actionBox.setAlignment(Pos.CENTER_RIGHT);

        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);

        Button btnEdit = new Button("Editar");
        btnEdit.getStyleClass().add("btn-action-edit");
        btnEdit.setOnAction(e -> {
            if (editRoomOverlayController != null) {
                editRoomOverlayController.setRoom(room);
                editRoomOverlay.setVisible(true);
            }
        });

        Button btnDelete = new Button("Eliminar");
        btnDelete.getStyleClass().add("btn-action-delete");
        btnDelete.setOnAction(e -> {
            if (deleteRoomOverlayController != null) {
                deleteRoomOverlayController.setRoom(room);
                deleteRoomOverlay.setVisible(true);
            }
        });

        buttonsBox.getChildren().addAll(btnEdit, btnDelete);
        actionBox.getChildren().add(buttonsBox);

        card.getChildren().addAll(detailsBox, actionBox);
        return card;
    }

    @FXML
    void addShowtimeOverlay() {
        addRoomOverlay.setVisible(true);
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
    void openShowtimesMenu() {
        App.app.setScene(Paths.ADMIN_SHOWTIMES_VIEW);
        App.app.setTitle(" | Funciones");
    }

    @FXML
    void openUsersMenu() {
        App.app.setScene(Paths.ADMIN_USERS_VIEW);
        App.app.setTitle(" | Usuarios");
    }

    @FXML
    void btnBackOnAction() {
        App.app.setTitle(" | Menú principal");
        App.app.setScene(Paths.MENU_VIEW);
    }
}