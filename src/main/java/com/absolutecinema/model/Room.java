package com.absolutecinema.model;

public class Room {
    // Atributos

    private String id;
    private String name;
    private int rows;
    private int columns;

    // Constructor

    public Room(String id, String name, int rows, int columns) {
        this.id = id;
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }

    public static Room printformat(String line) {

        String[] data = line.split(";");

        return new Room(
                data[0],
                data[1],
                Integer.parseInt(data[2]),
                Integer.parseInt(data[3])
        );
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    //Método para calcular la capacidad de cada sala
    public int getCapacity() {
        return getRows() * getColumns();
    }

    // Método toString
    @Override
    public String toString() {
        return id + ";" +
                name + ";" +
                rows + ";" +
                columns;
    }
}
