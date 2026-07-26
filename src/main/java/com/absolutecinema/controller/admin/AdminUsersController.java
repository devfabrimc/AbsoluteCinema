package com.absolutecinema.controller.admin;

import com.absolutecinema.application.App;
import com.absolutecinema.model.Role;
import com.absolutecinema.model.User;
import com.absolutecinema.repository.UserRepository;
import com.absolutecinema.utils.Paths;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class AdminUsersController implements Initializable {

    @FXML
    private ComboBox<Role> cbxRoles;

    @FXML
    private TextField txtSearch;

    @FXML
    private VBox vbxUsers;

    @FXML
    private AnchorPane addUserOverlay;

    @FXML
    private AnchorPane editUserOverlay;

    @FXML
    private AnchorPane deleteUserOverlay;

    @FXML
    private EditUserOverlayController editUserOverlayController;

    @FXML
    private DeleteUserOverlayController deleteUserOverlayController;

    private final UserRepository userRepository = new UserRepository();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUsers();
        loadCbx();
        txtSearch.setOnAction(event -> loadUsers());

        addUserOverlay.visibleProperty().addListener((obs, wasVisible, isVisible) -> {
            if (wasVisible && !isVisible) loadUsers();
        });
        editUserOverlay.visibleProperty().addListener((obs, wasVisible, isVisible) -> {
            if (wasVisible && !isVisible) loadUsers();
        });
        deleteUserOverlay.visibleProperty().addListener((obs, wasVisible, isVisible) -> {
            if (wasVisible && !isVisible) loadUsers();
        });
    }

    private void loadUsers() {
        vbxUsers.getChildren().clear();
        vbxUsers.getChildren().add(createHeader());

        List<User> allUsers = userRepository.findAll();
        Role selectedRole = cbxRoles.getValue();
        String searchText = txtSearch.getText() != null ? txtSearch.getText().trim().toLowerCase() : "";

        for (User user : allUsers) {
            if (selectedRole != null && user.getRole() != selectedRole) {
                continue;
            }

            if (!searchText.isEmpty() &&
                    !user.getFullName().toLowerCase().contains(searchText) &&
                    !user.getUsername().toLowerCase().contains(searchText)) {
                continue;
            }

            String roleLabel = user.getRole() == Role.ADMIN ? "ADMINISTRADOR" : "CLIENTE";
            String idLabel = "ID: " + user.getId();

            HBox row = addUserRow(
                    user.getFullName(),
                    idLabel,
                    user.getEmail(),
                    user.getUsername(),
                    roleLabel
            );

            vbxUsers.getChildren().add(row);
        }
    }

    private void loadCbx() {
        cbxRoles.getItems().setAll(Role.values());

        cbxRoles.setConverter(new StringConverter<>() {
            @Override
            public String toString(Role role) {
                if (role == null) return "TODOS LOS ROLES";
                return role == Role.ADMIN ? "ADMINISTRADORES" : "CLIENTES";
            }

            @Override
            public Role fromString(String string) {
                return null;
            }
        });

        cbxRoles.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadUsers();
        });
    }

    private HBox createHeader() {
        HBox header = new HBox(30);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-padding: 0 10 10 15;");

        Label lblName = new Label("NOMBRE COMPLETO");
        lblName.getStyleClass().add("table-header-text");
        lblName.setPrefWidth(220);

        Label lblEmail = new Label("CORREO");
        lblEmail.getStyleClass().add("table-header-text");
        lblEmail.setPrefWidth(180);

        Label lblUsername = new Label("USUARIO");
        lblUsername.getStyleClass().add("table-header-text");
        lblUsername.setPrefWidth(130);

        Label lblRole = new Label("ROL");
        lblRole.getStyleClass().add("table-header-text");
        lblRole.setPrefWidth(110);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox actionHeaderBox = new HBox();
        actionHeaderBox.setAlignment(Pos.CENTER_RIGHT);
        actionHeaderBox.setPrefWidth(130);

        Label lblActions = new Label("ACCIONES");
        lblActions.getStyleClass().add("table-header-text");
        actionHeaderBox.getChildren().add(lblActions);

        header.getChildren().addAll(lblName, lblEmail, lblUsername, lblRole, spacer, actionHeaderBox);
        return header;
    }

    private HBox addUserRow(String fullName, String id, String email, String username, String role) {
        HBox row = new HBox(30);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-padding: 12 10 12 15; -fx-border-color: transparent transparent #272a33 transparent; -fx-border-width: 0 0 1 0;");

        VBox nameBox = new VBox(3);
        Label lblFullName = new Label(fullName);
        lblFullName.setStyle("-fx-text-fill: #F5F1E8; -fx-font-weight: 900; -fx-font-size: 14px;");
        Label lblId = new Label(id);
        lblId.setStyle("-fx-text-fill: #888c96; -fx-font-size: 11px;");
        nameBox.getChildren().addAll(lblFullName, lblId);
        nameBox.setPrefWidth(220);

        Label lblEmail = new Label(email);
        lblEmail.setStyle("-fx-text-fill: #d1d5db; -fx-font-size: 13px;");
        lblEmail.setPrefWidth(180);

        Label lblUsername = new Label(username);
        lblUsername.setStyle("-fx-text-fill: #d1d5db; -fx-font-size: 13px;");
        lblUsername.setPrefWidth(130);

        Label lblRole = new Label(role);
        if (role.equalsIgnoreCase("ADMINISTRADOR")) {
            lblRole.getStyleClass().add("badge-green");
        } else {
            lblRole.getStyleClass().add("badge-blue");
        }
        lblRole.setMaxWidth(Double.MAX_VALUE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox actionBox = new HBox(10);
        actionBox.setAlignment(Pos.CENTER_RIGHT);

        Button btnEdit = new Button("Editar");
        btnEdit.getStyleClass().add("btn-action-edit");
        btnEdit.setOnAction(event -> {
            if (editUserOverlayController != null) {
                String cleanId = id.replace("ID: ", "").trim();
                editUserOverlayController.setUser(userRepository.findById(cleanId));
                editUserOverlay.setVisible(true);
            }
        });

        Button btnDelete = new Button("Eliminar");
        btnDelete.getStyleClass().add("btn-action-delete");
        btnDelete.setOnAction(event -> {
            if (deleteUserOverlayController != null) {
                String cleanId = id.replace("ID: ", "").trim();
                deleteUserOverlayController.setUser(userRepository.findById(cleanId));
                deleteUserOverlay.setVisible(true);
            }
        });

        actionBox.getChildren().addAll(btnEdit, btnDelete);

        row.getChildren().addAll(nameBox, lblEmail, lblUsername, lblRole, spacer, actionBox);
        return row;
    }

    @FXML
    void addUserOverlay() {
        addUserOverlay.setVisible(true);
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
    void openShowtimeMenu() {
        App.app.setScene(Paths.ADMIN_SHOWTIMES_VIEW);
        App.app.setTitle(" | Funciones");
    }

    @FXML
    void openRoomsMenu() {
        App.app.setScene(Paths.ADMIN_ROOMS_VIEW);
        App.app.setTitle(" | Salas");
    }

    @FXML
    void btnBackOnAction() {
        App.app.setTitle(" | Menú principal");
        App.app.setScene(Paths.MENU_VIEW);
    }
}