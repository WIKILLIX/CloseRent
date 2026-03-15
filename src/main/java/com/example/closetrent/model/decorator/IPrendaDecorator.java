package com.example.closetrent.model.decorator;

import com.example.closetrent.model.Prenda;

/**
 * Patrón Decorator: Interfaz para decorar prendas con funcionalidades adicionales.
 * Permite agregar dinámicamente comportamientos a las prendas (como prioridad de lavado).
 */
public interface IPrendaDecorator {

    /**
     * Obtiene la prenda decorada.
     */
    Prenda getPrenda();

    /**
     * Indica si la prenda tiene prioridad.
     */
    boolean tienePrioridad();

    /**
     * Obtiene la información de la prenda decorada.
     */
    String getInfo();
}
