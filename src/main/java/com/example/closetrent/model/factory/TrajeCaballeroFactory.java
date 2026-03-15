package com.example.closetrent.model.factory;

import com.example.closetrent.model.Prenda;
import com.example.closetrent.model.TrajeCaballero;
import com.example.closetrent.model.TrajeCaballero.AccesorioCuello;
import com.example.closetrent.model.TrajeCaballero.TipoTraje;

/**
 * Factory concreto para crear Trajes de Caballero.
 */
public class TrajeCaballeroFactory implements PrendaFactory {

    private TipoTraje tipoTraje;
    private AccesorioCuello accesorioCuello;

    /**
     * Constructor que recibe los atributos específicos de TrajeCaballero.
     */
    public TrajeCaballeroFactory(TipoTraje tipoTraje, AccesorioCuello accesorioCuello) {
        this.tipoTraje = tipoTraje;
        this.accesorioCuello = accesorioCuello;
    }

    @Override
    public Prenda crearPrenda(String referencia, String color, String marca,
                             String talla, Double valorAlquiler) {
        return new TrajeCaballero(referencia, color, marca, talla, valorAlquiler,
                                 tipoTraje, accesorioCuello);
    }
}
