package com.absolutecinema.model;

public class Purchase {
    // Atributos

    private String id;
    private String userId;
    private String purchaseDate;
    private double total;

    // Constructor

    public Purchase(String id, String userId, String purchaseDate, double total) {
        this.id = id;
        this.userId = userId;
        this.purchaseDate = purchaseDate;
        this.total = total;
    }



    //Getters

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    // Método toString

    @Override
    public String toString() {
        return id + ";" +
                userId + ";" +
                purchaseDate + ";" +
                String.format("%.2f", total);
    }

    /*  Método para crear una instancia de Purchase
        a partir de una cadena de texto.
        Formato que se espera (separado por ";"):
        id;userId;purchaseDate;total

        Parámetro: line, la cadena de texto que se
        debe procesar.
     */

    public static Purchase fromString(String line) {

        String[] data = line.split(";");

        return new Purchase(
                data[0],
                data[1],
                data[2],
                Double.parseDouble(data[3].replace(",", "."))
        );
    }
}
