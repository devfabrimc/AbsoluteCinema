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

    public Showtime(String id, String movieId, String roomId, String date, String time, Language language,
                    Format format, double price, List<String> reservedSeats) {
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

    public void setId(String id) {
        this.id = id;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Métodos para la manipulación de la disponibilidad de asientos

    public void addReservedSeat(String seatLabel){
        reservedSeats.add(seatLabel);
    }

    // Método toString

    @Override
    public String toString() {

        /* Declaramos una variable para convertir a la lista
           a una cadena separada por comas
         */

        return id + ";" +
                movieId + ";" +
                roomId + ";" +
                date + ";" +
                time + ";" +
                language.name() + ";" +
                format.name() + ";" +
                price + ";" +
                String.join(",", reservedSeats);
    }

    /*  Método para crear una instancia de Showtime
        a partir de una cadena de texto.
        Formato que se espera (separado por ";"):
        id;movieId;roomId;date;time;language;price;reservedSeats

        Parámetro: line, la cadena de texto que se
        debe procesar.
     */

    public static Showtime fromString(String line) {

        String[] data = line.split(";", -1);

        List<String> reservedSeats = new ArrayList<>();

        if (data.length > 8 && !data[8].isEmpty()) {
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
}
