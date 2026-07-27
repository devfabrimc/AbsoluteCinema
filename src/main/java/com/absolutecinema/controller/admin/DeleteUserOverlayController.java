package com.absolutecinema.controller.admin;

import com.absolutecinema.model.Purchase;
import com.absolutecinema.model.Ticket;
import com.absolutecinema.model.User;
import com.absolutecinema.repository.PurchaseRepository;
import com.absolutecinema.repository.TicketRepository;
import com.absolutecinema.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class DeleteUserOverlayController {

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblErrorMessage;

    @FXML
    private Label lblFullName;

    @FXML
    private Label lblRole;

    @FXML
    private Label lblUserName;

    @FXML
    private AnchorPane rootPane;

    private User currentUser;
    private final UserRepository userRepository = new UserRepository();
    private final PurchaseRepository purchaseRepository = new PurchaseRepository();
    private final TicketRepository ticketRepository = new TicketRepository();

    public void setUser(User user) {
        this.currentUser = user;

        if (user != null) {
            lblFullName.setText(user.getFullName());
            lblUserName.setText(user.getUsername());
            lblEmail.setText(user.getEmail());
            lblRole.setText(user.getRole() != null ? user.getRole().toString() : "");
        }
    }

    @FXML
    void closeModal() {
        rootPane.setVisible(false);
        lblErrorMessage.setVisible(false);
        lblErrorMessage.setManaged(false);
    }

    @FXML
    void confirmDelete() {
        try {
            if (currentUser != null) {
                List<Purchase> userPurchases = purchaseRepository.findByUserId(currentUser.getId());

                if (userPurchases != null) {
                    for (Purchase purchase : userPurchases) {
                        List<Ticket> purchaseTickets = ticketRepository.findByPurchaseId(purchase.getId());
                        if (purchaseTickets != null) {
                            for (Ticket ticket : purchaseTickets) {
                                ticketRepository.delete(ticket.getId());
                            }
                        }
                        purchaseRepository.delete(purchase.getId());
                    }
                }

                userRepository.delete(currentUser.getId());
            }

            rootPane.setVisible(false);
            lblErrorMessage.setVisible(false);
            lblErrorMessage.setManaged(false);

        } catch (Exception e) {
            lblErrorMessage.setManaged(true);
            lblErrorMessage.setVisible(true);
            lblErrorMessage.setText("Error al eliminar el usuario y sus dependencias.");
            System.err.println(e.getMessage());
        }
    }
}