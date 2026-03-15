package com.example.closetrent.model.decorator;

import com.example.closetrent.model.Prenda;

/**
 * Clase base abstracta para decoradores de prendas.
 */
public abstract class PrendaDecorator implements IPrendaDecorator {

    protected Prenda prenda;

    public PrendaDecorator(Prenda prenda) {
        this.prenda = prenda;
    }

    @Override
    public Prenda getPrenda() {
        return prenda;
    }

    @Override
    public String getInfo() {
        return prenda.getInfo();
    }
}
