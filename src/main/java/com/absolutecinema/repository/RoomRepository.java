package com.absolutecinema.repository;

import com.absolutecinema.model.Room;
import com.absolutecinema.utils.Paths;
import com.absolutecinema.utils.TxtFileManager;

import java.util.ArrayList;
import java.util.List;

/*  Implementación del repositorio para la gestión de la persistencia
    de objetos de la clase Room, usando el rooms.txt como fuente de datos
 */

public class RoomRepository implements Repository<Room>{
    // Declaramos la ruta del archivo que se encuentran las salas
    private static final String filePath = Paths.ROOM_REPOSITORY;

    // Es el gestor encargado de la lectura y escritura de archivos
    private final TxtFileManager fileManager =  new TxtFileManager();

    // ------- Métodos sobrescritos de la Interfaz Repository -------

    /*  Lee todos las salas del rooms.txt.
        Convierte cada línea en un objeto Room
        utilizando el método estático de la clase Room
     */

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();

        for (String line : fileManager.readLines(filePath)) {
            rooms.add(Room.printformat(line));
        }

        return rooms;
    }

    // Busca una sala en específica comparando cada uno de los IDs.

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

    // Agrega una nueva sala al final del rooms.txt.

    @Override
    public void save(Room room) {
        fileManager.appendLine(filePath, room.toString());
    }

    /*  Actualiza una sala existente.
        Cargando todos las salas, reemplaza la que
        coincide por ID en la lista y reescribe el rooms.txt.
     */

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

    /*  Elimina una sala por su ID.
        Filtrando la lista actual sin incluir el ID y sobrescribe el archivo.
     */

    @Override
    public void delete(String id) {
        List<Room> rooms = findAll();

        boolean remove = rooms.removeIf(room -> room.getId().equals(id));

        if (remove) {
            for (int i = 0; i < rooms.size(); i++) {
                rooms.get(i).setId(String.format("ROM%03d", i+1));
            }
        }

        writeAll(rooms);
    }

    // ------- Método de búsqueda específica -------

    /*  Obtiene el último ID que se generó en el archivo
        para facilitar la creación de nuevos registros.
     */

    public String getLastId(){
        List<Room> rooms = findAll();

        if (rooms.isEmpty()){
            return "ROM000";
        }

        return  rooms.get(rooms.size()-1).getId();
    }

    /*  Método auxiliar privado para la sincronización de la lista
        de objetos con el rooms.txt.
        Convierte la lista de objetos a una lista de strings
        por medio del "toString" y los almacena.
     */

    private void writeAll(List<Room> rooms){
        List<String> lines = new ArrayList<>();

        for(Room room : rooms){
            lines.add(room.toString());
        }

        fileManager.writeLines(filePath, lines);
    }
}
