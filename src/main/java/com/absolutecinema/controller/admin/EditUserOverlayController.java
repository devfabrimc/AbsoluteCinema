package com.absolutecinema.controller.admin;

import com.absolutecinema.model.Role;
import com.absolutecinema.model.User;
import com.absolutecinema.repository.UserRepository;
import com.absolutecinema.utils.PasswordUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class EditUserOverlayController {

    @FXML
    private ComboBox<Role> cbxRol;

    @FXML
    private TextField lblEmail;

    @FXML
    private Label lblErrorMessage;

    @FXML
    private TextField lblName;

    @FXML
    private PasswordField lblPassword;

    @FXML
    private TextField lblUserName;

    @FXML
    private AnchorPane rootPane;

    private User currentUser;
    private final UserRepository userRepository = new UserRepository();

    @FXML
    public void initialize() {
        cbxRol.setItems(FXCollections.observableArrayList(Role.values()));
        lblErrorMessage.setVisible(false);
        lblErrorMessage.setManaged(false);
    }

    public void setUser(User user) {
        this.currentUser = user;

        lblName.setText(user.getFullName());
        lblEmail.setText(user.getEmail());
        lblUserName.setText(user.getUsername());
        cbxRol.setValue(user.getRole());
        lblPassword.clear();
    }

    @FXML
    void closeModal() {
        resetForm();
        rootPane.setVisible(false);
    }

    @FXML
    void saveUser() {
        try {
            if (lblName.getText().isBlank() || lblEmail.getText().isBlank() ||
                    lblUserName.getText().isBlank() || cbxRol.getValue() == null) {

                showError("Todos los campos obligatorios deben estar llenos.");
                return;
            }

            currentUser.setFullName(lblName.getText().trim());
            currentUser.setEmail(lblEmail.getText().trim());
            currentUser.setUsername(lblUserName.getText().trim());
            currentUser.setRole(cbxRol.getValue());

            if (!lblPassword.getText().isBlank()) {
                currentUser.setPasswordHash(PasswordUtils.hashPassword(lblPassword.getText()));
            }

            userRepository.update(currentUser);
            closeModal();

        } catch (Exception e) {
            showError("Ocurrió un error al actualizar el usuario.");
            System.err.println(e.getMessage());
        }
    }

    private void showError(String message) {
        lblErrorMessage.setManaged(true);
        lblErrorMessage.setVisible(true);
        lblErrorMessage.setText(message);
    }

    private void resetForm() {
        lblName.clear();
        lblEmail.clear();
        lblUserName.clear();
        lblPassword.clear();
        cbxRol.setValue(null);
        lblErrorMessage.setVisible(false);
        lblErrorMessage.setManaged(false);
    }
}