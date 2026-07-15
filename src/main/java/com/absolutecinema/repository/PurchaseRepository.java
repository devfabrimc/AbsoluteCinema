package com.absolutecinema.repository;

import com.absolutecinema.model.Purchase;
import com.absolutecinema.utils.Paths;
import com.absolutecinema.utils.TxtFileManager;

import java.util.ArrayList;
import java.util.List;

/*  Implementación del repositorio para la gestión de la persistencia
    de objetos de la clase Purchase, usando el purchases.txt como fuente de datos
 */

public class PurchaseRepository implements Repository<Purchase> {
    // Declaramos la ruta del archivo que se encuentran las compras
    public static final String filePath = Paths.PURCHASE_REPOSITORY;

    // Es el gestor encargado de la lectura y escritura de archivos
    public final TxtFileManager fileManager = new TxtFileManager();

    // ------- Métodos sobrescritos de la Interfaz Repository -------

    /*  Lee todos los usuarios del purchases.txt.
        Convierte cada línea en un objeto Purchase
        utilizando el método estático de la clase Purchase
     */

    @Override
    public List<Purchase> findAll() {
        List<Purchase> purchases = new ArrayList<>();

        for (String line : fileManager.readLines(filePath)) {
            purchases.add(Purchase.fromString(line));
        }

        return purchases;
    }

    // Busca una compra en específica comparando cada uno de los IDs.

    @Override
    public Purchase findById(String id) {
        for (Purchase purchase : findAll()) {
            if (purchase.getId().equals(id)) {
                return purchase;
            }
        }

        return null;
    }

    // Agrega una nueva compra al final del purchases.txt

    @Override
    public void save(Purchase purchase) {
        fileManager.appendLine(filePath, purchase.toString());
    }

    /*  Actualiza una compra que ya existe.
        Cargando todos las compras, reemplaza el que
        coincide por ID en la lista y reescribe el purchases.txt
     */

    @Override
    public void update(Purchase purchase) {
        List<Purchase> purchases = findAll();

        for (int i = 0; i < purchases.size(); i++) {
            if (purchases.get(i).getId().equals(purchase.getId())) {
                purchases.set(i, purchase);
                break;
            }
        }

        writeAll(purchases);
    }

    /*  Elimina una compra por su ID.
        Filtrando la lista actual sin incluir el ID y sobrescribe el archivo
     */

    @Override
    public void delete(String id) {
        List<Purchase> purchases = findAll();

        purchases.removeIf(purchase -> purchase.getId().equals(id));
        writeAll(purchases);
    }

    // ------- Métodos de búsquedas específicas -------

    // Busca todas las compras asociadas a un ID de usuario en específico

    public List<Purchase> findByUserId(String userId) {
        List<Purchase> result = new ArrayList<>();

        for (Purchase purchase : findAll()) {
            if (purchase.getUserId().equals(userId)) {
                result.add(purchase);
            }
        }
        return result;
    }

    /*  Obtiene el último ID que se generó en el archivo
        para facilitar la creación de nuevos registros.
     */

    public String getLastId() {
        List<Purchase> purchases = findAll();

        if (purchases.isEmpty()) {
            return "PUR000";
        }
        return purchases.get(purchases.size() - 1).getId();
    }

    /*  Método auxiliar privado para la sincronización de la lista
        de objetos con el purchases.txt.
        Convierte la lista de objetos a una lista de strings
        por medio del "toString" y los almacena.
     */

    private void writeAll(List<Purchase> purchases){
        List<String> lines = new ArrayList<>();

        for(Purchase purchase : purchases){
            lines.add(purchase.toString());
        }

        fileManager.writeLines(filePath, lines);
    }
}
