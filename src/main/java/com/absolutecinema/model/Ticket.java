package com.absolutecinema.model;

public class Ticket {
    // Atributos

    private String id;
    private String purchaseId;
    private String showtimeId;
    private String movieTitle;
    private String roomName;
    private String seatLabel;
    private double price;

    // Constructor

    public Ticket(String id, String purchaseId, String showtimeId, String movieTitle, String roomName, String seatLabel, double price) {
        this.id = id;
        this.purchaseId = purchaseId;
        this.showtimeId = showtimeId;
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

    public String getShowtimeId() {
        return showtimeId;
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
