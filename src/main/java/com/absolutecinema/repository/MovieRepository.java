package com.absolutecinema.repository;

import com.absolutecinema.model.Genre;
import com.absolutecinema.model.Movie;
import com.absolutecinema.model.MovieStatus;
import com.absolutecinema.utils.Paths;
import com.absolutecinema.utils.TxtFileManager;

import java.util.ArrayList;
import java.util.List;

/*  Implementación del repositorio para la gestión de la persistencia
    de objetos de la clase Movie, usando el movies.txt como fuente de datos
 */

public class MovieRepository implements Repository<Movie> {
    // Declaramos la ruta del archivo que se encuentran las películas
    private static final String filePath = Paths.MOVIE_REPOSITORY;

    // Es el gestor encargado de la lectura y escritura de archivos
    private final TxtFileManager fileManager = new TxtFileManager();

    // ------- Métodos sobrescritos de la Interfaz Repository -------

    /*  Lee todos las películas del movies.txt.
        Convierte cada línea en un objeto Movie
        utilizando el método estático de la clase Movie
     */

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();

        for (String line : fileManager.readLines(filePath)) {
            movies.add(Movie.fromString(line));
        }

        return movies;
    }

    // Busca una película en específica comparando cada uno de los IDs.

    @Override
    public Movie findById(String id) {
        for (Movie movie : findAll()) {
            if (movie.getId().equals(id)) {
                return movie;
            }
        }
        return null;
    }

    // Agrega una nueva película al final del movies.txt.
    @Override
    public void save(Movie movie) {
        fileManager.appendLine(filePath, movie.toString());
    }

    /*  Actualiza una película existente.
        Cargando todos los películas, reemplaza la que
        coincide por ID en la lista y reescribe el movies.txt.
     */

    @Override
    public void update(Movie movie) {
        List<Movie> movies = findAll();
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getId().equals(movie.getId())) {
                movies.set(i, movie);
                break;
            }
        }

        writeAll(movies);
    }

    /*  Elimina una película por su ID.
        Filtrando la lista actual sin incluir el ID y sobrescribe el archivo.
     */

    @Override
    public void delete(String id) {
        List<Movie> movies = findAll();
        boolean remove = movies.removeIf(movie -> movie.getId().equals(id));

        if (remove) {
            for (int i=0; i<movies.size(); i++) {
                movies.get(i).setId(String.format("MOV%03d", i+1));
            }
        }
        writeAll(movies);
    }

    // ------- Métodos de búsquedas específicas -------

    // Busca o filtra las películas dependiendo de su estado (Cartelera o Próximamente)

    public List<Movie> findByStatus(MovieStatus status) {
        List<Movie> result = new ArrayList<>();

        for (Movie movie : findAll()) {
            if (movie.getStatus().equals(status)) {
                result.add(movie);
            }
        }

        return result;
    }

    /*  Obtiene el último ID que se generó en el archivo
        para facilitar la creación de nuevos registros.
     */

    public String getLastId() {
        List<Movie> movies = findAll();

        if (movies.isEmpty()) {
            return "MOV000";
        }
        return movies.get(movies.size() - 1).getId();
    }

    /*  Método auxiliar privado para la sincronización de la lista
        de objetos con el movies.txt.
        Convierte la lista de objetos a una lista de strings
        por medio del "toString" y los almacena.
     */

    private void writeAll(List<Movie> movies) {
        List<String> lines = new ArrayList<>();

        for (Movie movie : movies) {
            lines.add(movie.toString());
        }

        fileManager.writeLines(filePath, lines);
    }
}
