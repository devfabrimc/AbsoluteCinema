package com.absolutecinema.repository;

import java.util.List;

public interface Repository<T> {
    // Devuelve todos los objetos guardados en el archivo txt
    List<T> findAll();

    // Busca un objeto específico mediante su ID
    T findById(String id);

    // Guarda una nueva entidad determinada en el archivo txt
    void save(T object);

    // Actualiza un registro que ya existe (es decir, puede sobrescribir o modificar el archivo)
    void update(T object);

    // Elimina una entidad del archivo a través de su ID
    void delete(String id);
}
