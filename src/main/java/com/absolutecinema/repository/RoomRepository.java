package com.absolutecinema.repository;

import com.absolutecinema.model.Room;
import com.absolutecinema.utils.TxtFileManager;

import java.util.ArrayList;
import java.util.List;

public class RoomRepository implements Repository<Room>{
    private static final String filePath = "resources/data/rooms.txt";
    private final TxtFileManager fileManager =  new TxtFileManager();

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();

        for (String line : fileManager.readLines(filePath)) {
            rooms.add(Room.printformat(line));
        }

        return rooms;
    }

    @Override
    public Room findById(String id) {
        List<Room> rooms = findAll();

        for (Room room : rooms) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        return null;
    }

    @Override
    public void save(Room room) {
        fileManager.appendLine(filePath, room.toString());
    }

    @Override
    public void update(Room room) {
        List<Room> rooms = findAll();

        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getId().equals(room.getId())) {
                rooms.set(i, room);
                break;
            }
        }

        writeAll(rooms);
    }

    @Override
    public void delete(String id) {
        List<Room> rooms = findAll();

        rooms.removeIf(room -> room.getId().equals(id));

        writeAll(rooms);
    }

    public String getLastId(){
        List<Room> rooms = findAll();

        if (rooms.isEmpty()){
            return "ROM000";
        }

        return  rooms.get(rooms.size()-1).getId();
    }

    private void writeAll(List<Room> rooms){
        List<String> lines = new ArrayList<>();

        for(Room room : rooms){
            lines.add(room.toString());
        }

        fileManager.writeLines(filePath, lines);
    }
}
