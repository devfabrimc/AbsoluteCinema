package com.absolutecinema.model;

public class User {
    // Atributos

    private String id;
    private String fullName;
    private String email;
    private String username;
    private String passwordHash;
    private Role role;

    // Constructor

    public User(String id, String fullName, String email, String username, String passwordHash, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public static User printformat(String line) {

        String[] data = line.split(";");

        return new User(
                data[0],
                data[1],
                data[2],
                data[3],
                data[4],
                Role.valueOf(data[5])
        );
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Método para validar si es administrador
    public boolean isAdmin(){
        return role.equals(Role.ADMIN);
    }
}
