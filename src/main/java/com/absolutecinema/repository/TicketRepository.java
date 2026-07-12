package com.absolutecinema.repository;

import com.absolutecinema.model.Ticket;
import com.absolutecinema.utils.Paths;
import com.absolutecinema.utils.TxtFileManager;

import java.util.ArrayList;
import java.util.List;

public class TicketRepository implements Repository<Ticket>{
    public static final String filePath = Paths.TICKET_REPOSITORY;
    public final TxtFileManager fileManager = new TxtFileManager();

    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();

        for (String line : fileManager.readLines(filePath)){
            tickets.add(Ticket.printformat(line));
        }

        return tickets;
    }

    @Override
    public Ticket findById(String id) {
        for (Ticket ticket : findAll()){
            if (ticket.getId().equals(id)){
                return ticket;
            }
        }

        return null;
    }

    @Override
    public void save(Ticket ticket) {
        fileManager.appendLine(filePath, ticket.toString());
    }

    @Override
    public void update(Ticket ticket) {
        List<Ticket> tickets = findAll();

        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).getId().equals(ticket.getId())) {
                tickets.set(i, ticket);
                break;
            }
        }

        writeAll(tickets);
    }

    @Override
    public void delete(String id) {
        List<Ticket> tickets = findAll();

        tickets.removeIf(ticket -> ticket.getId().equals(id));

        writeAll(tickets);
    }

    public List<Ticket> findByPurchaseId(String purchaseId) {
        List<Ticket> result = new ArrayList<>();

        for (Ticket ticket : findAll()){
            if (ticket.getPurchaseId().equals(purchaseId)){
                result.add(ticket);
            }
        }

        return result;
    }

    public String getLastId() {
        List<Ticket> tickets = findAll();

        if (tickets.isEmpty()) {
            return "TCK000";
        }
        return tickets.get(tickets.size() - 1).getId();
    }

    private void writeAll(List<Ticket> tickets){
        List<String> lines = new ArrayList<>();

        for(Ticket ticket : tickets){
            lines.add(ticket.toString());
        }

        fileManager.writeLines(filePath, lines);
    }
}
