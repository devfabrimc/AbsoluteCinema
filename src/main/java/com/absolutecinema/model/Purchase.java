package com.absolutecinema.model;

import java.util.List;

public class Purchase {
    // Atributos

    private String id;
    private String userId;
    private String purchaseDate;
    private double total;

    // Constructor

    public Purchase(String id, String userId, String purchaseDate, double total) {
        this.id = id;
        this.userId = userId;
        this.purchaseDate = purchaseDate;
        this.total = total;
    }

    public static Purchase printformat(String line) {

        String[] data = line.split(";");

        return new Purchase(
                data[0],
                data[1],
                data[2],
                Double.parseDouble(data[3])
        );
    }

    //Getters

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public double getTotal() {
        return total;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }
}
