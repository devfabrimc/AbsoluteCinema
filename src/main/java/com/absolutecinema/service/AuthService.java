package com.absolutecinema.service;

import com.absolutecinema.model.User;

public class AuthService {

    private UserRepository userRepository;

    //Metodo de User
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
