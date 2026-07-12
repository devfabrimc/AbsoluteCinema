package com.absolutecinema.utils;

import com.absolutecinema.model.User;

public class SessionManager {

    //Usaremos singleton para que la sesión se comparta en todas las pantallas del javafx
    private static SessionManager instance;
    private User currentUser;

    //Constructor privado para no crear copias de la sesión
    private SessionManager(){}

    //Metodo necesario para el Singleton
    public static SessionManager getInstance(){
        if (instance == null){
            //Si no existe se crea la única instancia de la sesión
            instance = new SessionManager();
        }
        //Se devuelve la misma sesión para cada pantalla (JavaFx)
        return instance;
    }

    //Metodo de login
    public void login(User user) {
        //Asignamos el usuario a la sesion actual
        this.currentUser = user;
    }

    //Metodo de logout
    public void logout() {
        //Eliminamos al usuario de la sesión actual
        this.currentUser = null;
    }

    //Getter del usuario actual
    public User getCurrentUser() {
        return this.currentUser;
    }

    //Metodo para verificar si alguien esta logueado (Sesión activa)
    public boolean isLoggedIn() {
        //Si no es nulo la sesión está activa
        return this.currentUser != null;
    }

    //Metodo para verificar si el usuario es administrador
    public boolean isAdmin() {
        //Tiene que estar logueado y tener el rol Admin (metodo en la clase User)
        return isLoggedIn() && this.currentUser.isAdmin();
    }
}