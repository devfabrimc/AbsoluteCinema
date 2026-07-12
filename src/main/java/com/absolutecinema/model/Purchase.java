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
