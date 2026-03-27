package com.example.closetrent.model.iterator;

import com.example.closetrent.model.Prenda;
import java.util.List;

/**
 * Implementación concreta del Iterator para Prendas.
 * Permite recorrer una colección de prendas de manera controlada.
 */
public class PrendaIterator implements Iterator<Prenda> {

    private final List<Prenda> prendas;
    private int posicion;

    /**
     * Constructor del iterador.
     *
     * @param prendas Lista de prendas a iterar
     */
    public PrendaIterator(List<Prenda> prendas) {
        this.prendas = prendas;
        this.posicion = 0;
    }

    @Override
    public boolean hasNext() {
        return posicion < prendas.size();
    }

    @Override
    public Prenda next() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("No hay más elementos en la colección");
        }
        return prendas.get(posicion++);
    }

    @Override
    public void reset() {
        posicion = 0;
    }

    /**
     * Obtiene la posición actual del iterador.
     *
     * @return Posición actual
     */
    public int getPosicion() {
        return posicion;
    }

    /**
     * Obtiene el total de elementos en la colección.
     *
     * @return Total de elementos
     */
    public int getTotalElementos() {
        return prendas.size();
    }
}
