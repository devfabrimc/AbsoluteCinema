package com.absolutecinema.model;

import java.util.List;

public class Showtime {
    // Atributos

    private String id;
    private String movieId;
    private String roomId;
    private String date;
    private String time;
    private Language language;
    private Format format;
    private double price;
    private List<String> reservedSeats;

    // Constructor

    public Showtime(String id, String movieId, String roomId, String date, String time, Language language, Format format, double price, List<String> reservedSeats) {
        this.id = id;
        this.movieId = movieId;
        this.roomId = roomId;
        this.date = date;
        this.time = time;
        this.language = language;
        this.format = format;
        this.price = price;
        this.reservedSeats = reservedSeats;
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Language getLanguage() {
        return language;
    }

    public Format getFormat() {
        return format;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getReservedSeats() {
        return reservedSeats;
    }

    // Métodos para la manipulación de la disponibilidad de asientos
    public void addReservedSeat(String seatLabel){
        reservedSeats.add(seatLabel);
    }

    public void removeReservedSeat(String seatLabel){
        reservedSeats.remove(seatLabel);
    }

    public boolean isSeatReserved(String seatLabel){
        return reservedSeats.contains(seatLabel);
    }
}
