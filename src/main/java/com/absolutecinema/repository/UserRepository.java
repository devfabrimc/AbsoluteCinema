package com.absolutecinema.repository;

import com.absolutecinema.model.User;
import com.absolutecinema.utils.Paths;
import com.absolutecinema.utils.TxtFileManager;

import java.util.ArrayList;
import java.util.List;

/*  Implementación del repositorio para la gestión de la persistencia
    de objetos de la clase User, usando el users.txt como fuente de datos
 */

public class UserRepository implements Repository<User> {
    // Declaramos la ruta del archivo que se encuentran los usuarios
    private static final String filepath = Paths.USER_REPOSITORY;

    // Es el gestor encargado de la lectura y escritura de archivos
    private final TxtFileManager fileManager = new TxtFileManager();

    // ------- Métodos sobrescritos de la Interfaz Repository -------

    /*  Lee todos los usuarios del users.txt.
        Convierte cada línea en un objeto User
        utilizando el método estático de la clase User
     */

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        for(String line : fileManager.readLines(filepath)){
            users.add(User.fromString(line));
        }

        return users;
    }

    // Busca un usuario en específico comparando cada uno de los IDs.

    @Override
    public User findById(String id) {

        for(User user : findAll()){
            if(user.getId().equals(id)){
                return user;
            }
        }

        return null;
    }

    // Agrega un nuevo usuario al final del users.txt

    @Override
    public void save(User user) {
        fileManager.appendLine(filepath, user.toString());
    }

    /*  Actualiza un usuario que ya existe.
        Cargando todos los usuarios, reemplaza el que
        coincide por ID en la lista y reescribe el users.txt
     */

    @Override
    public void update(User user) {
        List<User> users = findAll();

        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId().equals(user.getId())){
                users.set(i, user);
                break;
            }
        }

        writeAll(users);
    }


    /*  Elimina un usuario por su ID.
        Filtrando la lista actual sin incluir el ID y sobrescribe el archivo
     */

    @Override
    public void delete(String id) {
        List<User> users = findAll();

        boolean remove = users.removeIf(user -> user.getId().equals(id));

        if(remove){
            for (int i = 0; i < users.size(); i++) {
                users.get(i).setId(String.format("USR%03d", i+1));
            }
        }

        writeAll(users);
    }

    // ------- Métodos de búsquedas específicas -------

    // Busca o filtra los usuarios mediante su nombre de usuario

    public User findByUsername(String username) {
        for(User user : findAll()){
            if(user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }

        return null;
    }

    // Busca a los usuarios por su correo, ignorando mayúsculas y minúsculas

    public User findByEmail(String email) {
        for(User user : findAll()){
            if(user.getEmail().equalsIgnoreCase(email)){
                return user;
            }
        }

        return null;
    }

    /*  Obtiene el último ID que se generó en el archivo
        para facilitar la creación de nuevos registros.
     */

    public String getLastId(){
        List<User> users = findAll();

        if (users.isEmpty()){
            return "USR000";
        }

        return  users.get(users.size()-1).getId();
    }

    /*  Método auxiliar privado para la sincronización de la lista
        de objetos con el users.txt.
        Convierte la lista de objetos a una lista de strings
        por medio del "toString" y los almacena.
     */

    private void writeAll(List<User> users){
        List<String> lines = new ArrayList<>();

        for(User user : users){
            lines.add(user.toString());
        }

        fileManager.writeLines(filepath, lines);
    }
}
