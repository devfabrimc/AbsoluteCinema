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
    @FXML private TextField txtName;
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
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
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

        authService.register(name, email, name, PasswordUtils.hashPassword(password));

        closeModal();
    }

    private void showErrorMessage(String message) {
        lblRegisterMessage.setManaged(true);
        lblRegisterMessage.setVisible(true);
        lblRegisterMessage.setText(message);
    }

    private void closeModal() {
        txtName.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
        lblRegisterMessage.setText("");
        lblRegisterMessage.setVisible(false);
        lblRegisterMessage.setManaged(false);
        rootPane.setVisible(false);
    }

    public void openModal() {
        txtName.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
        lblRegisterMessage.setText("");
        lblRegisterMessage.setVisible(false);
        lblRegisterMessage.setManaged(false);
        rootPane.setVisible(true);
    }
}