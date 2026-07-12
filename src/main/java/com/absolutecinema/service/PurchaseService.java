package com.absolutecinema.service;

import com.absolutecinema.model.Purchase;
import com.absolutecinema.model.Ticket;
import com.absolutecinema.repository.ShowtimeRepository;

import java.util.Collections;
import java.util.List;

public class PurchaseService {
    private PurchaseRepository purchaseRepository;
    private TicketRepository ticketRepository;
    private ShowtimeRepository showtimeRepository;

    //Metodo de compra
    public Purchase purchase(String userId, String showtimeId, List<String> seats) {
        return null;
    }
    //Metodo de compra por usuario
    public List<Purchase> getPurchasesByUser(String userId) {
        return Collections.emptyList();
    }
    //Metodo compra de ticket
    public List<Ticket> getTicketsByPurchase(String purchaseId) {
        return Collections.emptyList();
    }
}