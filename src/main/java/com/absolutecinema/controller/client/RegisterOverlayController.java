package com.absolutecinema.controller.client;

import com.absolutecinema.repository.UserRepository;
import com.absolutecinema.service.AuthService;
import com.absolutecinema.utils.PasswordUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class RegisterOverlayController {

    @FXML private AnchorPane rootPane;
    @FXML private TextField txtFullName;
    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private Button btnRegisterVerify;
    @FXML private Label lblRegisterMessage;
    @FXML private Button btnCloseRegister;

    @FXML
    private Label lblOpenLoginOverlay;

    private final AuthService authService = new AuthService(new UserRepository());
    private MenuController parentController;

    public void setParentController(MenuController parentController) {
        this.parentController = parentController;
    }

    @FXML
    public void initialize() {
        lblRegisterMessage.setManaged(false);

        btnCloseRegister.setOnAction(event -> closeModal());
        btnRegisterVerify.setOnAction(event -> handleRegistration());

        lblOpenLoginOverlay.setOnMouseClicked(event -> {
            if (parentController != null) {
                closeModal();
                parentController.openLoginOverlay();
            }
        });
    }

    private void handleRegistration() {
        String fullName = txtFullName.getText().trim();
        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();

        if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty()) {
            showErrorMessage("Todos los campos son obligatorios.");
            return;
        }

        if (authService.emailExists(email)) {
            showErrorMessage("Esta correo electrónico ya está registrado.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showErrorMessage("Las contraseñas no coinciden.");
            return;
        }

        authService.register(fullName, email, username, password);

        closeModal();
    }

    private void showErrorMessage(String message) {
        lblRegisterMessage.setManaged(true);
        lblRegisterMessage.setVisible(true);
        lblRegisterMessage.setText(message);
    }

    private void resetForm() {
        txtFullName.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
        lblRegisterMessage.setText("");
        lblRegisterMessage.setVisible(false);
        lblRegisterMessage.setManaged(false);
    }

    private void closeModal() {
        resetForm();
        rootPane.setVisible(false);
    }
}