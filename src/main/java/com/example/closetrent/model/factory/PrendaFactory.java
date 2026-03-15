package com.example.closetrent.model.factory;

import com.example.closetrent.model.Prenda;

/**
 * Patrón Factory Method: Interfaz abstracta para crear prendas.
 * Cada tipo de prenda tiene su propia fábrica concreta.
 */
public interface PrendaFactory {

    /**
     * Método factory para crear una prenda.
     * @param referencia Referencia única de la prenda
     * @param color Color de la prenda
     * @param marca Marca de la prenda
     * @param talla Talla de la prenda
     * @param valorAlquiler Valor del alquiler
     * @return Prenda creada
     */
    Prenda crearPrenda(String referencia, String color, String marca,
                       String talla, Double valorAlquiler);
}
