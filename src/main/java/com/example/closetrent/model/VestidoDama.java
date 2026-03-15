package com.example.closetrent.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un Vestido de Dama para alquiler.
 */
@Entity
@DiscriminatorValue("VESTIDO_DAMA")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class VestidoDama extends Prenda {

    @Column(name = "tiene_pedreria")
    private Boolean tienePedreria;

    @Column(name = "es_largo")
    private Boolean esLargo;

    @Column(name = "cantidad_piezas")
    private Integer cantidadPiezas;

    /**
     * Constructor completo para VestidoDama.
     */
    public VestidoDama(String referencia, String color, String marca, String talla,
                       Double valorAlquiler, Boolean tienePedreria,
                       Boolean esLargo, Integer cantidadPiezas) {
        super(referencia, color, marca, talla, valorAlquiler, true);
        this.tienePedreria = tienePedreria;
        this.esLargo = esLargo;
        this.cantidadPiezas = cantidadPiezas;
    }

    @Override
    public String getInfo() {
        return String.format("Vestido Dama - Ref: %s, Color: %s, Marca: %s, Talla: %s, " +
                           "Pedrería: %s, Largo: %s, Piezas: %d, Valor: $%.2f",
                getReferencia(), getColor(), getMarca(), getTalla(),
                tienePedreria ? "Sí" : "No", esLargo ? "Largo" : "Corto",
                cantidadPiezas, getValorAlquiler());
    }

    @Override
    public String getTipoPrenda() {
        return "Vestido de Dama";
    }
}
