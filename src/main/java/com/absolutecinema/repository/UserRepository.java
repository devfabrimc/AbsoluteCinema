package com.absolutecinema.repository;

import com.absolutecinema.model.User;
import com.absolutecinema.utils.Paths;
import com.absolutecinema.utils.TxtFileManager;

import java.util.ArrayList;
import java.util.List;

/*  Implemantamos el repositorio para la entidad User
    La clase actúa como capa de persitencia, se encarga
    de leer y escribir datos de los usuarios en el users.txt
    mediante el uso de la clase TxtFileManager
 */
public class UserRepository implements Repository<User> {
    private static final String filepath = Paths.USER_REPOSITORY;
    private final TxtFileManager fileManager = new TxtFileManager();

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        for(String line : fileManager.readLines(filepath)){
            users.add(User.fromString(line));
        }

        return users;
    }

    @Override
    public User findById(String id) {

        for(User user : findAll()){
            if(user.getId().equals(id)){
                return user;
            }
        }

        return null;
    }

    @Override
    public void save(User user) {
        fileManager.appendLine(filepath, user.toString());
    }

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

    @Override
    public void delete(String id) {
        List<User> users = findAll();

        users.removeIf(user -> user.getId().equals(id));

        writeAll(users);
    }

    /*  Método que busca por su nombre de usuario
        (obviando mayúsculas o minúsculas).

        Parámetro: username, el nombre usuario que
        debe buscar
     */
    public User findByUsername(String username) {
        for(User user : findAll()){
            if(user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }

        return null;
    }

    /*  Método que busca un usuario por su correo electrónico
        (obviando mayúsculas o minúsculas).

        Parametro: email, El correo que debe buscar
     */
    public User findByEmail(String email) {
        for(User user : findAll()){
            if(user.getEmail().equalsIgnoreCase(email)){
                return user;
            }
        }

        return null;
    }

    /*  Método para obtener el ID del último usuario
        que está en el archivo.
     */

    public String getLastId(){
        List<User> users = findAll();

        if (users.isEmpty()){
            return "USR000";
        }

        return  users.get(users.size()-1).getId();
    }

    /*  Método auxiliar para sobrescribir el archivo
        con la lista actual de usuarios.

        Parametros: users, la lista de usuarios a guardar
        en el archivo.
     */
    private void writeAll(List<User> users){
        List<String> lines = new ArrayList<>();

        for(User user : users){
            lines.add(user.toString());
        }

        fileManager.writeLines(filepath, lines);
    }
}
