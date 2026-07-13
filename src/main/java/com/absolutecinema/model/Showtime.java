package com.absolutecinema.model;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static Showtime printformat(String line) {

        String[] data = line.split(";");

        List<String> reservedSeats = new ArrayList<>();

        if (!data[8].isEmpty()) {
            reservedSeats = Arrays.asList(data[8].split(","));
        }

        return new Showtime(
                data[0],
                data[1],
                data[2],
                data[3],
                data[4],
                Language.valueOf(data[5]),
                Format.valueOf(data[6]),
                Double.parseDouble(data[7]),
                new ArrayList<>(reservedSeats)
        );
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

    // Método toString
    @Override
    public String toString() {
        /* Declaramos una variable para convertir a la lista
           a una cadena separada por comas
         */
        String seatsString = String.join(",", reservedSeats);

        return id + ";" +
                movieId + ";" +
                roomId + ";" +
                date + ";" +
                time + ";" +
                language.name() + ";" +
                format.name() + ";" +
                price + ";" +
                seatsString;

    }
}
