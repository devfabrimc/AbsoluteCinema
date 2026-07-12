package com.absolutecinema.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    public static String hashPassword(String password) {
        try {
            //Crear variable del tipo MessageDigest y declaramos el algoritmo a usar (SHA256)
            MessageDigest passwordHash = MessageDigest.getInstance("SHA-256");
            //Trabajamos con Bytes y necesitamos un lugar donde almacenarlos (arreglo digest)
            //Dentro del metodo digest obtenemos los bytes de la contraseña
            //Dentro del getBytes nos aseguramos que use UTF-8
            byte [] digest = passwordHash.digest(password.getBytes(StandardCharsets.UTF_8));
            //Transformación de Bytes a Hexadecimal
            //Creamos un StringBuilder (ideal para bytes)
            StringBuilder hexPasswordHash = new StringBuilder();
            //Recorremos el arreglo digest
            for (byte b : digest){
                //Agregamos cada byte al hexPasswordHash
                //Pero transformamos cada byte en un String con el String.format
                hexPasswordHash.append(String.format("%02x",b));
            }
            //Finalmente transformamos el StringBuilder a String convencional y retornamos
            return hexPasswordHash.toString();
        //Catch en caso de no encontrar el algoritmo SHA-256
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifyPassword(String password, String hash) {
        //Aplicamos la encriptación a la constraseña que ingrese el usuario
        String currentPasswordHash = hashPassword(password);
        //Comparamos dicho hash con el del parámetro y lo retornamos
        return currentPasswordHash.equals(hash);
    }
}