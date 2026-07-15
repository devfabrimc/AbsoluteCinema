package com.absolutecinema.repository;

import com.absolutecinema.model.Ticket;
import com.absolutecinema.utils.Paths;
import com.absolutecinema.utils.TxtFileManager;

import java.util.ArrayList;
import java.util.List;

/*  Implementación del repositorio para la gestión de la persistencia
    de objetos de la clase Ticket, usando el tickets.txt como fuente de datos
 */

public class TicketRepository implements Repository<Ticket>{
    // Declaramos la ruta del archivo que se encuentran los tickets
    public static final String filePath = Paths.TICKET_REPOSITORY;

    // Es el gestor encargado de la lectura y escritura de archivos
    public final TxtFileManager fileManager = new TxtFileManager();

    // ------- Métodos sobrescritos de la Interfaz Repository -------

    /*  Lee todos los tickets del tickets.txt.
        Convierte cada línea en un objeto Ticket
        utilizando el método estático de la clase Ticket
     */

    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();

        for (String line : fileManager.readLines(filePath)){
            tickets.add(Ticket.fromString(line));
        }

        return tickets;
    }

    // Busca un ticket en específico comparando cada uno de los IDs.

    @Override
    public Ticket findById(String id) {
        for (Ticket ticket : findAll()){
            if (ticket.getId().equals(id)){
                return ticket;
            }
        }

        return null;
    }

    // Agrega un nuevo ticket al final del tickets.txt.

    @Override
    public void save(Ticket ticket) {
        fileManager.appendLine(filePath, ticket.toString());
    }

    /*  Actualiza un ticket existente.
        Cargando todos los tickets, reemplaza la que
        coincide por ID en la lista y reescribe el tickets.txt.
     */

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

    /*  Elimina un ticket por su ID.
        Filtrando la lista actual sin incluir el ID y sobrescribe el archivo.
     */

    @Override
    public void delete(String id) {
        List<Ticket> tickets = findAll();

        tickets.removeIf(ticket -> ticket.getId().equals(id));

        writeAll(tickets);
    }

    // ------- Método de búsqueda específica -------

    //  Busca todos los tickets asociados a un ID de compra en específico.

    public List<Ticket> findByPurchaseId(String purchaseId) {
        List<Ticket> result = new ArrayList<>();

        for (Ticket ticket : findAll()){
            if (ticket.getPurchaseId().equals(purchaseId)){
                result.add(ticket);
            }
        }

        return result;
    }

    /*  Obtiene el último ID que se generó en el archivo
        para facilitar la creación de nuevos registros.
     */

    public String getLastId() {
        List<Ticket> tickets = findAll();

        if (tickets.isEmpty()) {
            return "TCK000";
        }
        return tickets.get(tickets.size() - 1).getId();
    }

    /*  Método auxiliar privado para la sincronización de la lista
        de objetos con el tickets.txt.
        Convierte la lista de objetos a una lista de strings
        por medio del "toString" y los almacena.
     */

    private void writeAll(List<Ticket> tickets){
        List<String> lines = new ArrayList<>();

        for(Ticket ticket : tickets){
            lines.add(ticket.toString());
        }

        fileManager.writeLines(filePath, lines);
    }
}
