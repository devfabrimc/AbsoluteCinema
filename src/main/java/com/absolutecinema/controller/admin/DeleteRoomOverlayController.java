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

public class DeleteRoomOverlayController {

    @FXML
    private Label lblCapacity;

    @FXML
    private Label lblColumns;

    @FXML
    private Label lblErrorMessage;

    @FXML
    private Label lblRoom;

    @FXML
    private Label lblRows;

    @FXML
    private AnchorPane rootPane;

    private Room currentRoom;
    private final RoomRepository roomRepository = new RoomRepository();
    private final ShowtimeRepository showtimeRepository = new ShowtimeRepository();
    private final TicketRepository ticketRepository = new TicketRepository();
    private final PurchaseRepository purchaseRepository = new PurchaseRepository();

    public void setRoom(Room room) {
        this.currentRoom = room;

        if (room != null) {
            lblRoom.setText(room.getName());
            lblRows.setText(String.valueOf(room.getRows()));
            lblColumns.setText(String.valueOf(room.getColumns()));
            lblCapacity.setText(room.getCapacity() + " asientos");
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
            if (currentRoom != null) {
                List<Showtime> showtimes = showtimeRepository.findAll();
                if (showtimes != null) {
                    for (Showtime showtime : showtimes) {
                        if (showtime.getRoomId().equals(currentRoom.getId())) {

                            List<Ticket> tickets = ticketRepository.findAll();
                            if (tickets != null) {
                                for (Ticket ticket : tickets) {
                                    if (ticket.getShowtimeId().equals(showtime.getId())) {
                                        ticketRepository.delete(ticket.getId());

                                        if (ticket.getPurchaseId() != null) {
                                            purchaseRepository.delete(ticket.getPurchaseId());
                                        }
                                    }
                                }
                            }

                            showtimeRepository.delete(showtime.getId());
                        }
                    }
                }

                roomRepository.delete(currentRoom.getId());
            }

            rootPane.setVisible(false);
            lblErrorMessage.setVisible(false);
            lblErrorMessage.setManaged(false);

        } catch (Exception e) {
            lblErrorMessage.setManaged(true);
            lblErrorMessage.setVisible(true);
            lblErrorMessage.setText("Error al eliminar la sala y sus dependencias.");
            System.err.println(e.getMessage());
        }
    }
}