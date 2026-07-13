package com.absolutecinema.service;

import com.absolutecinema.model.Room;
import com.absolutecinema.model.Seat;
import com.absolutecinema.model.Showtime;
import com.absolutecinema.repository.RoomRepository;
import com.absolutecinema.repository.ShowtimeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeatService {
    private ShowtimeRepository showtimeRepository;
    private RoomRepository roomRepository;

    //Constructor con inyección de dependencias
    public SeatService(RoomRepository roomRepository,
                       ShowtimeRepository showtimeRepository){
        this.roomRepository = roomRepository;
        this.showtimeRepository = showtimeRepository;
    }

    //Metodo para generar los asientos que tiene la sala de una función
    //Usamos las filas y columnas de la sala para crear los asientos
    public List<Seat> getSeatsForShowtime(String showtimeId) {
        //Creamos un showtime para encontrar la función por ID
        Showtime showtime = showtimeRepository.findById(showtimeId);
        //Si la función no existe retornamos null
        if (showtime == null){
            return null;
        }
        //Creamos una room para encontrar la sala asociada a la funcion showtime
        Room room = roomRepository.findById(showtime.getRoomId());
        //Si la sala no existe retornamos null
        if (room == null){
            return null;
        }
        //Creamos la lista de asientos con polimorfismo
        List<Seat> allSeats = new ArrayList<>();
        //Iteramos sobre las filas y columnas para mapear los asientos
        for (int r = 1; r < room.getRows(); r++) {
            for (int c = 1; c < room.getColumns(); c++) {
                //Generamos los asientos usando un String para darles formato
                String label = String.format("%c%d",(char)('A' + r-1), c);
                //Creamos y guardamos dicho asiento en nuestra lista
                allSeats.add(new Seat(r, c, label));
            }
        }
        //Retornamos la lista llena
        return allSeats;
    }
    //Metodo para ver los asientos que ya están ocupados en una función
    public List<Seat> getOccupiedSeats(String showtimeId) {
        //Creamos un showtime para encontrar la función por ID
        Showtime showtime = showtimeRepository.findById(showtimeId);
        //Si la función no existe retornamos o no hay asientos reservados devolvemos null
        if (showtime == null || showtime.getReservedSeats() == null){
            return null;
        }
        //Creamos una room para encontrar la sala asociada a la funcion showtime
        Room room = roomRepository.findById(showtime.getRoomId());
        //Si la sala no existe retornamos null
        if (room == null){
            return null;
        }
        //Creamos una lista para guardar los asientos guardados
        List<Seat> occupiedSeats = new ArrayList<>();
        //Creamos una lista para guardar los ID de los asientos reservados
        List<String> reservedLabels = showtime.getReservedSeats();
        // Buscamos las coordenadas recreando el mapa de la sala
        for (int r = 1; r <= room.getRows(); r++) {
            for (int c = 1; c <= room.getColumns(); c++) {
                String label = String.format("%c%d", (char) ('A' + r - 1), c);
                //Verificamos si la lista de los asientos reservados contiene el label
                if (reservedLabels.contains(label)) {
                    //Creamos y guardamos dicho asiento en nuestra lista
                    occupiedSeats.add(new Seat(r, c, label));
                }
            }
        }
        //Retornamos la lista llena
        return occupiedSeats;

    }
    //Metodo para declarar un asiento libre
    public boolean isSeatAvailable(String showtimeId, String seatLabel) {
        //Creamos un showtime para encontrar la función por ID
        Showtime showtime = showtimeRepository.findById(showtimeId);
        //Si la función no existe retornamos o no hay asientos reservados devolvemos false
        //La segunda parte del if es para evitar errores de NullPointerException
        if (showtime == null || showtime.getReservedSeats() == null) {
            return false;
        }
        // Si la lista de reservados ya contiene la etiqueta, NO está disponible
        return !showtime.getReservedSeats().contains(seatLabel);
    }

}