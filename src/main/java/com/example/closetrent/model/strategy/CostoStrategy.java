package com.example.closetrent.model.strategy;

import com.example.closetrent.model.Prenda;
import java.util.List;

/**
 * Patrón Strategy: Interfaz para diferentes estrategias de cálculo de costo de alquiler.
 * Permite definir diferentes algoritmos de cálculo sin modificar la lógica principal.
 */
public interface CostoStrategy {

    /**
     * Calcula el valor total del alquiler basado en la estrategia específica.
     *
     * @param prendas Lista de prendas a alquilar
     * @return Valor total del alquiler
     */
    Double calcularValor(List<Prenda> prendas);

    /**
     * Obtiene el nombre de la estrategia.
     *
     * @return Nombre descriptivo de la estrategia
     */
    String getNombreEstrategia();
}
