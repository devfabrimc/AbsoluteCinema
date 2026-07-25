package com.absolutecinema.controller.client;

import com.absolutecinema.application.App;
import com.absolutecinema.model.*;
import com.absolutecinema.repository.PurchaseRepository;
import com.absolutecinema.repository.ShowtimeRepository;
import com.absolutecinema.repository.TicketRepository;
import com.absolutecinema.service.PurchaseService;
import com.absolutecinema.utils.DurationFormatter;
import com.absolutecinema.utils.GenreFormatter;
import com.absolutecinema.utils.Paths;
import com.absolutecinema.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ConfirmPurchaseOverlayController implements Initializable {
    @FXML
    private Label lblAmount;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblFormat;

    @FXML
    private Label lblHour;

    @FXML
    private Label lblMovieMeta;

    @FXML
    private Label lblMovieTitle;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblRoom;

    @FXML
    private Label lblSelectedSeats;

    @FXML
    private Label lblTotal;

    @FXML
    private AnchorPane rootPane;

    private Movie movie;
    private Room room;
    private Showtime showtime;
    private List<String> selectedSeats;

    private ShowtimeRepository showtimeRepository;
    private TicketRepository ticketRepository;
    private PurchaseRepository purchaseRepository;
    private PurchaseService purchaseService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showtimeRepository = new ShowtimeRepository();
        ticketRepository = new TicketRepository();
        purchaseRepository = new PurchaseRepository();

        purchaseService = new PurchaseService(purchaseRepository, ticketRepository, showtimeRepository);
    }

    public void setData(Movie movie, Room room, Showtime showtime, List<String> selectedSeats) {
        this.movie = movie;
        this.room = room;
        this.showtime = showtime;
        this.selectedSeats = selectedSeats;


        lblMovieTitle.setText(movie.getTitle().toUpperCase());
        lblMovieMeta.setText(GenreFormatter.format(movie.getGenre()) + " • " + movie.getRating() + " • " + DurationFormatter.format(movie.getDurationMinutes()));

        lblRoom.setText(room.getName());
        lblDate.setText(showtime.getDate());
        lblHour.setText(showtime.getTime());
        lblFormat.setText(showtime.getFormat().getDisplayName() + " - " + showtime.getLanguage());

        lblSelectedSeats.setText(String.join(", ", selectedSeats));

        lblPrice.setText("$" + showtime.getPrice());
        lblAmount.setText(String.valueOf(selectedSeats.size()));

        double total = showtime.getPrice() * selectedSeats.size();
        lblTotal.setText("$" + total);
    }

    @FXML
    void closeModal() {
        rootPane.setVisible(false);
    }

    @FXML
    void btnConfirmPurchase() {
        String currentUserId;

        if(!SessionManager.getInstance().isLoggedIn()) {
            currentUserId = "USR000";
        } else {
            currentUserId = SessionManager.getInstance().getCurrentUser().getId();
        }


        Purchase purchase = purchaseService.purchase(
                currentUserId,
                showtime.getId(),
                selectedSeats
        );

        if (purchase != null) {
            rootPane.setVisible(false);
            App.app.setScene(Paths.MENU_VIEW);
            App.app.setTitle(" | Cartelera");
        } else {
            System.out.println("ERROR: No se pudo procesar la compra.");
        }
    }
}
