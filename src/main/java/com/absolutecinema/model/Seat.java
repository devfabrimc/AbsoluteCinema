package com.absolutecinema.model;

public class Seat {
    // Atributos
    private int row;
    private int column;
    private String label;

    // Constructor
    public Seat(int row, int column, String label) {
        this.row = row;
        this.column = column;
        this.label = label;
    }

    // Getters
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getLabel() {
        return label;
    }
}
