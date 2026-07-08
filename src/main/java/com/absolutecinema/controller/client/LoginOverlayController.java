package com.absolutecinema.controller.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginOverlayController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button btnCloseLogin;

    @FXML
    private Button btnLoginVerify;

    @FXML
    private Label lblLoginMessage;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblOpenRegisterOverlay;

    private final AuthService authService = new AuthService();
    private MenuController parentController;
    
    public void setParentController(MenuController parentController) {
        this.parentController = parentController;
    }

    @FXML
    public void initialize() {
        lblLoginMessage.setManaged(false);
        btnCloseLogin.setOnAction(event -> closeModal());

        btnLoginVerify.setOnAction(event -> handleLogin());

        lblOpenRegisterOverlay.setOnMouseClicked(event -> {
            if (parentController != null) {
                closeModal();
                parentController.openRegisterOverlay();
            }
        });
    }

    private void handleLogin() {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText();

        User user = authService.authenticate(email, password);

        if (user != null) {
            SessionManager.iniciarSesión(user);

            if (parentController != null) {
                parentController.updateNavbarAfterLogin(user.getNombre());
            }

            closeModal();
        } else {
            lblLoginMessage.setText("Correo electrónico o contraseña incorrectos.");
            lblLoginMessage.setVisible(true);
            lblLoginMessage.setManaged(true);
        }
    }

    private void closeModal() {
        txtEmail.clear();
        txtPassword.clear();
        lblLoginMessage.setText("");
        rootPane.setVisible(false);
    }

    public void openModal() {
        txtEmail.clear();
        txtPassword.clear();
        lblLoginMessage.setText("");
        rootPane.setVisible(true);
    }
}
