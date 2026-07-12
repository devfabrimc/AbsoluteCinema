package com.absolutecinema.service;

import com.absolutecinema.model.Role;
import com.absolutecinema.model.User;
import com.absolutecinema.repository.MovieRepository;
import com.absolutecinema.repository.UserRepository;
import com.absolutecinema.utils.PasswordUtils;

public class AuthService {

    private UserRepository userRepository;

    //Constructor con inyección de dependencias
    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //Metodo de login
    public User login (String username, String password){
        //Creamos un usuario con username para buscarlo en el repositorio
        User user = userRepository.findByUsername(username);
        //Validamos si existe el usuario
        if (user == null){
            //Decimos que el usuario no está en el repositorio
            return null;
        }

        //Validamos la contraseña
        boolean isPasswordValid = PasswordUtils.verifyPassword(password, user.getPasswordHash());
        if (isPasswordValid){
            //Contraseña correcta
            return user;
        }
        //Contraseña incorrecta
        return null;
    }
    //Metodo para registrar usuarios
    public boolean register(String fullName, String email, String username,
                            String password){

        //Verificar que el usuario no exista para evitar duplicados
        if (usernameExists(username) || emailExists(email)){
            //Decimos que la creación del usuario no se realizó correctamente
            return false;
        }

        //Crear el hash de la contraseña
        String hashedPassword = PasswordUtils.hashPassword(password);

        //Crear el ID del usuario
        String lastId = userRepository.getLastId();
        //Extraemos solo la parte numérica del String
        //Recortamos el String mediante el substring
        int numericPart = Integer.parseInt(lastId.substring(3));
        int nextIdNumber = numericPart + 1;
        //Reconstruimos el String para el nuevo usuario
        String newId = String.format("USR%03d", nextIdNumber);

        //Creamos el usuario con el rol de customer por defecto
        User newUser = new User(newId, fullName, email, username, hashedPassword, Role.CUSTOMER);

        //Guardamos el usuario en el repositorio
        userRepository.save(newUser);

        //Decimos que la creación del usuario fue hecha correctamente
        return true;
    }
    //Metodo de logout
    public void logout(){
        System.out.println("Sesion finalizada...");
    }
    //Metodo verifica si el username ya existe
    public boolean usernameExists(String username){
        //Si la comparación es true significa que ya existe el usuario
        return userRepository.findByUsername(username) != null;
    }
    //Metodo verifica si el email ya existe
    public boolean emailExists(String email){
        //Si la comparación es true significa que ya existe el correo
        return userRepository.findByEmail(email) != null;
    }
}
