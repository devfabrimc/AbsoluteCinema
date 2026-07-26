package com.absolutecinema.controller.admin;

import com.absolutecinema.model.Room;
import com.absolutecinema.model.Showtime;
import com.absolutecinema.model.Ticket;
import com.absolutecinema.repository.PurchaseRepository;
import com.absolutecinema.repository.RoomRepository;
import com.absolutecinema.repository.ShowtimeRepository;
import com.absolutecinema.repository.TicketRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class DeleteShowtimeOverlayController {

    @FXML
    private Label lblDate;

    @FXML
    private Label lblErrorMessage;

    @FXML
    private Label lblFormat;

    @FXML
    private Label lblHour;

    @FXML
    private Label lblRoom;

    @FXML
    private AnchorPane rootPane;

    private Showtime currentShowtime;
    private final ShowtimeRepository showtimeRepository = new ShowtimeRepository();
    private final RoomRepository roomRepository = new RoomRepository();
    private final TicketRepository ticketRepository = new TicketRepository();
    private final PurchaseRepository purchaseRepository = new PurchaseRepository();

    public void setShowtime(Showtime showtime) {
        this.currentShowtime = showtime;

        if (showtime != null) {
            lblDate.setText(showtime.getDate());
            lblHour.setText(showtime.getTime());
            lblFormat.setText(showtime.getFormat() != null ? showtime.getLanguage() + " - " + showtime.getFormat().getDisplayName() : "");

            Room room = roomRepository.findById(showtime.getRoomId());
            if (room != null) {
                lblRoom.setText(room.getName());
            } else {
                lblRoom.setText("ID: " + showtime.getRoomId());
            }
        }
    }

    @FXML
    void closeModal(ActionEvent event) {
        rootPane.setVisible(false);
        lblErrorMessage.setVisible(false);
        lblErrorMessage.setManaged(false);
    }

    @FXML
    void confirmDelete(ActionEvent event) {
        try {
            if (currentShowtime != null) {
                List<Ticket> tickets = ticketRepository.findAll();
                if (tickets != null) {
                    for (Ticket ticket : tickets) {
                        if (ticket.getShowtimeId().equals(currentShowtime.getId())) {
                            ticketRepository.delete(ticket.getId());

                            if (ticket.getPurchaseId() != null) {
                                purchaseRepository.delete(ticket.getPurchaseId());
                            }
                        }
                    }
                }

                showtimeRepository.delete(currentShowtime.getId());
            }
            rootPane.setVisible(false);
            lblErrorMessage.setVisible(false);
            lblErrorMessage.setManaged(false);
        } catch (Exception e) {
            lblErrorMessage.setManaged(true);
            lblErrorMessage.setVisible(true);
            lblErrorMessage.setText("Error al eliminar la función.");
            System.err.println(e.getMessage());
        }
    }
}