package com.absolutecinema.model;

import java.util.List;

public class Purchase {
    // Atributos
    private String id;
    private String userId;
    private List<String> ticketIds;
    private double total;
    private String purchaseDate;

    // Constructor
    public Purchase(String id, String userId, List<String> ticketIds, double total, String purchaseDate) {
        this.id = id;
        this.userId = userId;
        this.ticketIds = ticketIds;
        this.total = total;
        this.purchaseDate = purchaseDate;
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getTicketIds() {
        return ticketIds;
    }

    public double getTotal() {
        return total;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }
}
