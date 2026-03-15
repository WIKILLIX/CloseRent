package com.example.closetrent.model.factory;

import com.example.closetrent.model.Prenda;
import com.example.closetrent.model.VestidoDama;

/**
 * Factory concreto para crear Vestidos de Dama.
 */
public class VestidoDamaFactory implements PrendaFactory {

    private Boolean tienePedreria;
    private Boolean esLargo;
    private Integer cantidadPiezas;

    /**
     * Constructor que recibe los atributos específicos de VestidoDama.
     */
    public VestidoDamaFactory(Boolean tienePedreria, Boolean esLargo, Integer cantidadPiezas) {
        this.tienePedreria = tienePedreria;
        this.esLargo = esLargo;
        this.cantidadPiezas = cantidadPiezas;
    }

    @Override
    public Prenda crearPrenda(String referencia, String color, String marca,
                             String talla, Double valorAlquiler) {
        return new VestidoDama(referencia, color, marca, talla, valorAlquiler,
                              tienePedreria, esLargo, cantidadPiezas);
    }
}
