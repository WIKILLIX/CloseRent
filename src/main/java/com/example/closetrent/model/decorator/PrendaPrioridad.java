package com.example.closetrent.model.decorator;

import com.example.closetrent.model.Prenda;

/**
 * Decorador para prendas con prioridad de lavado.
 * Se usa cuando la prenda llega manchada, es delicada o el administrador lo decide.
 */
public class PrendaPrioridad extends PrendaDecorator {

    private String motivoPrioridad;

    /**
     * Constructor con motivo de prioridad.
     */
    public PrendaPrioridad(Prenda prenda, String motivoPrioridad) {
        super(prenda);
        this.motivoPrioridad = motivoPrioridad;
    }

    /**
     * Constructor sin motivo específico.
     */
    public PrendaPrioridad(Prenda prenda) {
        this(prenda, "Prioridad administrativa");
    }

    @Override
    public boolean tienePrioridad() {
        return true;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " [PRIORIDAD: " + motivoPrioridad + "]";
    }

    public String getMotivoPrioridad() {
        return motivoPrioridad;
    }
}
