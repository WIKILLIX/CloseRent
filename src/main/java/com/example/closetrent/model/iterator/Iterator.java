package com.example.closetrent.model.iterator;

/**
 * Patrón Iterator: Interfaz para iterar sobre colecciones.
 * Permite recorrer elementos sin exponer la estructura interna.
 *
 * @param <T> Tipo de elemento a iterar
 */
public interface Iterator<T> {

    /**
     * Verifica si hay más elementos en la colección.
     *
     * @return true si hay más elementos, false en caso contrario
     */
    boolean hasNext();

    /**
     * Obtiene el siguiente elemento de la colección.
     *
     * @return El siguiente elemento
     */
    T next();

    /**
     * Reinicia el iterador al inicio de la colección.
     */
    void reset();
}
