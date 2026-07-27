package com.absolutecinema.repository;

import com.absolutecinema.model.Showtime;
import com.absolutecinema.utils.Paths;
import com.absolutecinema.utils.TxtFileManager;

import java.util.ArrayList;
import java.util.List;

/*  Implementación del repositorio para la gestión de la persistencia
    de objetos de la clase Showtime, usando el showtimes.txt como fuente de datos
 */

public class ShowtimeRepository implements Repository<Showtime> {
    // Declaramos la ruta del archivo que se encuentran los tickets
    private static final String filePath = Paths.SHOWTIME_REPOSITORY;

    // Es el gestor encargado de la lectura y escritura de archivos
    private final TxtFileManager fileManager = new TxtFileManager();

    // ------- Métodos sobrescritos de la Interfaz Repository -------

    /*  Lee todos los tickets del showtimes.txt.
        Convierte cada línea en un objeto Showtime
        utilizando el método estático de la clase Showtime
     */

    @Override
    public List<Showtime> findAll() {
        List<Showtime> showtimes = new ArrayList<>();

        for(String line : fileManager.readLines(filePath)){
            showtimes.add(Showtime.fromString(line));
        }

        return showtimes;
    }

    // Busca una función en específica comparando cada uno de los IDs.

    @Override
    public Showtime findById(String id) {

        for(Showtime showtime : findAll()){
            if(showtime.getId().equals(id)){
                return showtime;
            }
        }

        return null;
    }

    // Agrega una nueva función al final del showtimes.txt.

    @Override
    public void save(Showtime showtime) {
        fileManager.appendLine(filePath, showtime.toString());
    }

    /*  Actualiza una función existente.
        Cargando todas las funciones, reemplaza la que
        coincide por ID en la lista y reescribe el showtimes.txt.
     */

    @Override
    public void update(Showtime showtime) {
        List<Showtime> showtimes = findAll();

        for (int i = 0; i < showtimes.size(); i++) {
            if (showtimes.get(i).getId().equals(showtime.getId())) {
                showtimes.set(i, showtime);
                break;
            }
        }

        writeAll(showtimes);
    }

    /*  Elimina una función por su ID.
        Filtrando la lista actual sin incluir el ID y sobrescribe el archivo.
     */

    @Override
    public void delete(String id) {
        List<Showtime> showtimes = findAll();

        boolean remove = showtimes.removeIf(showtime -> showtime.getId().equals(id));

        if(remove){
            for (int i = 0; i < showtimes.size(); i++) {
                showtimes.get(i).setId(String.format("SHW%03d", i+1));
            }
        }

        writeAll(showtimes);
    }

    // ------- Método de búsqueda específica -------

    //  Busca todas las funciones asociadas a un ID de película en específico.

    public List<Showtime> findByMovieId(String movieId){
        List<Showtime> result = new ArrayList<>();
        for (Showtime showtime :  findAll()){
            if (showtime.getMovieId().equals(movieId)){
                result.add(showtime);
            }
        }

        return result;
    }

    // Busca todas las funciones programadas para una fecha específica.

    public List<Showtime> findByDate(String date){
        List<Showtime>  result = new ArrayList<>();

        for (Showtime showtime :   findAll()){
            if (showtime.getDate().equals(date)){
                result.add(showtime);
            }
        }

        return result;
    }

    /*  Obtiene el último ID que se generó en el archivo
        para facilitar la creación de nuevos registros.
     */

    public String getLastId() {
        List<Showtime> showtimes = findAll();

        if (showtimes.isEmpty()) {
            return "SHW000";
        }
        return showtimes.get(showtimes.size() - 1).getId();
    }

    /*  Método auxiliar privado para la sincronización de la lista
        de objetos con el showtimes.txt.
        Convierte la lista de objetos a una lista de strings
        por medio del "toString" y los almacena.
     */

    private void writeAll(List<Showtime> showtimes){
        List<String> lines = new ArrayList<>();

        for(Showtime showtime : showtimes){
            lines.add(showtime.toString());
        }

        fileManager.writeLines(filePath, lines);
    }
}
