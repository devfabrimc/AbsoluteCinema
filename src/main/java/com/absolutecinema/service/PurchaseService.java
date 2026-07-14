package com.absolutecinema.service;

import com.absolutecinema.model.Purchase;
import com.absolutecinema.model.Showtime;
import com.absolutecinema.model.Ticket;
import com.absolutecinema.repository.PurchaseRepository;
import com.absolutecinema.repository.ShowtimeRepository;
import com.absolutecinema.repository.TicketRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class PurchaseService {
    private PurchaseRepository purchaseRepository;
    private TicketRepository ticketRepository;
    private ShowtimeRepository showtimeRepository;

    //Constructor con inyección de dependencias
    public PurchaseService(PurchaseRepository purchaseRepository,
                           TicketRepository ticketRepository,
                           ShowtimeRepository showtimeRepository){
        this.purchaseRepository = purchaseRepository;
        this.ticketRepository = ticketRepository;
        this.showtimeRepository = showtimeRepository;
    }

    //Metodo de compra
    public Purchase purchase(String userId, String showtimeId, List<String> seats) {
        //Creamos un showtime para buscar la función por ID
        Showtime showtime = showtimeRepository.findById(showtimeId);
        //Validamos que exista la función y los asientos para su compra
        if(showtime == null || seats == null || seats.isEmpty()){
            //retornamos null
            return null;
        }

        //Calcular el total de la compra
        double pricePerSeat = showtime.getPrice();
        double total = pricePerSeat * seats.size();

        //Generar ID para la compra
        String lastPurchaseId = purchaseRepository.getLastId();
        int nextPurchaseNum = Integer.parseInt(lastPurchaseId.substring(3)) + 1;
        String newPurchaseId = String.format("PUR%03d", nextPurchaseNum);

        //Creación de Purchase
        Purchase newPurchase = new Purchase(lastPurchaseId, userId, LocalDate.now().toString(),total );
        //Guardado de Purchase
        purchaseRepository.save(newPurchase);

        //Creación del ID de los tickets individuales
        String lastTicketId = ticketRepository.getLastId();
        int nextTicketNum = Integer.parseInt(lastTicketId.substring(3)) + 1;
        //Iteramos sobre


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