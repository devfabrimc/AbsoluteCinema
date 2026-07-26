package com.absolutecinema.controller.admin;

import com.absolutecinema.model.Role;
import com.absolutecinema.model.User;
import com.absolutecinema.repository.UserRepository;
import com.absolutecinema.utils.PasswordUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddUserOverlayController {

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

    private final UserRepository userRepository = new UserRepository();

    /**
     * Inicializa los componentes del
     * formulario de registro de usuarios.
     * Carga los roles disponibles y oculta
     * el mensaje de error por defecto.
     */
    @FXML
    public void initialize() {
        cbxRol.setItems(FXCollections.observableArrayList(Role.values()));
        lblErrorMessage.setVisible(false);
        lblErrorMessage.setManaged(false);
    }

    /**
     * Cierra la ventana y restablece
     * todos los campos del formulario.
     */
    @FXML
    void closeModal() {
        resetForm();
        rootPane.setVisible(false);
    }

    /**
     * Válida los datos ingresados y registra
     * un nuevo usuario en el repositorio.
     * Genera automáticamente un identificador
     * único y cifra la contraseña antes
     * de almacenarla.
     */
    @FXML
    void saveUser() {
        try {
            if (lblName.getText().isBlank() || lblEmail.getText().isBlank() ||
                    lblUserName.getText().isBlank() || lblPassword.getText().isBlank() ||
                    cbxRol.getValue() == null) {

                showError("Todos los campos son obligatorios.");
                return;
            }

            String lastId = userRepository.getLastId();
            String newId = generateNextId(lastId);

            User newUser = new User(
                    newId,
                    lblName.getText().trim(),
                    lblEmail.getText().trim(),
                    lblUserName.getText().trim(),
                    PasswordUtils.hashPassword(lblPassword.getText()),
                    cbxRol.getValue()
            );

            userRepository.save(newUser);
            closeModal();

        } catch (Exception e) {
            showError("Ocurrió un error al guardar el usuario.");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Muestra un mensaje de error
     * en la interfaz.
     * @param message mensaje que se mostrará
     * al usuario.
     */
    private void showError(String message) {
        lblErrorMessage.setManaged(true);
        lblErrorMessage.setVisible(true);
        lblErrorMessage.setText(message);
    }

    private String generateNextId(String lastId) {
        if (lastId == null || !lastId.startsWith("USR")) {
            return "USR001";
        }
        int numericPart = Integer.parseInt(lastId.substring(3));
        return String.format("USR%03d", numericPart + 1);
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