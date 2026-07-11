package com.absolutecinema.model;

public class Room {
    // Atributos
    private String id;
    private String name;
    private int rows;
    private int colums;

    // Constructor
    public Room(String id, String name, int rows, int colums) {
        this.id = id;
        this.name = name;
        this.rows = rows;
        this.colums = colums;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColums() {
        return colums;
    }

    public void setColums(int colums) {
        this.colums = colums;
    }

    // Metodo para calcular la capacidad de la sala
    public int getCapacity() {
        return getRows() * getColums();
    }
}
