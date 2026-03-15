package com.example.closetrent.model.decorator;

import com.example.closetrent.model.Prenda;

/**
 * Decorador base para prendas sin prioridad (orden normal de llegada).
 */
public class PrendaBaseDecorator extends PrendaDecorator {

    public PrendaBaseDecorator(Prenda prenda) {
        super(prenda);
    }

    @Override
    public boolean tienePrioridad() {
        return false;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " [Normal]";
    }
}
