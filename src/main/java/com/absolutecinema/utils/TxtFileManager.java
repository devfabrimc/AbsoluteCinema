package com.absolutecinema.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class TxtFileManager {
    /* Método que lee todas las líneas
       del archivo
       Declaramos como parametro la variable
       path como ruta del archivo
     */

    public List<String> readLines(String path) {
        Path filePath = Paths.get(path);
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
            }
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /*  Método para escribir o sobrescribir
        por completo un archivo
        Declaramos como parametros la variable
        path y la lista de líneas
        que es para la ruta del archivo
        y las línes a escribir, respectivamente
     */
    public void writeLines(String path, List<String> lines) {
        Path filePath = Paths.get(path);
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
            } else {
                Files.write(filePath, lines);
            }
        } catch (IOException e) {
            System.out.println("Error writing to file:  " + e.getMessage());
        }
    }

    /* Método para agregar una línea
       al final del archivo
       Declaramos como parametros la variable
       path y la lista de líneas
       que es para la ruta del archivo
       y las líneas a agregar, respectivamente
     */
    public void appendLine(String path, String lines) {
        Path filePath = Paths.get(path);
        try {
            if (!Files.exists(filePath)){
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
            } else {
                BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND);
                writer.write(lines);
                writer.newLine();
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("Error adding line to file:   " + e.getMessage());
        }
    }
}
