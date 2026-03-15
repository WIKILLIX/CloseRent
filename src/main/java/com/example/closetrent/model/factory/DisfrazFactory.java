package com.example.closetrent.model.factory;

import com.example.closetrent.model.Disfraz;
import com.example.closetrent.model.Prenda;

/**
 * Factory concreto para crear Disfraces.
 */
public class DisfrazFactory implements PrendaFactory {

    private String nombreDisfraz;

    /**
     * Constructor que recibe los atributos específicos de Disfraz.
     */
    public DisfrazFactory(String nombreDisfraz) {
        this.nombreDisfraz = nombreDisfraz;
    }

    @Override
    public Prenda crearPrenda(String referencia, String color, String marca,
                             String talla, Double valorAlquiler) {
        return new Disfraz(referencia, color, marca, talla, valorAlquiler, nombreDisfraz);
    }
}
