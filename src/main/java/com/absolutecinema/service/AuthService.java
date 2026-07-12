package com.absolutecinema.service;

import com.absolutecinema.model.User;
import com.absolutecinema.repository.MovieRepository;
import com.absolutecinema.repository.UserRepository;

public class AuthService {

    private UserRepository userRepository;

    //Constructor con inyección de dependencias
    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //Metodo de login
    public User login (String username, String password){

        return null;
    }
    //Metodo de registro
    public boolean register(String fullName, String email, String username,
                            String password){
        return false;
    }
    //Metodo de logout
    public void logout(){

    }
    //Metodo verifica si el username ya existe
    public boolean usernameExists(String username){
        return false;
    }
    //Metodo verifica si el email ya existe
    public boolean emailExists(String email){
        return false;
    }
}
