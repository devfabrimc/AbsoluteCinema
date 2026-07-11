package com.absolutecinema.model;

public class Ticket {
    // Atributos
    private String id;
    private String purchaseId;
    private String showtimeID;
    private String movieTitle;
    private String roomName;
    private String seatLabel;
    private double price;

    // Constructor
    public Ticket(String id, String purchaseId, String showtimeID, String movieTitle, String roomName, String seatLabel, double price) {
        this.id = id;
        this.purchaseId = purchaseId;
        this.showtimeID = showtimeID;
        this.movieTitle = movieTitle;
        this.roomName = roomName;
        this.seatLabel = seatLabel;
        this.price = price;
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public String getShowtimeID() {
        return showtimeID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getSeatLabel() {
        return seatLabel;
    }

    public double getPrice() {
        return price;
    }
}
