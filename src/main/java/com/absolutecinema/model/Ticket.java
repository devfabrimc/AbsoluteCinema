package com.absolutecinema.model;

public class Ticket {
    // Atributos

    private String id;
    private String purchaseId;
    private String showtimeId;
    private String seatLabel;
    private double price;

    // Constructor

    public Ticket(String id, String purchaseId, String showtimeId, String seatLabel, double price) {
        this.id = id;
        this.purchaseId = purchaseId;
        this.showtimeId = showtimeId;
        this.seatLabel = seatLabel;
        this.price = price;
    }

    public static Ticket printformat(String line) {

        String[] data = line.split(";");

        return new Ticket(
                data[0],
                data[1],
                data[2],
                data[3],
                Double.parseDouble(data[4].replace(",", "."))
        );
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

    public String getSeatLabel() {
        return seatLabel;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return id + ";" +
                purchaseId + ";" +
                showtimeId + ";" +
                seatLabel + ";" +
                String.format("%.2f", price);
    }
}
