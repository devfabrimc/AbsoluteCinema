package com.absolutecinema.controller.client;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class NoAccountOverlayController {

    @FXML
    private AnchorPane rootPane;

    private MovieCardController parentController;

    public void setParentController(MovieCardController parentController) {
        this.parentController = parentController;
    }

    @FXML
    void closeModal() {
        rootPane.setVisible(false);
    }

    @FXML
    void continueButton() {
        rootPane.setVisible(false);
    }

}
