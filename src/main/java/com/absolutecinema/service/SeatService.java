package com.absolutecinema.service;

import com.absolutecinema.model.Seat;
import com.absolutecinema.repository.RoomRepository;
import com.absolutecinema.repository.ShowtimeRepository;

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

    //Metodo para tener todos los asientos de una función
    public List<Seat> getSeatsForShowtime(String showtimeId) {return Collections.emptyList();

    }
    //Metodo para ver los asientos ocupados
    public List<Seat> getOccupiedSeats(String showtimeId) {
        return Collections.emptyList();
    }
    //Metodo para declarar un asiento libre
    public boolean isSeatAvailable(String showtimeId, String seatLabel) {
        return false;
    }

}